"""Envelope / page assertions for e2e reports."""

from __future__ import annotations

import json
import sys
from dataclasses import dataclass, field
from typing import Any


def _safe_print(*args: object) -> None:
    text = " ".join(str(a) for a in args)
    try:
        print(text, flush=True)
    except UnicodeEncodeError:
        sys.stdout.write(text.encode("ascii", "backslashreplace").decode("ascii") + "\n")
        sys.stdout.flush()


@dataclass
class CaseResult:
    name: str
    ok: bool = False
    error: str = ""
    url: str = ""
    status: int = 0
    biz_code: int = 0
    body: str = ""

    def to_dict(self) -> dict[str, Any]:
        d: dict[str, Any] = {"name": self.name, "ok": self.ok}
        if self.error:
            d["error"] = self.error
        if self.url:
            d["url"] = self.url
        if self.status:
            d["status"] = self.status
        if self.biz_code:
            d["biz_code"] = self.biz_code
        if self.body:
            d["body"] = self.body
        return d


@dataclass
class CaseBucket:
    total: int = 0
    pass_: int = 0
    fail: list[CaseResult] = field(default_factory=list)

    def add(self, cr: CaseResult) -> None:
        self.total += 1
        if cr.ok:
            self.pass_ += 1
            _safe_print("PASS", cr.name)
        else:
            self.fail.append(cr)
            _safe_print("FAIL", cr.name, (cr.error or "")[:240])

    def to_dict(self) -> dict[str, Any]:
        return {
            "total": self.total,
            "pass": self.pass_,
            "fail": [f.to_dict() for f in self.fail],
        }


def truncate(s: str, n: int) -> str:
    if len(s) <= n:
        return s
    return s[:n] + "..."


def parse_code(raw: Any) -> int:
    if raw is None:
        return 0
    if isinstance(raw, bool):
        return 0
    if isinstance(raw, int):
        return raw
    if isinstance(raw, float):
        return int(raw)
    if isinstance(raw, str):
        text = raw.strip()
        if not text:
            return 0
        return int(text)
    return 0


def parse_envelope(body: bytes | str) -> tuple[dict[str, Any], dict[str, Any] | None]:
    if isinstance(body, bytes):
        text = body.decode("utf-8", "replace")
    else:
        text = body
    if not text.strip():
        return {}, None
    obj = json.loads(text)
    if not isinstance(obj, dict):
        return {}, None
    data = obj.get("data")
    return obj, data if isinstance(data, dict) else None


def assert_biz_ok(status: int, code: int) -> None:
    if status < 200 or status >= 300:
        raise AssertionError(f"http status {status}")
    if code not in (0, 200):
        raise AssertionError(f"biz code {code}")


def assert_keys(m: dict[str, Any] | None, *keys: str) -> None:
    if m is None:
        raise AssertionError("data is nil")
    missing = [k for k in keys if k not in m]
    if missing:
        raise AssertionError(f"missing keys: {','.join(missing)}")


def assert_page(m: dict[str, Any] | None) -> list[dict[str, Any]]:
    assert_keys(m, "size", "current", "total", "pages", "records")
    assert m is not None
    raw = m["records"]
    if not isinstance(raw, list):
        raise AssertionError("records not array")
    return [item for item in raw if isinstance(item, dict)]


def as_string(v: Any) -> str:
    if v is None:
        return ""
    if isinstance(v, str):
        return v
    if isinstance(v, float):
        return f"{v:.0f}"
    return str(v)


def find_id_by_field(records: list[dict[str, Any]], field: str, want: str) -> str:
    for rec in records:
        if as_string(rec.get(field)) == want:
            return as_string(rec.get("id"))
    return ""
