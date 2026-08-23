#!/usr/bin/env python3
"""向 db.sql 写入内容运营演示种子（展示图、通知消息、反馈）。"""

from __future__ import annotations

import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DB = ROOT / "scripts" / "db.sql"
MARKER = "-- === CONTENT_DEMO_SEED (auto, do not edit) ==="

TS = "2026-08-23 09:00:00"
ACC_SUPER = "1"
ACC_IAM = "8200000000000201"
ACC_ALL = "8200000000000202"
ACC_READONLY = "8200000000000206"
ACC_PORTAL_BOB = "8200000000000211"
ACC_PORTAL_ALICE = "8200000000000212"
ACC_PORTAL_LEGACY = "7491847383584804864"

IMG_HOME = "https://images.unsplash.com/photo-1497366216548-37526070297c?w=1600&h=700&fit=crop"
IMG_TEAM = "https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=1600&h=700&fit=crop"
IMG_DATA = "https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=1600&h=700&fit=crop"
IMG_NEWS = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=1600&h=700&fit=crop"
IMG_SECURE = "https://images.unsplash.com/photo-1563986768609-322da13575f3?w=1600&h=700&fit=crop"
IMG_ADMIN = "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=1600&h=700&fit=crop"


def sql_banner(
    bid: str,
    title: str,
    image: str,
    url: str | None,
    link_type: str,
    summary: str,
    desc: str,
    category: str,
    btype: str,
    position: str,
    targets: str,
    sort: int,
) -> str:
    url_val = f"'{url}'" if url else "NULL"
    desc_sql = desc.replace("'", "''")
    summary_sql = summary.replace("'", "''")
    title_sql = title.replace("'", "''")
    return (
        f"INSERT INTO `sys_banner` VALUES ('{bid}', '{title_sql}', '{image}', {url_val}, "
        f"'{link_type}', '{summary_sql}', '{desc_sql}', '{category}', '{btype}', '{position}', "
        f"'{targets}', {sort}, 0, 'ENABLED', NULL, NULL, '{TS}', '{ACC_SUPER}', "
        f"'{TS}', '{ACC_SUPER}');"
    )


def sql_notice(
    nid: str,
    kind: str,
    title: str,
    content: str,
    severity: str,
    targets: str,
    status: str = "PUBLISHED",
    category: str = "SYSTEM",
    target_scope: str = "ALL",
    content_type: str = "text",
    publish_locations: str = "{}",
    is_pinned: int = 0,
    view_count: int = 0,
    publish_at: str = TS,
) -> str:
    title_sql = title.replace("'", "''")
    content_sql = content.replace("'", "''")
    return (
        f"INSERT INTO `sys_notice` VALUES ('{nid}', '{kind}', '{title_sql}', '{content_sql}', "
        f"'{content_type}', '{category}', '{severity}', '{target_scope}', '{targets}', '[]', "
        f"'[]', '[]', '{publish_locations}', {is_pinned}, NULL, NULL, NULL, NULL, NULL, "
        f"'{status}', '{publish_at}', NULL, NULL, {view_count}, '{{}}', '{TS}', '{ACC_SUPER}', "
        f"'{TS}', '{ACC_SUPER}');"
    )


def sql_feedback(
    fid: str,
    title: str,
    content: str,
    category: str,
    contact: str,
    status: str,
    account_type: str,
    account_id: str,
    reply: str | None = None,
    replied_by: str | None = None,
    replied_at: str | None = None,
) -> str:
    title_sql = title.replace("'", "''")
    content_sql = content.replace("'", "''")
    contact_sql = contact.replace("'", "''")
    reply_val = f"'{reply.replace(chr(39), chr(39)*2)}'" if reply else "NULL"
    replied_by_val = f"'{replied_by}'" if replied_by else "NULL"
    replied_at_val = f"'{replied_at}'" if replied_at else "NULL"
    return (
        f"INSERT INTO `sys_feedback` VALUES ('{fid}', '{title_sql}', '{content_sql}', "
        f"'{category}', '{contact_sql}', '[]', '{status}', {reply_val}, {replied_by_val}, "
        f"{replied_at_val}, '{account_type}', '{account_id}', '{TS}', '{account_id}', "
        f"'{TS}', '{account_id}');"
    )


def build_block() -> str:
    lines: list[str] = [MARKER, "", "-- 展示图"]
    lines += [
        sql_banner(
            "8300000000000101",
            "门户首页欢迎",
            IMG_HOME,
            "/",
            "ROUTE",
            "欢迎使用 HEI 门户",
            "门户首页顶部轮播",
            "HOME",
            "CAROUSEL",
            "HOME_TOP",
            '["PORTAL"]',
            5,
        ),
        sql_banner(
            "8300000000000102",
            "活动中心",
            IMG_NEWS,
            "/activities",
            "ROUTE",
            "查看最新活动与报名",
            "门户活动推广位",
            "HOME",
            "CAROUSEL",
            "HOME_TOP",
            '["PORTAL"]',
            15,
        ),
        sql_banner(
            "8300000000000103",
            "安全合规提示",
            IMG_SECURE,
            None,
            "NONE",
            "请妥善保管账号密码",
            "门户安全宣传卡片",
            "HOME",
            "CARD",
            "HOME_MIDDLE",
            '["PORTAL"]',
            25,
        ),
        sql_banner(
            "8300000000000104",
            "管理端工作台",
            IMG_ADMIN,
            "/workspace",
            "ROUTE",
            "快捷进入工作台",
            "管理端顶部运营位",
            "ADMIN_DASHBOARD",
            "CAROUSEL",
            "ADMIN_TOP",
            '["ADMIN"]',
            5,
        ),
        sql_banner(
            "8300000000000105",
            "IAM 权限指引",
            IMG_DATA,
            "/iam/account",
            "ROUTE",
            "组织权限配置入口",
            "管理端 IAM 引导",
            "ADMIN_DASHBOARD",
            "CARD",
            "ADMIN_SIDE",
            '["ADMIN"]',
            15,
        ),
        sql_banner(
            "8300000000000106",
            "双端同步公告",
            IMG_TEAM,
            "/sys/notice",
            "ROUTE",
            "管理端与门户同步消息",
            "双端可见展示图",
            "HOME",
            "BANNER",
            "GLOBAL_TOP",
            '["ADMIN", "PORTAL"]',
            20,
        ),
        "",
        "-- 通知消息",
        sql_notice(
            "8300000000000201",
            "NOTIFICATION",
            "门户新手指引",
            "完成个人资料填写后可解锁更多功能，建议绑定邮箱与手机号。",
            "INFO",
            '["PORTAL"]',
        ),
        sql_notice(
            "8300000000000202",
            "NOTIFICATION",
            "Bob 的专属通知",
            "这是面向门户账户的示例通知，用于演示消息列表。",
            "INFO",
            '["PORTAL"]',
            category="SYSTEM",
        ),
        sql_notice(
            "8300000000000203",
            "ANNOUNCEMENT",
            "门户春季活动",
            "春季主题活动已开启，欢迎参与线上打卡与积分兑换。",
            "SUCCESS",
            '["PORTAL"]',
            publish_locations='{"center": true, "workspace": true}',
            view_count=3,
        ),
        sql_notice(
            "8300000000000204",
            "NOTIFICATION",
            "管理端 IAM 更新",
            "角色、用户组与部门授权演示数据已就绪，可在组织权限模块查看。",
            "INFO",
            '["ADMIN"]',
        ),
        sql_notice(
            "8300000000000205",
            "NOTIFICATION",
            "审计日志提醒",
            "关键操作将写入审计日志，请管理员定期查看异常登录与权限变更。",
            "WARNING",
            '["ADMIN"]',
            category="SECURITY",
        ),
        sql_notice(
            "8300000000000206",
            "ANNOUNCEMENT",
            "管理端版本说明",
            "本次演示环境包含完整 IAM 与内容运营模块，仅供本地开发验证。",
            "INFO",
            '["ADMIN"]',
            publish_locations='{"workspace": true}',
            is_pinned=1,
            view_count=2,
        ),
        sql_notice(
            "8300000000000207",
            "NOTIFICATION",
            "双端系统通知",
            "管理端与门户均可收到本条通知，用于验证目标账户类型多选。",
            "INFO",
            '["ADMIN", "PORTAL"]',
        ),
        sql_notice(
            "8300000000000208",
            "NOTIFICATION",
            "待处理反馈提醒",
            "有新的用户反馈待处理，请前往反馈管理查看。",
            "WARNING",
            '["ADMIN"]',
            category="SYSTEM",
        ),
        "",
        "-- 反馈（按账户类型分组演示）",
        sql_feedback(
            "8300000000000301",
            "门户登录体验",
            "希望增加短信验证码登录选项。",
            "SUGGESTION",
            "bob@demo.local",
            "PENDING",
            "PORTAL",
            ACC_PORTAL_BOB,
        ),
        sql_feedback(
            "8300000000000302",
            "活动页面加载慢",
            "活动列表在弱网下加载超过 5 秒。",
            "BUG",
            "13800001001",
            "PENDING",
            "PORTAL",
            ACC_PORTAL_BOB,
        ),
        sql_feedback(
            "8300000000000303",
            "个人中心样式问题",
            "头像上传后预览区域会抖动。",
            "BUG",
            "alice@demo.local",
            "RESOLVED",
            "PORTAL",
            ACC_PORTAL_ALICE,
            reply="已记录，将在下个版本修复。",
            replied_by=ACC_SUPER,
            replied_at=TS,
        ),
        sql_feedback(
            "8300000000000304",
            "希望增加深色模式",
            "门户夜间使用较刺眼，建议支持深色主题。",
            "SUGGESTION",
            "alice@demo.local",
            "PENDING",
            "PORTAL",
            ACC_PORTAL_ALICE,
        ),
        sql_feedback(
            "8300000000000305",
            "历史门户反馈",
            "早期门户测试账号提交的反馈样例。",
            "GENERAL",
            "13800000000",
            "RESOLVED",
            "PORTAL",
            ACC_PORTAL_LEGACY,
            reply="ok",
            replied_by=ACC_SUPER,
            replied_at="2026-08-08 13:40:49.757811",
        ),
        sql_feedback(
            "8300000000000306",
            "部门树展示优化",
            "建议部门详情中直接展示成员数量。",
            "SUGGESTION",
            "iam-admin@demo.local",
            "PENDING",
            "ADMIN",
            ACC_IAM,
        ),
        sql_feedback(
            "8300000000000307",
            "角色授权交互",
            "授权用户弹窗希望支持按部门筛选。",
            "SUGGESTION",
            "biz-all@demo.local",
            "PENDING",
            "ADMIN",
            ACC_ALL,
        ),
        sql_feedback(
            "8300000000000308",
            "只读账号权限确认",
            "只读账号不应看到删除按钮，请确认前端权限控制。",
            "BUG",
            "readonly@demo.local",
            "RESOLVED",
            "ADMIN",
            ACC_READONLY,
            reply="已确认，仅隐藏无权限按钮。",
            replied_by=ACC_SUPER,
            replied_at=TS,
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
    text = text.rstrip() + "\n\n" + build_block() + "\n"
    DB.write_text(text, encoding="utf-8")
    print(f"patched {DB}")


if __name__ == "__main__":
    main()
