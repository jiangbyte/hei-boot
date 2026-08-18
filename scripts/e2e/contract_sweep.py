""" Author: Charlie

全量 OpenAPI 契约扫：GET 出参对照 response schema；POST 入参负向（空体 → 422 错误壳）。
写成功出参由 CRUD 用例覆盖。
"""

from __future__ import annotations

import json
from typing import Any
from urllib.parse import quote

from .assert_util import CaseBucket, CaseResult, truncate
from .client import do_raw
from .contract import (
    build_registry,
    fetch_openapi,
    generate_example,
    has_json_200,
    iter_operations,
    request_body_schema,
    response_json_schema,
    validate_against_schema,
)
from .sweep import is_sql_suspect, materialize_path, pick_token, skip_route_reason


def _enrich_get_query(path: str, op: dict[str, Any], openapi: dict[str, Any]) -> str:
    params = op.get("parameters") or []
    parts: list[str] = []
    names: set[str] = set()
    for p in params:
        if not isinstance(p, dict) or p.get("in") != "query":
            continue
        name = str(p.get("name") or "")
        if not name:
            continue
        names.add(name)
        schema = p.get("schema") if isinstance(p.get("schema"), dict) else {}
        required = bool(p.get("required"))
        if name in {"current", "size", "page", "pageSize"} or required:
            if "example" in (schema or {}):
                val = schema["example"]
            elif "default" in (schema or {}):
                val = schema["default"]
            elif name in {"current", "page"}:
                val = 1
            elif name in {"size", "pageSize"}:
                val = 5
            elif name in {"id", "account_id", "accountId", "plan_id", "planId"}:
                val = "1"
            elif name in {"position"}:
                val = "HOME_TOP"
            elif name in {"table_name", "tableName"}:
                val = "sys_account"
            else:
                val = generate_example(openapi, schema or {"type": "string"})
            parts.append(f"{quote(name)}={quote(str(val))}")
    if path.rstrip("/").endswith(("/page", "/list", "/tree", "/my-page", "/children/page")):
        if "current" not in names:
            parts.append("current=1")
        if "size" not in names:
            parts.append("size=5")
    if path.rstrip("/").endswith("/list") and "banner" in path.lower() and "position" not in names:
        parts.append("position=HOME_TOP")
    for p in params:
        if isinstance(p, dict) and p.get("in") == "query" and p.get("name") in {"id", "account_id", "accountId"}:
            key = str(p.get("name"))
            if not any(x.startswith(f"{key}=") for x in parts):
                parts.append(f"{key}=1")
    return "&".join(parts)


def _skip_contract(method: str, path: str) -> str | None:
    reason = skip_route_reason(method, path)
    if reason:
        return reason
    low = path.lower()
    if any(
        s in low
        for s in (
            "/send-login-code",
            "/register/send-code",
            "/register",
            "/forgot-password",
            "/reset-password",
            "/refresh",
            "/kick",
            "/force-logout",
            "/test-push",
            "/test-webhook",
            "/trigger",
            "/execute",
            "/batch-save",
            "/easytrans/",
        )
    ):
        return "side-effect"
    return None


def _validate_response(
    openapi: dict[str, Any],
    registry: Any,
    op: dict[str, Any],
    status: int,
    raw: bytes,
) -> str | None:
    text = raw.decode("utf-8", "replace")
    schema = response_json_schema(openapi, op, str(status))
    if schema is None and status == 200 and not has_json_200(openapi, op):
        return None
    if schema is None:
        if 400 <= status < 500:
            try:
                obj = json.loads(text) if text.strip() else None
            except json.JSONDecodeError:
                return f"non-json error body status={status}"
            if isinstance(obj, dict) and "code" in obj and "message" in obj:
                return None
            return f"undeclared status={status} body not ApiError-shaped"
        if status >= 500:
            return f"undeclared 5xx status={status}"
        return f"no schema for status={status}"
    try:
        instance = json.loads(text) if text.strip() else None
    except json.JSONDecodeError:
        return f"response not json status={status}"
    return validate_against_schema(openapi, registry, schema, instance)


def run_contract_sweep(
    base: str,
    admin_tok: str,
    portal_tok: str,
    out_bucket: CaseBucket,
    in_bucket: CaseBucket,
    skipped: list[CaseResult],
    results: list[dict[str, Any]],
) -> dict[str, int]:
    openapi = fetch_openapi(base)
    registry = build_registry(openapi)
    ops = iter_operations(openapi)

    stats = {
        "ops_total": len(ops),
        "out_pass": 0,
        "out_fail": 0,
        "in_pass": 0,
        "in_fail": 0,
        "skipped": 0,
        "fail_5xx": 0,
        "sql_suspect": 0,
    }

    for item in ops:
        method = item["method"]
        path = item["path"]
        op = item["operation"]
        name = f"{method} {path}"

        skip = _skip_contract(method, path)
        if skip:
            skipped.append(CaseResult(name=name, ok=True, error=skip))
            stats["skipped"] += 1
            continue

        path_m = materialize_path(path)
        tok = pick_token(path_m, admin_tok, portal_tok)
        url = base + path_m

        if method == "GET":
            q = _enrich_get_query(path_m, op, openapi)
            get_url = f"{url}&{q}" if q and "?" in url else (f"{url}?{q}" if q else url)
            cr_out = CaseResult(name=f"OUT {name}", url=get_url)
            try:
                status, raw, ar = do_raw(method, get_url, tok, "")
                text = raw.decode("utf-8", "replace")
                cr_out.status, cr_out.biz_code, cr_out.body = status, ar.code, truncate(text, 240)
                entry: dict[str, Any] = {
                    "phase": "out",
                    "method": method,
                    "path": path,
                    "url": get_url,
                    "status": status,
                    "biz_code": ar.code,
                    "body": truncate(text, 280),
                    "is_5xx": status >= 500 or ar.code >= 500,
                    "sql_suspect": is_sql_suspect(text),
                }
                err = _validate_response(openapi, registry, op, status, raw)
                if entry["is_5xx"] or entry["sql_suspect"]:
                    cr_out.error = "5xx" if entry["is_5xx"] else "sql_suspect"
                    stats["fail_5xx" if entry["is_5xx"] else "sql_suspect"] += 1
                    stats["out_fail"] += 1
                    out_bucket.add(cr_out)
                elif err:
                    cr_out.error = err
                    stats["out_fail"] += 1
                    out_bucket.add(cr_out)
                    entry["schema_error"] = err
                else:
                    cr_out.ok = True
                    stats["out_pass"] += 1
                    out_bucket.add(cr_out)
                results.append(entry)
            except Exception as exc:  # noqa: BLE001
                err = str(exc)
                cr_out.error = err
                stats["out_fail"] += 1
                out_bucket.add(cr_out)
                conn = any(s in err.lower() for s in ("10054", "10061", "connection", "refused", "timed out"))
                results.append(
                    {
                        "phase": "out",
                        "method": method,
                        "path": path,
                        "url": get_url,
                        "error": err,
                        "is_5xx": conn,
                    }
                )

        if method != "POST":
            continue
        req_schema = request_body_schema(openapi, op)
        if req_schema is None:
            continue
        url = base + path_m
        cr_in = CaseResult(name=f"IN {name}", url=url)
        try:
            st, raw, ar = do_raw("POST", url, tok, "{}")
            text = raw.decode("utf-8", "replace")
            cr_in.status, cr_in.biz_code, cr_in.body = st, ar.code, truncate(text, 240)
            entry_in: dict[str, Any] = {
                "phase": "in",
                "method": method,
                "path": path,
                "url": url,
                "status": st,
                "biz_code": ar.code,
                "body": truncate(text, 280),
                "is_5xx": st >= 500 or ar.code >= 500,
                "sql_suspect": is_sql_suspect(text),
            }
            if entry_in["is_5xx"] or entry_in["sql_suspect"]:
                cr_in.error = "5xx" if entry_in["is_5xx"] else "sql_suspect"
                stats["in_fail"] += 1
                stats["fail_5xx" if entry_in["is_5xx"] else "sql_suspect"] += 1
                in_bucket.add(cr_in)
            else:
                err = _validate_response(openapi, registry, op, st, raw)
                if err and st == 422:
                    # 错误壳不符合 schema 才失败；多数 422 未在 OpenAPI 声明
                    if "non-json" in err or "not ApiError" in err:
                        cr_in.error = err
                        stats["in_fail"] += 1
                        in_bucket.add(cr_in)
                        entry_in["schema_error"] = err
                    else:
                        cr_in.ok = True
                        stats["in_pass"] += 1
                        in_bucket.add(cr_in)
                elif st >= 500:
                    cr_in.error = f"unexpected status {st}"
                    stats["in_fail"] += 1
                    in_bucket.add(cr_in)
                elif err and st < 400:
                    cr_in.error = err
                    stats["in_fail"] += 1
                    in_bucket.add(cr_in)
                    entry_in["schema_error"] = err
                else:
                    cr_in.ok = True
                    stats["in_pass"] += 1
                    in_bucket.add(cr_in)
            results.append(entry_in)
        except Exception as exc:  # noqa: BLE001
            err = str(exc)
            cr_in.error = err
            stats["in_fail"] += 1
            in_bucket.add(cr_in)
            conn = any(s in err.lower() for s in ("10054", "10061", "connection", "refused", "timed out"))
            results.append(
                {
                    "phase": "in",
                    "method": method,
                    "path": path,
                    "url": url,
                    "error": err,
                    "is_5xx": conn,
                }
            )

    return stats
