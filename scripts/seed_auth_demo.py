#!/usr/bin/env python3
"""向 db.sql 写入认证/授权演示种子（部门、角色、账户、Profile、IAM 关系、业务样例数据）。"""

from __future__ import annotations

import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DB = ROOT / "scripts" / "db.sql"
MARKER = "-- === AUTH_DEMO_SEED (auto, do not edit) ==="

PWD_HASH = "$2b$12$LLjMHXpuX5MuFwevZiU11OP4OQYVIDUKBBqNeK6VvDqfTWeqs8BJi"  # 123456
TS = "2026-08-23 08:00:00"

DEPT_HQ = "8200000000000101"
DEPT_RD = "8200000000000102"
DEPT_MARKET = "8200000000000103"
DEPT_RD_FE = "8200000000000104"
DEPT_RD_BE = "8200000000000105"
DEPT_RD_QA = "8200000000000106"
DEPT_HR = "8200000000000107"

GRP_RD = "8200000000000301"
GRP_MKT = "8200000000000302"
GRP_IAM = "8200000000000303"
GRP_FE = "8200000000000304"
GRP_BE = "8200000000000305"
GRP_QA = "8200000000000306"

ROLE_IAM_ADMIN = "2"
ROLE_BIZ_ALL = "3"
ROLE_BIZ_DEPT = "4"
ROLE_BIZ_SELF = "5"
ROLE_BIZ_CHILD = "6"
ROLE_IAM_READONLY = "7"

ACC_IAM = "8200000000000201"
ACC_ALL = "8200000000000202"
ACC_DEPT = "8200000000000203"
ACC_SELF = "8200000000000204"
ACC_CHILD = "8200000000000205"
ACC_READONLY = "8200000000000206"
ACC_GROUP = "8200000000000207"
ACC_BE = "8200000000000208"
ACC_QA = "8200000000000209"
ACC_PORTAL_BOB = "8200000000000211"
ACC_PORTAL_ALICE = "8200000000000212"

POS_RD_DIR = "8200000000000801"
POS_RD_SENIOR = "8200000000000802"
POS_FE_ENG = "8200000000000803"
POS_BE_ENG = "8200000000000804"
POS_QA_ENG = "8200000000000805"
POS_MKT_MGR = "8200000000000806"
POS_SALES = "8200000000000807"
POS_HR_SPEC = "8200000000000808"

RES_WORKSPACE = "200001"
RES_ORG = "200006"
RES_BIZ_ACTIVITY = "202004"
RES_BIZ_CATALOG = "202005"
RES_BIZ_ORDER = "202006"
RES_BIZ_KNOWLEDGE = "202007"
RES_ACCOUNT_MENU = "200007"
RES_ACCOUNT_DETAIL_BTN = "201102"

SCOPE_PATCHES: list[tuple[str, str, str]] = [
    ("biz:cgtestcatalog:", "DEPT", "[]"),
    ("biz:cgtestorder:", "SELF", "[]"),
    ("biz:cgtestknowledgecategory:", "DEPT_AND_CHILD", "[]"),
    ("iam:account:page", "CUSTOM", f'["{DEPT_RD}","{DEPT_MARKET}"]'),
]

ACCOUNT_LOGIN_PATTERN = re.compile(r"^[a-zA-Z0-9_]{3,64}$")


def assert_account_login(login: str) -> None:
    if not ACCOUNT_LOGIN_PATTERN.fullmatch(login):
        raise ValueError(f"invalid account login: {login!r} (allowed: [a-zA-Z0-9_], 3-64 chars)")


def sql_account(aid: str, atype: str) -> str:
    return (
        f"INSERT INTO `sys_account` VALUES ('{aid}', '{PWD_HASH}', '{atype}', "
        f"'ENABLED', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, "
        f"NULL, NULL, NULL, '{TS}', '1', '{TS}', '1');"
    )


def sql_identity(iid: str, aid: str, itype: str, identifier: str, primary: int = 1) -> str:
    return (
        f"INSERT INTO `sys_account_identity` VALUES ('{iid}', '{aid}', '{itype}', "
        f"'{identifier}', 1, {primary}, 'BOUND', '{TS}', '1', '{TS}', '1');"
    )


def sql_admin_profile(aid: str, nickname: str, email: str, remark: str) -> str:
    return (
        f"INSERT INTO `profile_user_admin` VALUES ('{aid}', '{nickname}', NULL, NULL, "
        f"NULL, '{email}', '{remark}', '{TS}', '1', '{TS}', '1');"
    )


def sql_portal_profile(aid: str, nickname: str, email: str, phone: str = "") -> str:
    phone_val = f"'{phone}'" if phone else "NULL"
    return (
        f"INSERT INTO `profile_user_portal` VALUES ('{aid}', '{nickname}', NULL, NULL, "
        f"{phone_val}, '{email}', '{TS}', '1', '{TS}', '1');"
    )


def sql_dept(
    did: str,
    parent: str | None,
    name: str,
    sort: int,
    master: str | None = None,
    deputy: str | None = None,
) -> str:
    parent_val = f"'{parent}'" if parent else "NULL"
    master_val = f"'{master}'" if master else "NULL"
    deputy_val = f"'{deputy}'" if deputy else "NULL"
    return (
        f"INSERT INTO `sys_dept` VALUES ('{did}', {parent_val}, {master_val}, {deputy_val}, "
        f"'{name}', 'ORG', {sort}, 0, 'ENABLED', '{{}}', '{TS}', '1', '{TS}', '1');"
    )


def sql_role(
    rid: str,
    code: str,
    name: str,
    sort: int,
    remark: str,
    owner_dept: str | None = None,
) -> str:
    owner_val = f"'{owner_dept}'" if owner_dept else "NULL"
    return (
        f"INSERT INTO `sys_role` VALUES ('{rid}', '{code}', '{name}', 'SYS', "
        f"'PLATFORM', {owner_val}, {sort}, 'ENABLED', 0, '{remark}', '{{}}', '{TS}', '1', "
        f"'{TS}', '1');"
    )


def sql_group(gid: str, name: str, owner_dept: str, desc: str) -> str:
    desc_sql = desc.replace("'", "''")
    return (
        f"INSERT INTO `sys_group` VALUES ('{gid}', '{name}', '{owner_dept}', "
        f"'{desc_sql}', 'ENABLED', '{{}}', '{TS}', '1', '{TS}', '1');"
    )


def sql_position(
    pid: str,
    name: str,
    category: str,
    owner_dept: str,
    sort: int,
    desc: str = "",
) -> str:
    desc_sql = desc.replace("'", "''")
    desc_val = f"'{desc_sql}'" if desc_sql else "NULL"
    return (
        f"INSERT INTO `sys_position` VALUES ('{pid}', '{name}', '{category}', "
        f"'{owner_dept}', {sort}, 0, 'ENABLED', {desc_val}, '{{}}', "
        f"'{TS}', '1', '{TS}', '1');"
    )


def sql_rel(
    rid: str,
    subject_type: str,
    subject_id: str,
    relation_type: str,
    target_type: str,
    target_id: str,
    target_key: str = "",
    grant_mode: str = "CASCADE",
    data_scope: str = "ALL",
    custom_depts: str = "[]",
    is_primary: int = 0,
    sort: int = 0,
    desc: str = "",
) -> str:
    desc_sql = desc.replace("'", "''")
    return (
        f"INSERT INTO `sys_iam_relation` VALUES ('{rid}', '{subject_type}', "
        f"'{subject_id}', 'ADMIN', '{relation_type}', '{target_type}', '{target_id}', "
        f"'{target_key}', '{grant_mode}', '{data_scope}', '{custom_depts}', "
        f"{is_primary}, {sort}, 'ENABLED', '{desc_sql}', NULL, NULL, '{{}}', "
        f"'{TS}', '1', '{TS}', '1');"
    )


def patch_data_scopes(text: str) -> str:
    out: list[str] = []
    for line in text.splitlines():
        if "RESOURCE_PERMISSION" not in line:
            out.append(line)
            continue
        patched = line
        for prefix, scope, custom in SCOPE_PATCHES:
            if prefix in line:
                patched = re.sub(
                    r"'CASCADE', 'ALL', '\[\]'",
                    f"'CASCADE', '{scope}', '{custom}'",
                    patched,
                    count=1,
                )
                break
        out.append(patched)
    return "\n".join(out)


def build_block() -> str:
    lines: list[str] = [MARKER, "", "-- 部门树"]
    lines += [
        sql_dept(DEPT_HQ, None, "总部", 1),
        sql_dept(DEPT_RD, DEPT_HQ, "研发部", 1, ACC_IAM, ACC_CHILD),
        sql_dept(DEPT_MARKET, DEPT_HQ, "市场部", 2, ACC_ALL),
        sql_dept(DEPT_HR, DEPT_HQ, "人事行政部", 3, ACC_READONLY),
        sql_dept(DEPT_RD_FE, DEPT_RD, "前端组", 1, ACC_DEPT),
        sql_dept(DEPT_RD_BE, DEPT_RD, "后端组", 2, ACC_BE),
        sql_dept(DEPT_RD_QA, DEPT_RD, "测试组", 3, ACC_QA),
        "",
        "-- 岗位",
        sql_position(POS_RD_DIR, "研发总监", "MANAGEMENT", DEPT_RD, 1, "研发部管理岗"),
        sql_position(POS_RD_SENIOR, "高级工程师", "TECHNICAL", DEPT_RD, 2, "研发部技术骨干"),
        sql_position(POS_FE_ENG, "前端工程师", "TECHNICAL", DEPT_RD_FE, 1, "前端组默认岗位"),
        sql_position(POS_BE_ENG, "后端工程师", "TECHNICAL", DEPT_RD_BE, 1, "后端组默认岗位"),
        sql_position(POS_QA_ENG, "测试工程师", "TECHNICAL", DEPT_RD_QA, 1, "测试组默认岗位"),
        sql_position(POS_MKT_MGR, "市场经理", "MANAGEMENT", DEPT_MARKET, 1, "市场部管理岗"),
        sql_position(POS_SALES, "销售专员", "OPERATION", DEPT_MARKET, 2, "市场部业务岗"),
        sql_position(POS_HR_SPEC, "人事专员", "SUPPORT", DEPT_HR, 1, "人事行政支持岗"),
        "",
        "-- 角色（非超管）",
        sql_role(
            ROLE_IAM_ADMIN,
            "IAM_ADMIN",
            "IAM 管理员",
            10,
            "组织权限管理（非超管）",
            DEPT_RD,
        ),
        sql_role(ROLE_BIZ_ALL, "BIZ_ALL", "业务-全部数据", 20, "活动模块 ALL", DEPT_MARKET),
        sql_role(ROLE_BIZ_DEPT, "BIZ_DEPT", "业务-本部门", 21, "目录模块 DEPT", DEPT_RD),
        sql_role(ROLE_BIZ_SELF, "BIZ_SELF", "业务-仅本人", 22, "订单模块 SELF", DEPT_RD_FE),
        sql_role(
            ROLE_BIZ_CHILD,
            "BIZ_CHILD",
            "业务-部门及子部门",
            23,
            "知识分类 DEPT_AND_CHILD",
            DEPT_RD,
        ),
        sql_role(
            ROLE_IAM_READONLY,
            "IAM_READONLY",
            "IAM 只读",
            30,
            "账号管理只读",
            DEPT_HR,
        ),
        "",
        "-- 用户组",
        sql_group(GRP_RD, "研发组成员", DEPT_RD, "继承 BIZ_DEPT，目录模块本部门数据"),
        sql_group(GRP_FE, "前端专项组", DEPT_RD_FE, "前端组成员，用于子部门协作演示"),
        sql_group(GRP_MKT, "市场运营组", DEPT_MARKET, "继承 BIZ_ALL，活动模块全量数据"),
        sql_group(GRP_IAM, "IAM协作组", DEPT_RD, "继承 IAM_READONLY，账号只读协作"),
        sql_group(GRP_BE, "后端专项组", DEPT_RD_BE, "后端组成员，目录模块本部门数据"),
        sql_group(GRP_QA, "测试专项组", DEPT_RD_QA, "测试组成员，知识分类部门及子部门"),
        "",
        "-- 用户组-角色",
        sql_rel(
            "8200000000000311",
            "GROUP",
            GRP_RD,
            "GROUP_ROLE",
            "ROLE",
            ROLE_BIZ_DEPT,
            desc="研发组绑定业务-本部门角色",
        ),
        sql_rel(
            "8200000000000312",
            "GROUP",
            GRP_FE,
            "GROUP_ROLE",
            "ROLE",
            ROLE_BIZ_SELF,
            desc="前端组绑定业务-仅本人角色",
        ),
        sql_rel(
            "8200000000000313",
            "GROUP",
            GRP_MKT,
            "GROUP_ROLE",
            "ROLE",
            ROLE_BIZ_ALL,
            desc="市场组绑定业务-全量角色",
        ),
        sql_rel(
            "8200000000000314",
            "GROUP",
            GRP_IAM,
            "GROUP_ROLE",
            "ROLE",
            ROLE_IAM_READONLY,
            desc="IAM协作组绑定只读角色",
        ),
        sql_rel(
            "8200000000000315",
            "GROUP",
            GRP_BE,
            "GROUP_ROLE",
            "ROLE",
            ROLE_BIZ_DEPT,
            desc="后端组绑定业务-本部门角色",
        ),
        sql_rel(
            "8200000000000316",
            "GROUP",
            GRP_QA,
            "GROUP_ROLE",
            "ROLE",
            ROLE_BIZ_CHILD,
            desc="测试组绑定业务-部门及子部门角色",
        ),
        "",
    ]

    lines.append("-- 管理端账户")
    for aid, login, nick, email, remark in [
        (ACC_IAM, "admin_iam", "IAM管理员", "iam-admin@demo.local", "IAM 管理员"),
        (ACC_ALL, "admin_all", "业务全量", "biz-all@demo.local", "活动 ALL"),
        (ACC_DEPT, "admin_dept", "研发部员", "biz-dept@demo.local", "目录 DEPT"),
        (ACC_SELF, "admin_self", "仅本人", "biz-self@demo.local", "订单 SELF"),
        (ACC_CHILD, "admin_child", "研发含子部门", "biz-child@demo.local", "知识分类 DEPT_AND_CHILD"),
        (ACC_READONLY, "admin_readonly", "只读账号", "readonly@demo.local", "账号只读"),
        (ACC_GROUP, "admin_group", "组授权", "group-rd@demo.local", "用户组继承角色"),
        (ACC_BE, "admin_be", "后端工程师", "biz-be@demo.local", "后端组，目录 DEPT"),
        (ACC_QA, "admin_qa", "测试工程师", "biz-qa@demo.local", "测试组，知识 DEPT_AND_CHILD"),
    ]:
        assert_account_login(login)
        lines += [
            sql_account(aid, "ADMIN"),
            sql_identity(f"{aid}01", aid, "ACCOUNT", login),
            sql_identity(f"{aid}02", aid, "EMAIL", email, primary=0),
            sql_admin_profile(aid, nick, email, remark),
        ]
    lines.append("")

    lines.append("-- 门户账户")
    for aid, login, nick, email, phone in [
        (ACC_PORTAL_BOB, "portal_bob", "Bob", "bob@demo.local", "13800001001"),
        (ACC_PORTAL_ALICE, "portal_alice", "Alice", "alice@demo.local", "13800001002"),
    ]:
        assert_account_login(login)
        lines += [
            sql_account(aid, "PORTAL"),
            sql_identity(f"{aid}01", aid, "ACCOUNT", login),
            sql_identity(f"{aid}02", aid, "EMAIL", email, 0),
            sql_portal_profile(aid, nick, email, phone),
        ]
    lines += [
        "-- 账户-部门",
        sql_rel("8200000000000401", "ACCOUNT", ACC_IAM, "ACCOUNT_DEPT", "DEPT", DEPT_RD, is_primary=1),
        sql_rel("8200000000000402", "ACCOUNT", ACC_DEPT, "ACCOUNT_DEPT", "DEPT", DEPT_RD_FE, is_primary=1),
        sql_rel("8200000000000403", "ACCOUNT", ACC_CHILD, "ACCOUNT_DEPT", "DEPT", DEPT_RD, is_primary=1),
        sql_rel("8200000000000404", "ACCOUNT", ACC_GROUP, "ACCOUNT_DEPT", "DEPT", DEPT_RD, is_primary=1),
        sql_rel("8200000000000405", "ACCOUNT", ACC_ALL, "ACCOUNT_DEPT", "DEPT", DEPT_MARKET, is_primary=1),
        sql_rel("8200000000000406", "ACCOUNT", ACC_SELF, "ACCOUNT_DEPT", "DEPT", DEPT_RD_FE, is_primary=1),
        sql_rel("8200000000000407", "ACCOUNT", ACC_READONLY, "ACCOUNT_DEPT", "DEPT", DEPT_HR, is_primary=1),
        sql_rel("8200000000000408", "ACCOUNT", ACC_BE, "ACCOUNT_DEPT", "DEPT", DEPT_RD_BE, is_primary=1),
        sql_rel("8200000000000409", "ACCOUNT", ACC_QA, "ACCOUNT_DEPT", "DEPT", DEPT_RD_QA, is_primary=1),
        sql_rel(
            "8200000000000410",
            "ACCOUNT",
            ACC_DEPT,
            "ACCOUNT_DEPT",
            "DEPT",
            DEPT_RD,
            is_primary=0,
            sort=1,
            desc="前端组员工兼属研发部",
        ),
        sql_rel(
            "8200000000000411",
            "ACCOUNT",
            ACC_BE,
            "ACCOUNT_DEPT",
            "DEPT",
            DEPT_RD,
            is_primary=0,
            sort=1,
            desc="后端组员工兼属研发部",
        ),
        "",
    ]

    lines.append("-- 账户-角色 / 用户组")
    for i, (aid, rid) in enumerate(
        [
            (ACC_IAM, ROLE_IAM_ADMIN),
            (ACC_ALL, ROLE_BIZ_ALL),
            (ACC_DEPT, ROLE_BIZ_DEPT),
            (ACC_SELF, ROLE_BIZ_SELF),
            (ACC_CHILD, ROLE_BIZ_CHILD),
            (ACC_READONLY, ROLE_IAM_READONLY),
            (ACC_BE, ROLE_BIZ_DEPT),
            (ACC_QA, ROLE_BIZ_CHILD),
        ],
        start=1,
    ):
        lines.append(
            sql_rel(
                f"820000000000050{i}",
                "ACCOUNT",
                aid,
                "ACCOUNT_ROLE",
                "ROLE",
                rid,
                sort=i,
            )
        )
    lines += [
        sql_rel(
            "8200000000000520",
            "ACCOUNT",
            ACC_GROUP,
            "ACCOUNT_GROUP",
            "GROUP",
            GRP_RD,
        ),
        sql_rel(
            "8200000000000521",
            "ACCOUNT",
            ACC_DEPT,
            "ACCOUNT_GROUP",
            "GROUP",
            GRP_RD,
        ),
        sql_rel(
            "8200000000000522",
            "ACCOUNT",
            ACC_SELF,
            "ACCOUNT_GROUP",
            "GROUP",
            GRP_FE,
        ),
        sql_rel(
            "8200000000000523",
            "ACCOUNT",
            ACC_ALL,
            "ACCOUNT_GROUP",
            "GROUP",
            GRP_MKT,
        ),
        sql_rel(
            "8200000000000524",
            "ACCOUNT",
            ACC_IAM,
            "ACCOUNT_GROUP",
            "GROUP",
            GRP_IAM,
        ),
        sql_rel(
            "8200000000000525",
            "ACCOUNT",
            ACC_READONLY,
            "ACCOUNT_GROUP",
            "GROUP",
            GRP_IAM,
        ),
        sql_rel(
            "8200000000000526",
            "ACCOUNT",
            ACC_CHILD,
            "ACCOUNT_GROUP",
            "GROUP",
            GRP_RD,
            desc="研发副主管加入研发组成员",
        ),
        sql_rel(
            "8200000000000527",
            "ACCOUNT",
            ACC_BE,
            "ACCOUNT_GROUP",
            "GROUP",
            GRP_BE,
        ),
        sql_rel(
            "8200000000000528",
            "ACCOUNT",
            ACC_BE,
            "ACCOUNT_GROUP",
            "GROUP",
            GRP_RD,
            desc="后端组兼属研发大组",
        ),
        sql_rel(
            "8200000000000529",
            "ACCOUNT",
            ACC_QA,
            "ACCOUNT_GROUP",
            "GROUP",
            GRP_QA,
        ),
        "",
    ]

    lines.append("-- 角色-资源授权")
    rel_id = 8200000000000600
    for item in [
        (ROLE_IAM_ADMIN, RES_WORKSPACE, "工作台"),
        (ROLE_IAM_ADMIN, RES_ORG, "组织权限"),
        (ROLE_BIZ_ALL, RES_WORKSPACE, "工作台"),
        (ROLE_BIZ_ALL, RES_BIZ_ACTIVITY, "活动"),
        (ROLE_BIZ_DEPT, RES_WORKSPACE, "工作台"),
        (ROLE_BIZ_DEPT, RES_BIZ_CATALOG, "目录"),
        (ROLE_BIZ_SELF, RES_WORKSPACE, "工作台"),
        (ROLE_BIZ_SELF, RES_BIZ_ORDER, "订单"),
        (ROLE_BIZ_CHILD, RES_WORKSPACE, "工作台"),
        (ROLE_BIZ_CHILD, RES_BIZ_KNOWLEDGE, "知识分类"),
        (ROLE_IAM_READONLY, RES_ACCOUNT_MENU, "账号列表"),
        (ROLE_IAM_READONLY, RES_ACCOUNT_DETAIL_BTN, "账号详情"),
    ]:
        rid, res, desc = item[0], item[1], item[2]
        mode = item[3] if len(item) > 3 else "CASCADE"
        rel_id += 1
        lines.append(
            sql_rel(
                str(rel_id),
                "ROLE",
                rid,
                "SUBJECT_RESOURCE_GRANT",
                "RESOURCE",
                res,
                grant_mode=mode,
                desc=desc,
            )
        )
    lines.append("")

    lines.append("-- 业务样例数据")
    lines += [
        (
            "INSERT INTO `cg_test_activity` VALUES ('900000000000000002', 'ACT-RD-01', "
            "'研发部活动', 'TRAINING', 'OFFLINE', 'ENABLED', NULL, '研发部 ALL 样例', "
            "'2026-08-01 09:00:00', '2026-08-01 18:00:00', 50, 0.00, 1, 0, '{}', '{}', "
            f"'{TS}', '{ACC_ALL}', '{TS}', '{ACC_ALL}', '{DEPT_RD}');"
        ),
        (
            "INSERT INTO `cg_test_activity` VALUES ('900000000000000003', 'ACT-MKT-01', "
            "'市场部活动', 'MARKETING', 'ONLINE', 'ENABLED', NULL, '市场部 ALL 样例', "
            "'2026-08-02 09:00:00', '2026-08-02 18:00:00', 100, 0.00, 1, 0, '{}', '{}', "
            f"'{TS}', '{ACC_ALL}', '{TS}', '{ACC_ALL}', '{DEPT_MARKET}');"
        ),
        (
            "INSERT INTO `cg_test_catalog` VALUES ('900000000000000104', '900000000000000101', "
            "'RD-CAT', '研发目录', 'SYSTEM', 'ENABLED', 11, 1, 'folder', '研发部目录', "
            f"'{{}}', '{TS}', '{ACC_DEPT}', '{TS}', '{ACC_DEPT}', '{DEPT_RD}');"
        ),
        (
            "INSERT INTO `cg_test_catalog` VALUES ('900000000000000105', '900000000000000101', "
            "'MKT-CAT', '市场目录', 'BUSINESS', 'ENABLED', 21, 1, 'folder', '市场部目录', "
            f"'{{}}', '{TS}', '{ACC_DEPT}', '{TS}', '{ACC_DEPT}', '{DEPT_MARKET}');"
        ),
        (
            "INSERT INTO `cg_test_catalog` VALUES ('900000000000000106', '900000000000000101', "
            "'BE-CAT', '后端目录', 'SYSTEM', 'ENABLED', 12, 1, 'folder', '后端组目录', "
            f"'{{}}', '{TS}', '{ACC_BE}', '{TS}', '{ACC_BE}', '{DEPT_RD_BE}');"
        ),
        (
            "INSERT INTO `cg_test_order` VALUES ('900000000000000202', 'CG-ORDER-RD', "
            "'研发订单', '张三', '13800002001', 'PAID', 'NORMAL', '2026-08-10 10:00:00', "
            "NULL, 100.00, 1, 0, '{}', '研发部订单', '{}', "
            f"'{TS}', '{ACC_DEPT}', '{TS}', '{ACC_DEPT}', '{DEPT_RD}');"
        ),
        (
            "INSERT INTO `cg_test_order` VALUES ('900000000000000203', 'CG-ORDER-SELF', "
            "'本人订单', '李四', '13800002002', 'PAID', 'NORMAL', '2026-08-11 10:00:00', "
            "NULL, 200.00, 1, 0, '{}', '仅本人可见', '{}', "
            f"'{TS}', '{ACC_SELF}', '{TS}', '{ACC_SELF}', NULL);"
        ),
        (
            "INSERT INTO `cg_test_order` VALUES ('900000000000000204', 'CG-ORDER-OTHER', "
            "'他人订单', '王五', '13800002003', 'PAID', 'NORMAL', '2026-08-12 10:00:00', "
            "NULL, 300.00, 1, 0, '{}', '他人创建', '{}', "
            f"'{TS}', '{ACC_ALL}', '{TS}', '{ACC_ALL}', NULL);"
        ),
        (
            "INSERT INTO `cg_test_knowledge_category` VALUES ('900000000000000303', NULL, "
            "'RD-KB', '研发知识库', 'ENABLED', 2, 1, '研发根分类', '{}', "
            f"'{TS}', '{ACC_CHILD}', '{TS}', '{ACC_CHILD}', '{DEPT_RD}');"
        ),
        (
            "INSERT INTO `cg_test_knowledge_category` VALUES ('900000000000000304', "
            "'900000000000000303', 'RD-FE-KB', '前端知识库', 'ENABLED', 1, 1, '前端子分类', "
            f"'{{}}', '{TS}', '{ACC_CHILD}', '{TS}', '{ACC_CHILD}', '{DEPT_RD_FE}');"
        ),
        (
            "INSERT INTO `cg_test_knowledge_category` VALUES ('900000000000000305', NULL, "
            "'MKT-KB', '市场知识库', 'ENABLED', 3, 1, '市场根分类', '{}', "
            f"'{TS}', '{ACC_CHILD}', '{TS}', '{ACC_CHILD}', '{DEPT_MARKET}');"
        ),
        "",
        f"{MARKER} END",
    ]
    return "\n".join(lines)


def main() -> None:
    text = DB.read_text(encoding="utf-8")
    if MARKER in text:
        text = re.sub(
            re.escape(MARKER) + r".*?" + re.escape(MARKER) + r" END\n?",
            "",
            text,
            flags=re.DOTALL,
        )
    text = patch_data_scopes(text)
    text = text.rstrip() + "\n\n" + build_block() + "\n"
    DB.write_text(text, encoding="utf-8")
    print(f"patched {DB}")


if __name__ == "__main__":
    main()
