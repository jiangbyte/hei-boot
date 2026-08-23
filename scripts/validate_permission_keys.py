#!/usr/bin/env python3
"""校验权限 key 是否为 module:entity:action 三段小写。"""

from __future__ import annotations

import re
import sys
from pathlib import Path

from permission_code_map import VALID_PERM

ROOT = Path(__file__).resolve().parents[2]
DB = ROOT / "hei-boot" / "scripts" / "db.sql"
SCAN_DIRS = [ROOT / "hei-boot", ROOT / "hei-admin" / "src"]

DB_BINDING_PAT = re.compile(
    r"INSERT INTO `sys_iam_relation` VALUES \('[^']+', 'RESOURCE', '[^']+', '[^']+', "
    r"'RESOURCE_PERMISSION', 'PERMISSION', '', '([^']+)'"
)
JAVA_PERM_PAT = re.compile(r'@SaCheckPermission\(value\s*=\s*"([^"]+)"')
HAS_PERM_PAT = re.compile(r"""hasPermission\(['"]([^'"]+)['"]\)""")
PERM_FIELD_PAT = re.compile(r"""permission:\s*['"]([^'"]+)['"]""")

SKIP_PREFIXES = ("jdbc:", "hei:banner:interaction:", "hei:admin:")


def is_permission_candidate(key: str) -> bool:
    if not key or key == "*:*:*":
        return False
    if any(key.startswith(p) for p in SKIP_PREFIXES):
        return False
    return ":" in key and key.count(":") >= 2


def collect_keys() -> tuple[dict[str, int], dict[str, list[str]]]:
    counts = {"db_binding": 0, "java": 0, "admin": 0}
    sources: dict[str, list[str]] = {}

    def add(source: str, key: str) -> None:
        if not is_permission_candidate(key):
            return
        sources.setdefault(key, []).append(source)

    if DB.exists():
        for key in DB_BINDING_PAT.findall(DB.read_text(encoding="utf-8")):
            counts["db_binding"] += 1
            add("db.sql", key)

    for base in SCAN_DIRS:
        if not base.exists():
            continue
        for path in base.rglob("*"):
            if path.suffix not in {".java", ".vue", ".ts", ".tsx"}:
                continue
            if "node_modules" in path.parts:
                continue
            try:
                text = path.read_text(encoding="utf-8")
            except Exception:
                continue
            rel = str(path.relative_to(ROOT))
            if path.suffix == ".java":
                for key in JAVA_PERM_PAT.findall(text):
                    counts["java"] += 1
                    add(rel, key)
            else:
                for key in HAS_PERM_PAT.findall(text):
                    counts["admin"] += 1
                    add(rel, key)
                for key in PERM_FIELD_PAT.findall(text):
                    counts["admin"] += 1
                    add(rel, key)

    return counts, sources


def main() -> int:
    counts, sources = collect_keys()
    bad = sorted(
        k for k in sources if is_permission_candidate(k) and not VALID_PERM.match(k)
    )

    print("=== 权限 key 校验 ===")
    print(f"DB 绑定: {counts['db_binding']}")
    print(f"Java @SaCheckPermission: {counts['java']}")
    print(f"前端 hasPermission/permission: {counts['admin']}")
    print(f"去重后权限 key: {len(sources)}")
    print(f"不合规: {len(bad)}")
    if bad:
        for k in bad:
            parts = k.split(":")
            print(f"  {k} ({len(parts)} 段) <- {', '.join(sources[k][:3])}")
        return 1
    print("全部合规")
    return 0


if __name__ == "__main__":
    sys.exit(main())
