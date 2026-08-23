""" Author: Charlie

MySQL e2e 编排：导入种子 → 启 Spring Boot → 扫路由报告。

用法::

    set JAVA_HOME=E:\\envs\\jdk-21
    python scripts/run_dialect_e2e.py
"""

from __future__ import annotations

import json
import os
import subprocess
import sys
import threading
import time
from pathlib import Path
from urllib.parse import quote
from urllib.request import urlopen

ROOT = Path(__file__).resolve().parents[1]
HOST = "127.0.0.1"
PASS = "123456"
DB = "hei_boot"
PORT = 8000
REDIS_URL = f"redis://:{quote(PASS)}@{HOST}:6379/0"
JAVA_HOME = os.environ.get("JAVA_HOME", r"E:\envs\jdk-21")
MAVEN_HOME = os.environ.get("MAVEN_HOME", r"E:\tools\apache-maven")


def _env() -> dict[str, str]:
    env = os.environ.copy()
    env["JAVA_HOME"] = JAVA_HOME
    path_parts = [
        str(Path(JAVA_HOME) / "bin"),
        str(Path(MAVEN_HOME) / "bin"),
        env.get("Path", env.get("PATH", "")),
    ]
    env["Path"] = os.pathsep.join(path_parts)
    env["PATH"] = env["Path"]
    env["SPRING_PROFILES_ACTIVE"] = "dev"
    env["PYTHONUTF8"] = "1"
    env["PYTHONIOENCODING"] = "utf-8"
    env["PYTHONUNBUFFERED"] = "1"
    env["REDIS_HOST"] = HOST
    env["REDIS_PORT"] = "6379"
    env["REDIS_PASSWORD"] = PASS
    env["REDIS_DATABASE"] = "0"
    env["DB_WRITE_DRIVER"] = "com.mysql.cj.jdbc.Driver"
    env["DB_WRITE_URL"] = (
        f"jdbc:mysql://{HOST}:3306/{DB}?useUnicode=true&characterEncoding=utf8"
        f"&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false"
    )
    env["DB_WRITE_USERNAME"] = "root"
    env["DB_WRITE_PASSWORD"] = PASS
    env["DB_READ_DRIVER"] = env["DB_WRITE_DRIVER"]
    env["DB_READ_URL"] = env["DB_WRITE_URL"]
    env["DB_READ_USERNAME"] = "root"
    env["DB_READ_PASSWORD"] = PASS
    return env


def _ensure_containers(*names: str) -> None:
    for name in names:
        running = subprocess.run(
            ["wsl", "docker", "inspect", "-f", "{{.State.Running}}", name],
            cwd=ROOT,
            capture_output=True,
            text=True,
        )
        if (running.stdout or "").strip().lower() != "true":
            subprocess.run(["wsl", "docker", "start", name], check=False, cwd=ROOT, capture_output=True)


def _keepalive_containers(names: tuple[str, ...], stop: threading.Event) -> None:
    """WSL Docker 容器会无故 Exit 0，测试期间周期性拉起。"""
    while not stop.wait(5):
        _ensure_containers(*names)


def _wait_tcp(host: str, port: int, *, containers: tuple[str, ...] = (), attempts: int = 45) -> None:
    import socket

    for i in range(attempts):
        if containers and i > 0 and i % 5 == 0:
            _ensure_containers(*containers)
        try:
            with socket.create_connection((host, port), timeout=1.0):
                print(f"tcp {host}:{port} ready after {i}", flush=True)
                return
        except OSError:
            time.sleep(1)
    raise RuntimeError(f"tcp {host}:{port} not reachable from Windows")


def _wait_redis() -> None:
    _wait_tcp(HOST, 6379, containers=("dev-redis",))
    # module-enabled Redis 启动后还要加载 AOF，仅 TCP 通不够
    for i in range(30):
        ping = subprocess.run(
            ["wsl", "docker", "exec", "dev-redis", "redis-cli", "-a", PASS, "PING"],
            cwd=ROOT,
            capture_output=True,
            text=True,
        )
        if "PONG" in (ping.stdout or "") + (ping.stderr or ""):
            print(f"redis PONG after {i}", flush=True)
            time.sleep(2)
            return
        time.sleep(1)
    raise RuntimeError("redis not ready")


def prepare_mysql() -> None:
    script = ROOT / "scripts" / "import-mysql-wsl.sh"
    # normalize line endings for bash
    text = script.read_text(encoding="utf-8").replace("\r\n", "\n").replace("\r", "\n")
    script.write_bytes(text.encode("utf-8"))
    proc = subprocess.run(
        ["wsl", "bash", "/mnt/e/projects/mine/hei/hei-boot/scripts/import-mysql-wsl.sh"],
        cwd=ROOT,
        check=False,
    )
    if proc.returncode != 0:
        raise RuntimeError("mysql seed import failed")
    # ensure containers still up with published ports (Windows host reachability)
    _ensure_containers("dev-mysql", "dev-redis")
    _wait_tcp(HOST, 3306, containers=("dev-mysql", "dev-redis"))
    _wait_redis()
    print("mysql: seeded", DB, flush=True)


def _wait(base: str, proc: subprocess.Popen | None = None, attempts: int = 120) -> None:
    for i in range(attempts):
        if proc is not None and proc.poll() is not None:
            raise RuntimeError(f"spring boot exited early code={proc.returncode}")
        try:
            with urlopen(base + "/api/v1/internal/health/live", timeout=2) as r:
                if r.status == 200:
                    print("app ready after", i, "checks", flush=True)
                    return
        except Exception:
            time.sleep(1)
    raise RuntimeError("spring boot not ready")


def _kill_port(port: int) -> None:
    # Windows: find and kill listeners on port
    try:
        out = subprocess.check_output(f'netstat -ano | findstr ":{port}"', shell=True, text=True, errors="ignore")
    except subprocess.CalledProcessError:
        return
    pids = set()
    for line in out.splitlines():
        parts = line.split()
        if len(parts) >= 5 and f":{port}" in parts[1] and parts[3] == "LISTENING":
            pids.add(parts[4])
    for pid in pids:
        subprocess.run(["taskkill", "/F", "/PID", pid], check=False, capture_output=True)


def run_e2e() -> dict:
    prepare_mysql()

    _kill_port(PORT)
    env = _env()
    base = f"http://127.0.0.1:{PORT}"
    out = ROOT / "scripts" / "e2e" / "report-mysql.json"
    log_path = ROOT / "scripts" / "e2e" / "boot-mysql.log"
    log_fp = open(log_path, "w", encoding="utf-8")
    jar = ROOT / "app" / "admin" / "target" / "admin.jar"
    skip_build = os.environ.get("HEI_E2E_SKIP_BUILD", "").lower() in {"1", "true", "yes"}
    if skip_build and jar.exists():
        print(f"skip maven package, use existing {jar}", flush=True)
    else:
        mvn = f'"{MAVEN_HOME}\\bin\\mvn.cmd"'
        mvn_log = ROOT / "scripts" / "e2e" / "mvn-mysql.log"
        with open(mvn_log, "w", encoding="utf-8") as mvn_fp:
            build = subprocess.run(
                f"{mvn} -pl app/admin -am package -DskipTests",
                cwd=ROOT,
                env=env,
                stdout=mvn_fp,
                stderr=subprocess.STDOUT,
                shell=True,
                check=False,
            )
        if build.returncode != 0:
            raise RuntimeError(f"maven package failed, see {mvn_log}")
    if not jar.exists():
        raise RuntimeError(f"missing jar: {jar}")
    keep_names = ("dev-mysql", "dev-redis")
    stop_keep = threading.Event()
    keeper = threading.Thread(target=_keepalive_containers, args=(keep_names, stop_keep), daemon=True)
    keeper.start()
    _ensure_containers("dev-mysql", "dev-redis")
    _wait_tcp(HOST, 3306, containers=("dev-mysql", "dev-redis"))
    _wait_redis()
    proc = subprocess.Popen(
        f'"{JAVA_HOME}\\bin\\java.exe" -jar "{jar}"',
        cwd=ROOT,
        env=env,
        stdout=log_fp,
        stderr=subprocess.STDOUT,
        shell=True,
    )
    try:
        _wait(base, proc)
        e2e = subprocess.run(
            [
                sys.executable,
                "-m",
                "scripts.e2e",
                "--base",
                base,
                "--redis",
                REDIS_URL,
                "--out",
                str(out),
            ],
            cwd=ROOT,
            env=env,
            check=False,
        )
        summary = {}
        if out.exists():
            summary = json.loads(out.read_text(encoding="utf-8"))
        return {
            "dialect": "mysql",
            "e2e_exit": e2e.returncode,
            "report": str(out),
            "log": str(log_path),
            "admin_login_ok": summary.get("admin_login_ok"),
            "portal_login_ok": summary.get("portal_login_ok"),
            "hard_pass": summary.get("hard_pass"),
            "hard_fail": summary.get("hard_fail"),
            "out_pass": (summary.get("out_cases") or {}).get("pass"),
            "out_total": (summary.get("out_cases") or {}).get("total"),
            "in_pass": (summary.get("in_cases") or {}).get("pass"),
            "in_total": (summary.get("in_cases") or {}).get("total"),
            "crud_pass": (summary.get("crud_cases") or {}).get("pass"),
            "crud_total": (summary.get("crud_cases") or {}).get("total"),
            "fail_5xx": summary.get("fail_5xx"),
        }
    finally:
        stop_keep.set()
        proc.terminate()
        try:
            proc.wait(timeout=30)
        except subprocess.TimeoutExpired:
            proc.kill()
        log_fp.close()
        _kill_port(PORT)


def main() -> int:
    started = time.time()
    subprocess.run(
        [sys.executable, "-m", "pip", "install", "-q", "bcrypt", "cryptography", "redis", "jsonschema", "referencing"],
        check=False,
    )
    print("\n===== mysql =====", flush=True)
    result = run_e2e()
    report = {"elapsed_seconds": round(time.time() - started, 1), "rounds": [result]}
    path = ROOT / "scripts" / "e2e" / "report-summary.json"
    path.write_text(json.dumps(report, ensure_ascii=False, indent=2), encoding="utf-8")
    print(json.dumps(report, ensure_ascii=False, indent=2))
    return 0 if result.get("e2e_exit") == 0 else 1


if __name__ == "__main__":
    raise SystemExit(main())
