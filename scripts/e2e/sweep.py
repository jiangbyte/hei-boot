"""OpenAPI route sweep helpers."""

from __future__ import annotations

import json
from typing import Any
from urllib.request import Request, urlopen

from .client import do_raw, truncate


def is_sql_suspect(body: str) -> bool:
    low = body.lower()
    needles = (
        "syntax error",
        "sqlException".lower(),
        "bad sql",
        "psql",
        "mysql",
        "jdbc",
        "org.postgresql",
        "com.mysql",
        "jsonb",
        "returning ",
        "for update skip locked",
    )
    return any(n in low for n in needles)


def pick_token(path: str, admin_tok: str, portal_tok: str) -> str:
    if "/portal/" in path:
        return portal_tok
    if "/internal/" in path:
        return admin_tok
    if "/admin/" in path:
        return admin_tok
    return admin_tok or portal_tok


def skip_route_reason(method: str, path: str) -> str | None:
    if method in {"HEAD", "OPTIONS", "CONNECT"}:
        return "method"
    if path in {"/metrics", "/v3/api-docs", "/swagger-ui.html"}:
        return "docs"
    low = path.lower()
    if "/oauth/" in path:
        return "oauth"
    if "/easytrans/" in low:
        return "internal-proxy"
    if any(
        s in low
        for s in (
            "/captcha",
            "/password-key",
            "/login",
            "/logout",
            "/register",
            "/forgot-password",
            "/reset-password",
            "/send-login-code",
            "/cancel",
            "/auth/refresh",
        )
    ):
        return "auth-bootstrap"
    if "/upload" in path or "/avatar" in path or "/download" in path:
        return "storage"
    return None


def materialize_path(path: str) -> str:
    return (
        path.replace("{provider}", "github")
        .replace("{account_id}", "1")
        .replace("{accountId}", "1")
        .replace("{id}", "1")
    )


def fetch_openapi_paths(base: str) -> list[tuple[str, str]]:
    """Return list of (METHOD, path) from springdoc OpenAPI."""
    url = base.rstrip("/") + "/v3/api-docs"
    req = Request(url)
    with urlopen(req, timeout=60) as resp:
        doc = json.loads(resp.read().decode("utf-8"))
    out: list[tuple[str, str]] = []
    for path, item in (doc.get("paths") or {}).items():
        if not isinstance(item, dict):
            continue
        for method in item.keys():
            m = method.upper()
            if m in {"GET", "POST", "PUT", "DELETE", "PATCH"}:
                out.append((m, path))
    out.sort(key=lambda x: (x[1], x[0]))
    return out


_SKIP_SUFFIXES = (
    "/captcha",
    "/password-key",
    "/login",
    "/logout",
    "/register",
    "/forgot-password",
    "/reset-password",
    "/send-login-code",
    "/cancel",
    "/auth/refresh",
    "/oauth/",
    "/avatar/upload",
    "/download",
    "/test-webhook",
    "/test-push",
    # 需必填 query 参数，裸扫会 4xx/5xx，非方言问题
    "/detail",
    "/fields",
    "/preview",
    "/table-columns",
    "/sessions/tokens",
)


def should_skip_sweep(method: str, path: str) -> bool:
    if "{" in path:
        return True
    low = path.lower()
    if any(s in low for s in _SKIP_SUFFIXES):
        return True
    if method != "GET" and not low.endswith(("/page", "/list", "/tree", "/detail", "/selector", "/current", "/overview")):
        # POST writes skipped in sweep; covered by hard/crud selectively
        if method == "POST":
            return True
    return False


def fill_query_defaults(path: str) -> str:
    if "?" in path:
        return path
    if path.rstrip("/").endswith(("/page", "/my-page", "/children/page")):
        return path + "?current=1&size=5"
    if path.rstrip("/").endswith("/list") and "banner" in path:
        return path + "?position=HOME_TOP"
    return path


def run_openapi_sweep(
    base: str,
    admin_tok: str,
    portal_tok: str,
    results: list[dict[str, Any]],
) -> tuple[int, int]:
    routes = fetch_openapi_paths(base)
    ok_n = 0
    fail_n = 0
    for method, path in routes:
        if should_skip_sweep(method, path):
            continue
        url_path = fill_query_defaults(path)
        tok = pick_token(url_path, admin_tok, portal_tok)
        url = base.rstrip("/") + url_path
        entry: dict[str, Any] = {"method": method, "path": url_path, "url": url}
        try:
            status, raw, ar = do_raw(method, url, tok)
            body = truncate(raw.decode("utf-8", "replace"), 400)
            suspect = is_sql_suspect(body)
            is_5xx = status >= 500 or ar.code >= 500
            entry.update(
                {
                    "status": status,
                    "biz_code": ar.code,
                    "body": body,
                    "is_5xx": is_5xx,
                    "sql_suspect": suspect,
                }
            )
            ok = (not is_5xx) and (not suspect) and (200 <= status < 500)
            entry["ok"] = ok
            if ok:
                ok_n += 1
                print("SWEEP PASS", method, url_path)
            else:
                fail_n += 1
                print("SWEEP FAIL", method, url_path, status, ar.code, body[:120])
        except Exception as exc:  # noqa: BLE001
            entry["error"] = str(exc)
            entry["ok"] = False
            entry["is_5xx"] = True
            fail_n += 1
            print("SWEEP FAIL", method, url_path, exc)
        results.append(entry)
    return ok_n, fail_n
