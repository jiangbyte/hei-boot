"""Dialect / smoke hard checks (aligned with hei-gin / hei-fastapi)."""

from __future__ import annotations

from dataclasses import dataclass


@dataclass(frozen=True)
class HardCheck:
    name: str
    method: str
    path: str
    body: str = ""


def hard_checks() -> list[HardCheck]:
    return [
        HardCheck("health_live", "GET", "/api/v1/internal/health/live"),
        HardCheck("health_ready", "GET", "/api/v1/internal/health/ready"),
        HardCheck("dashboard_overview", "GET", "/api/v1/admin/dashboard/overview"),
        HardCheck("roles_page", "GET", "/api/v1/admin/sys/roles/page?current=1&size=5&name=admin"),
        HardCheck("banners_page", "GET", "/api/v1/admin/sys/banners/page?current=1&size=5"),
        HardCheck("banners_list", "GET", "/api/v1/admin/sys/banners/list?position=HOME_TOP"),
        HardCheck("notices_page", "GET", "/api/v1/admin/sys/notices/page?current=1&size=5"),
        HardCheck("notices_my_page", "GET", "/api/v1/admin/sys/notices/my-page?current=1&size=5"),
        HardCheck("notices_unread", "GET", "/api/v1/admin/sys/notices/unread-count"),
        HardCheck("accounts_page", "GET", "/api/v1/admin/sys/accounts/page?current=1&size=5&account=super"),
        HardCheck("depts_tree", "GET", "/api/v1/admin/sys/depts/tree"),
        HardCheck("dicts_tree", "GET", "/api/v1/admin/sys/dicts/tree"),
        HardCheck("resources_tree", "GET", "/api/v1/admin/sys/resources/tree"),
        HardCheck("resources_current", "GET", "/api/v1/admin/sys/resources/current"),
        HardCheck("config_page", "GET", "/api/v1/admin/sys/config/page?current=1&size=5"),
        HardCheck("audit_page", "GET", "/api/v1/admin/sys/audit/page?current=1&size=5"),
        HardCheck("codegen_tables", "GET", "/api/v1/admin/sys/codegen/tables"),
        HardCheck("codegen_page", "GET", "/api/v1/admin/sys/codegen/page?current=1&size=5"),
        HardCheck("job_logs_page", "GET", "/api/v1/admin/sys/job-logs/page?current=1&size=5"),
        HardCheck("weak_password_page", "GET", "/api/v1/admin/sys/weak-password/page?current=1&size=5"),
        HardCheck("feedbacks_page", "GET", "/api/v1/admin/sys/feedbacks/page?current=1&size=5"),
        HardCheck("cg_activity_page", "GET", "/api/v1/admin/biz/cg-test-activity/page?current=1&size=5"),
        HardCheck("cg_catalog_tree", "GET", "/api/v1/admin/biz/cg-test-catalog/tree"),
        HardCheck("cg_order_page", "GET", "/api/v1/admin/biz/cg-test-order/page?current=1&size=5"),
        HardCheck("portal_banners_list", "GET", "/api/v1/portal/sys/banners/list?position=HOME_TOP"),
        HardCheck("portal_dicts_tree", "GET", "/api/v1/portal/sys/dicts/tree"),
        HardCheck("portal_notices_list", "GET", "/api/v1/portal/sys/notices/list"),
        HardCheck("portal_notices_my_page", "GET", "/api/v1/portal/sys/notices/my-page?current=1&size=5"),
        HardCheck("admin_me", "GET", "/api/v1/admin/me"),
        HardCheck("portal_me", "GET", "/api/v1/portal/me"),
    ]
