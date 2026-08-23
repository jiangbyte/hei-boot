#!/usr/bin/env python3
"""审计 sys_resource 按钮与 sys_iam_relation / hei-admin hasPermission 覆盖情况。"""

from __future__ import annotations

import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
DB = ROOT / "hei-boot" / "scripts" / "db.sql"
ADMIN = ROOT / "hei-admin" / "src"

from permission_code_map import code_to_perm


def parse_buttons(sql: str) -> dict[str, tuple[str, str, str]]:
    pat = re.compile(
        r"INSERT INTO `sys_resource` VALUES \('([^']+)', '([^']*)', '([^']+)', '([^']+)', '(BUTTON|ACTION)'"
    )
    out: dict[str, tuple[str, str, str]] = {}
    for m in pat.finditer(sql):
        rid, parent, code, name, _ = m.groups()
        out[rid] = (code, name, parent)
    return out


def parse_bindings(sql: str) -> dict[str, list[str]]:
    pat = re.compile(
        r"INSERT INTO `sys_iam_relation` VALUES \('[^']+', 'RESOURCE', '([^']+)', '[^']+', 'RESOURCE_PERMISSION', 'PERMISSION', '', '([^']+)'"
    )
    out: dict[str, list[str]] = {}
    for rid, key in pat.findall(sql):
        out.setdefault(rid, []).append(key)
    return out


def parse_admin_permissions(src: Path) -> set[str]:
    pat = re.compile(r"hasPermission\(['\"]([^'\"]+)['\"]\)")
    keys: set[str] = set()
    for f in src.rglob("*"):
        if f.suffix not in {".vue", ".ts", ".tsx"}:
            continue
        try:
            text = f.read_text(encoding="utf-8")
        except Exception:
            continue
        keys.update(pat.findall(text))
    return keys


def main() -> None:
    sql = DB.read_text(encoding="utf-8")
    buttons = parse_buttons(sql)
    bindings = parse_bindings(sql)
    admin_perms = parse_admin_permissions(ADMIN)

    missing_binding: list[str] = []
    wrong_binding: list[str] = []
    for rid, (code, name, _parent) in sorted(buttons.items(), key=lambda x: x[1][0]):
        expected = code_to_perm(code)
        actual = bindings.get(rid, [])
        if not actual:
            missing_binding.append(f"{rid}\t{code}\t{name}\texpected={expected}")
        elif expected and expected not in actual:
            wrong_binding.append(f"{rid}\t{code}\texpected={expected}\tactual={actual}")

    # admin perms not backed by any resource binding
    admin_without_db: list[str] = []
    all_bound_keys = {k for keys in bindings.values() for k in keys}
    for p in sorted(admin_perms):
        if p not in all_bound_keys and not p.endswith(":page"):
            # page often on MENU not BUTTON
            admin_without_db.append(p)

    # buttons whose expected perm never used in admin
    button_not_in_admin: list[str] = []
    for rid, (code, name, _parent) in buttons.items():
        expected = code_to_perm(code)
        if expected and expected not in admin_perms and not expected.endswith(":page"):
            button_not_in_admin.append(f"{code}\t{expected}\t{name}")

    print("=== DB BUTTON 资源统计 ===")
    print(f"按钮总数: {len(buttons)}")
    print(f"已绑定 RESOURCE_PERMISSION 的按钮: {sum(1 for rid in buttons if rid in bindings)}")
    print(f"未绑定: {len(missing_binding)}")
    print(f"绑定 key 不匹配: {len(wrong_binding)}")
    print()
    if missing_binding:
        print("--- 未绑定按钮 ---")
        for line in missing_binding:
            print(line)
        print()
    if wrong_binding:
        print("--- 绑定 key 不匹配 ---")
        for line in wrong_binding:
            print(line)
        print()

    print("=== hei-admin hasPermission 统计 ===")
    print(f"前端使用的权限 key 数: {len(admin_perms)}")
    print(f"其中无 DB 资源绑定的 key: {len(admin_without_db)}")
    if admin_without_db:
        print("--- 前端有、DB 无绑定 ---")
        for p in admin_without_db:
            print(p)
        print()

    print(f"DB 按钮预期权限但前端未使用: {len(button_not_in_admin)}")
    if button_not_in_admin[:30]:
        print("--- 样例 (前30) ---")
        for line in button_not_in_admin[:30]:
            print(line)


if __name__ == "__main__":
    main()
