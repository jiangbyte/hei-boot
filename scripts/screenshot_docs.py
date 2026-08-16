# -*- coding: utf-8 -*-
"""Capture ALL docs/images screenshots. Captcha cracked from Redis.

Run: conda run -n normal python scripts/screenshot_docs.py
"""
from __future__ import annotations

import sys
import time
from pathlib import Path

from playwright.sync_api import sync_playwright

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "scripts"))
from read_captcha import Redis, crack, DEFAULT_ALPHABET  # noqa: E402

OUT = ROOT / "docs" / "images"
ADMIN = "http://localhost:5176"
PORTAL = "http://localhost:5175"
VIEWPORT = {"width": 1440, "height": 900}

PH_ADMIN_ACCOUNT = "\u8bf7\u8f93\u5165\u7ba1\u7406\u5458\u8d26\u53f7"
PH_PORTAL_ACCOUNT = "\u8bf7\u8f93\u5165\u8d26\u53f7"
PH_PASSWORD = "\u8bf7\u8f93\u5165\u5bc6\u7801"
PH_CAPTCHA = "\u8bf7\u8f93\u5165\u9a8c\u8bc1\u7801"

ADMIN_SHOTS = [
    ("admin-dashboard.png", f"{ADMIN}/dashboard"),
    ("admin-iam-account.png", f"{ADMIN}/iam/account"),
    ("admin-iam-role.png", f"{ADMIN}/iam/role"),
    ("admin-iam-dept.png", f"{ADMIN}/iam/dept"),
    ("admin-iam-group.png", f"{ADMIN}/iam/group"),
    ("admin-iam-position.png", f"{ADMIN}/iam/position"),
    ("admin-iam-resource.png", f"{ADMIN}/iam/resource"),
    ("admin-iam-resource-module.png", f"{ADMIN}/iam/resource_module"),
    ("admin-iam-client-resource.png", f"{ADMIN}/iam/client_resource"),
    ("admin-sys-config.png", f"{ADMIN}/sys/config"),
    ("admin-sys-dict.png", f"{ADMIN}/sys/dict"),
    ("admin-sys-audit.png", f"{ADMIN}/sys/audit"),
    ("admin-sys-codegen.png", f"{ADMIN}/sys/codegen"),
    ("admin-sys-session.png", f"{ADMIN}/sys/session"),
    ("admin-sys-login-log.png", f"{ADMIN}/sys/login-log"),
    ("admin-sys-banner.png", f"{ADMIN}/sys/banner"),
    ("admin-message-notice.png", f"{ADMIN}/sys/notice"),
    ("admin-message-feedback.png", f"{ADMIN}/sys/feedback"),
    ("admin-sys-file.png", f"{ADMIN}/sys/file"),
    ("admin-biz-order.png", f"{ADMIN}/biz/cg-test-order"),
]


def crack_id(captcha_id: str) -> str:
    redis = Redis("127.0.0.1", 6379, "123456", 0)
    try:
        for _ in range(25):
            hashed = redis._cmd("GET", f"captcha:{captcha_id}")
            if hashed:
                code = crack(hashed, DEFAULT_ALPHABET)
                if code:
                    return code
            time.sleep(0.12)
    finally:
        redis.close()
    raise RuntimeError(f"unable to crack captcha {captcha_id}")


def clear_images() -> None:
    OUT.mkdir(parents=True, exist_ok=True)
    for p in OUT.glob("*.png"):
        p.unlink()
        print("deleted", p.name)


def shot(page, name: str) -> None:
    page.wait_for_timeout(900)
    page.screenshot(path=str(OUT / name), full_page=False)
    print("saved", name, "bytes=", (OUT / name).stat().st_size)


def load_login(page, url: str, realm: str) -> str:
    """Open login page and return the *latest* captcha_id (React StrictMode may fetch twice)."""
    ids: list[str] = []

    def on_response(response) -> None:
        if f"/api/v1/{realm}/captcha" not in response.url:
            return
        if response.request.method != "GET" or not response.ok:
            return
        try:
            payload = response.json()
        except Exception:  # noqa: BLE001
            return
        data = payload.get("data") or {}
        cid = data.get("captcha_id") or data.get("captchaId")
        if cid:
            ids.append(cid)

    page.on("response", on_response)
    page.goto(url, wait_until="networkidle")
    page.wait_for_timeout(1200)
    page.remove_listener("response", on_response)

    # Prefer DOM form field when present (portal Ant Design).
    dom_cid = page.evaluate(
        """() => {
          const nodes = Array.from(document.querySelectorAll('input'));
          const hit = nodes.find((el) => (el.id || '').includes('captcha_id') && el.value);
          return hit ? hit.value : '';
        }"""
    )
    if dom_cid:
        print("captcha from DOM:", dom_cid, "network:", ids)
        return dom_cid
    if not ids:
        raise RuntimeError("no captcha responses captured")
    print("captcha ids seen:", ids)
    return ids[-1]


def submit_login(page, account_ph: str, account: str, captcha_id: str) -> None:
    code = crack_id(captcha_id)
    print("captcha", captcha_id, code)
    page.get_by_placeholder(account_ph).click()
    page.get_by_placeholder(account_ph).fill(account)
    page.get_by_placeholder(PH_PASSWORD).click()
    page.get_by_placeholder(PH_PASSWORD).fill("123456")
    page.get_by_placeholder(PH_CAPTCHA).click()
    page.get_by_placeholder(PH_CAPTCHA).fill(code)
    page.wait_for_timeout(200)
    page.locator("button.auth-submit, button[type='submit']").first.click(timeout=15000)
    try:
        page.wait_for_url(lambda url: "/auth/login" not in url, timeout=20000)
    except Exception:
        # dump failure clue
        page.screenshot(path=str(OUT / "_login_fail.png"), full_page=False)
        msg = page.locator(".ant-message-error, .n-message, .ant-form-item-explain-error").all_inner_texts()
        print("login failed, messages:", msg)
        print("url:", page.url)
        raise
    page.wait_for_timeout(1200)


def goto_shot(page, url: str, name: str) -> None:
    page.goto(url, wait_until="networkidle", timeout=60000)
    page.wait_for_timeout(1000)
    shot(page, name)


def main() -> int:
    only = (sys.argv[1] if len(sys.argv) > 1 else "").strip().lower()
    if only != "portal":
        clear_images()

    with sync_playwright() as p:
        browser = p.chromium.launch(channel="chrome", headless=True)
        context = browser.new_context(viewport=VIEWPORT, device_scale_factor=1)
        page = context.new_page()

        if only not in ("portal",):
            cid = load_login(page, f"{ADMIN}/auth/login", "admin")
            page.wait_for_timeout(400)
            shot(page, "admin-login.png")
            submit_login(page, PH_ADMIN_ACCOUNT, "superadmin", cid)
            for name, url in ADMIN_SHOTS:
                goto_shot(page, url, name)
            context.clear_cookies()
            page.goto("about:blank")

        if only in ("", "portal"):
            cid = load_login(page, f"{PORTAL}/auth/login", "portal")
            page.wait_for_timeout(400)
            shot(page, "portal-login.png")
            submit_login(page, PH_PORTAL_ACCOUNT, "user", cid)
            goto_shot(page, f"{PORTAL}/", "portal-home.png")

        browser.close()

    files = sorted(OUT.glob("*.png"))
    print(f"done: {len(files)} files -> {OUT}")
    for f in files:
        print(f"  {f.name}\t{f.stat().st_size}")
    expected = {n for n, _ in ADMIN_SHOTS} | {
        "admin-login.png",
        "portal-login.png",
        "portal-home.png",
    }
    missing = sorted(expected - {f.name for f in files})
    if missing:
        print("MISSING:", missing)
        return 1
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
