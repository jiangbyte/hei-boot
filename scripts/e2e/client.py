"""HTTP helpers for hei-boot dialect e2e."""

from __future__ import annotations

import json
import time
from typing import Any
from urllib.error import HTTPError, URLError
from urllib.request import Request, urlopen

_TIMEOUT = 60


def truncate(s: str, n: int) -> str:
    if len(s) <= n:
        return s
    return s[:n] + "..."


class ApiResp:
    def __init__(self, code: int = 0, message: str = "", data: Any = None):
        self.code = code
        self.message = message
        self.data = data


def parse_loose(raw: bytes) -> ApiResp:
    try:
        obj = json.loads(raw.decode("utf-8", "replace"))
    except Exception:
        return ApiResp(code=500, message=raw.decode("utf-8", "replace")[:200])
    if not isinstance(obj, dict):
        return ApiResp(data=obj)
    code = obj.get("code", 0)
    if isinstance(code, bool):
        code = 0
    elif isinstance(code, float):
        code = int(code)
    elif not isinstance(code, int):
        try:
            code = int(code)
        except Exception:
            code = 0
    return ApiResp(code=code, message=str(obj.get("message") or ""), data=obj.get("data"))


def do_raw(method: str, url: str, token: str = "", body: str = "") -> tuple[int, bytes, ApiResp]:
    last_err: Exception | None = None
    for attempt in range(5):
        data = body.encode("utf-8") if body else None
        req = Request(url, data=data, method=method.upper())
        req.add_header("Connection", "close")
        if body:
            req.add_header("Content-Type", "application/json")
        if token:
            req.add_header("Authorization", token)
        try:
            with urlopen(req, timeout=_TIMEOUT) as resp:
                raw = resp.read()
                status = int(getattr(resp, "status", 200))
            return status, raw, parse_loose(raw)
        except HTTPError as exc:
            raw = exc.read() if exc.fp else b""
            status = int(exc.code)
            return status, raw, parse_loose(raw)
        except (URLError, OSError, ConnectionError, TimeoutError) as exc:
            last_err = exc
            time.sleep(2.0 * (attempt + 1))
    raise RuntimeError(f"{method} {url}: {last_err}") from last_err


def get_json(url: str) -> dict[str, Any]:
    status, raw, _ = do_raw("GET", url)
    if status >= 500:
        raise RuntimeError(f"GET {url} status {status}: {truncate(raw.decode('utf-8', 'replace'), 200)}")
    return json.loads(raw.decode("utf-8"))
