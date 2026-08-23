"""资源 code → 三段式权限 key 映射（与后端 @SaCheckPermission 一致）。"""

from __future__ import annotations

import re

VALID_PERM = re.compile(r"^[a-z][a-z0-9]*:[a-z][a-z0-9]*:[a-z][a-z0-9]*$")

# 资源 code 与权限 key 命名不一致时的显式映射
CODE_OVERRIDES: dict[str, str] = {
    "workspace-overview": "workspace:overview:view",
    "sys-session-tokenlist": "auth:session:tokenlist",
    "sys-session-exit": "auth:session:exit",
    "sys-session-tokenexit": "auth:session:tokenexit",
    "sys-login-log-detail": "sys:audit:detail",
    "sys-real-name-review": "sys:realname:verify",
    "sys-job-log": "sys:joblog:page",
    "iam-resource-list": "iam:resource:list",
    "iam-resource-grant": "iam:resource:grant",
    "iam-clientresource-list": "iam:clientresource:list",
    "iam-clientresource-grant": "iam:clientresource:grant",
}

# grant 类按钮：资源 code 后缀与权限 action 不一致
IAM_GRANT_PERM: dict[str, str] = {
    "account-grant-role": "iam:account:grantrole",
    "account-grant-group": "iam:account:grantgroup",
    "account-grant-dept": "iam:account:grantdept",
    "account-grant-resource": "iam:account:grantresource",
    "account-grant-client-resource": "iam:account:grantclientresource",
    "group-grant-user": "iam:group:grantuser",
    "group-grant-role": "iam:group:grantrole",
    "group-grant-resource": "iam:group:grantresource",
    "group-grant-client-resource": "iam:group:grantclientresource",
    "role-grant-resource": "iam:role:grantresource",
    "role-grant-client-resource": "iam:role:grantclientresource",
    "role-grant-user": "iam:role:grantuser",
}

# 仅处理资源 code 中带连字符、权限 key 需合并的少数场景
SYS_ENTITY_ALIAS: dict[str, str] = {
    "login-log": "audit",
    "real-name": "realname",
    "job-log": "joblog",
}


def _normalize_entity(module: str, entity: str) -> str:
    if module == "sys":
        return SYS_ENTITY_ALIAS.get(entity, entity.replace("-", ""))
    return entity.replace("-", "")


def code_to_perm(code: str) -> str | None:
    if code in CODE_OVERRIDES:
        return CODE_OVERRIDES[code]
    if code.startswith(("sys:", "iam:", "biz:", "auth:", "workspace:")):
        return code
    if code.startswith("workspace-"):
        return CODE_OVERRIDES.get(code, "workspace:overview:view")
    if code.startswith("sys-"):
        body = code[4:]
        if "-" in body:
            entity, action = body.rsplit("-", 1)
            return f"sys:{_normalize_entity('sys', entity)}:{action}"
        return None
    if code.startswith("iam-"):
        body = code[4:]
        if body in IAM_GRANT_PERM:
            return IAM_GRANT_PERM[body]
        if "-" in body:
            entity, action = body.rsplit("-", 1)
            return f"iam:{_normalize_entity('iam', entity)}:{action}"
        return None
    if code.startswith("biz-"):
        body = code[4:]
        if "-" in body:
            entity, action = body.rsplit("-", 1)
            return f"biz:{_normalize_entity('biz', entity)}:{action}"
        return None
    return None
