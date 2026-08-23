#!/usr/bin/env python3
"""从 scripts/db.sql（MySQL）生成 scripts/db_column_labels.py（权威字段注释）。"""

from __future__ import annotations

import json
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
SRC = ROOT / "scripts" / "db.sql"
OUT = ROOT / "scripts" / "db_column_labels.py"

# 表级中文注释（db.sql 中 COMMENT 未写表级注释时用于 enhance）
TABLE_LABELS = {
    "cg_test_activity": "代码生成测试-活动",
    "cg_test_catalog": "代码生成测试-目录",
    "cg_test_knowledge_category": "代码生成测试-知识分类",
    "cg_test_knowledge_doc": "代码生成测试-知识文档",
    "cg_test_order": "代码生成测试-订单",
    "cg_test_order_item": "代码生成测试-订单明细",
    "profile_user_admin": "管理端用户资料",
    "profile_user_portal": "门户用户资料",
    "profile_identity": "账号实名认证快照",
    "real_name_case": "实名业务工单",
    "real_name_case_record": "实名业务工单流水",
    "sys_account": "系统账号",
    "sys_account_identity": "账号身份标识",
    "sys_account_oauth_binding": "三方登录绑定",
    "sys_account_password_history": "账号密码历史",
    "sys_alert_log": "系统告警日志",
    "sys_banner": "Banner 轮播",
    "sys_client_module": "客户端模块",
    "sys_client_resource": "客户端资源",
    "sys_codegen_field": "代码生成字段配置",
    "sys_codegen_plan": "代码生成方案",
    "sys_config": "系统动态配置",
    "sys_workspace_shortcut": "工作台个人快捷应用",
    "sys_dept": "部门",
    "sys_dict": "数据字典",
    "sys_feedback": "意见反馈",
    "sys_file": "文件元数据",
    "sys_group": "用户组",
    "sys_iam_relation": "IAM 关系",
    "sys_job": "内置任务",
    "sys_job_log": "任务执行日志",
    "sys_notice": "系统公告/通知",
    "sys_notice_read": "公告已读记录",
    "sys_operation_audit_log": "操作审计日志",
    "sys_operation_audit_outbox": "操作审计 Outbox",
    "sys_position": "职位",
    "sys_resource": "菜单/按钮/API 资源",
    "sys_resource_module": "资源模块",
    "sys_role": "角色",
    "sys_weak_password": "弱口令库",
}

COMMON_COLUMN_LABELS: dict[str, str] = {
    "id": "主键ID",
    "created_at": "创建时间",
    "created_by": "创建人（账户ID）",
    "updated_at": "更新时间",
    "updated_by": "更新人（账户ID）",
    "extra": "扩展信息（JSON）",
    "status": "状态",
    "sort": "排序号（越小越靠前）",
    "remark": "备注说明",
    "description": "描述说明",
    "name": "名称",
    "code": "编码",
    "category": "分类",
    "type": "类型",
    "title": "标题",
    "content": "内容",
    "summary": "摘要",
    "icon": "图标标识",
    "color": "颜色值",
    "path": "路径",
    "account_id": "账户ID",
    "account_type": "账户类型：ADMIN（管理端）/ PORTAL（门户端）",
    "parent_id": "父级ID",
    "module_id": "所属模块ID",
    "owner_dept_id": "所属部门ID（数据权限范围）",
    "is_visible": "是否可见：1 可见 / 0 隐藏",
    "enabled": "是否启用：1 启用 / 0 停用",
    "success": "是否成功：1 成功 / 0 失败",
    "verified": "是否已验证：1 是 / 0 否",
    "is_primary": "是否主记录：1 是 / 0 否",
    "is_builtin": "是否内置：1 内置不可删 / 0 可维护",
    "is_public": "是否公开：1 公开 / 0 不公开",
    "is_pinned": "是否置顶：1 置顶 / 0 不置顶",
    "is_cache": "是否缓存路由：1 缓存 / 0 不缓存",
    "is_affix": "是否固定标签页：1 固定 / 0 不固定",
    "is_virtual": "是否虚拟组织：1 虚拟 / 0 实体",
    "is_top": "是否置顶：1 置顶 / 0 不置顶",
    "is_gift": "是否赠品：1 是 / 0 否",
    "need_approval": "是否需要审批：1 需要 / 0 不需要",
    "need_invoice": "是否开票：1 需要 / 0 不需要",
    "view_count": "浏览/查看次数",
    "duration_ms": "耗时（毫秒）",
    "attempts": "重试次数",
    "payload": "事件载荷（JSON）",
    "params": "执行参数（JSON）",
    "before_data": "变更前数据（JSON）",
    "after_data": "变更后数据（JSON）",
    "raw_profile": "第三方原始资料（JSON）",
    "ext_json": "扩展配置（JSON）",
    "rule_config": "规则配置（JSON）",
    "item_config": "明细配置（JSON）",
    "invoice_config": "发票配置（JSON）",
    "settings": "展示设置（JSON）",
    "publish_locations": "发布位置配置（JSON）",
    "target_account_types": "目标账户类型列表（JSON 数组）",
    "target_account_ids": "目标账户ID列表（JSON 数组）",
    "target_dept_ids": "目标部门ID列表（JSON 数组）",
    "target_role_ids": "目标角色ID列表（JSON 数组）",
    "attachment_ids": "附件ID列表（JSON 数组）",
    "attach_object_names": "附件 object_name 列表（JSON 数组）",
    "custom_scope_dept_ids": "自定义数据范围部门ID列表（JSON 数组）",
    "ip": "客户端/实例 IP 地址",
    "user_agent": "客户端 User-Agent",
    "request_id": "请求链路 ID（Trace）",
    "error_message": "错误信息",
    "provider": "第三方服务提供方",
    "provider_order_no": "第三方业务订单号",
    "document_type": "证件类型：ID_CARD/PASSPORT 等",
    "verify_channel": "认证通道：THIRD_PARTY（三方）/ MANUAL（人工）",
    "business_type": "业务类型",
    "password_hash": "密码哈希值（不可逆）",
}

TABLE_COLUMN_LABELS: dict[str, dict[str, str]] = {
    "sys_account": {
        "password_hash": "登录密码哈希值（bcrypt 等）",
        "account_type": "账户类型：ADMIN（管理端）/ PORTAL（门户端）",
        "account_status": "账户状态：ACTIVE（正常）/ LOCKED（锁定）/ CANCELLED（已注销）",
        "cancelled_at": "账号注销完成时间",
        "cancelled_by": "执行注销的操作人账户ID",
        "cancel_reason": "注销原因说明",
        "cancel_notify_email": "注销前快照：通知邮箱（身份清理前保留）",
        "cancel_notify_phone": "注销前快照：通知手机号（身份清理前保留）",
        "last_login_ip": "上一次成功登录 IP",
        "last_login_address": "上一次成功登录地理位置",
        "last_login_time": "上一次成功登录时间",
        "last_login_device": "上一次成功登录设备标识",
        "latest_login_ip": "最近一次成功登录 IP",
        "latest_login_address": "最近一次成功登录地理位置",
        "latest_login_time": "最近一次成功登录时间",
        "latest_login_device": "最近一次成功登录设备标识",
    },
    "sys_account_identity": {
        "identity_type": "身份类型：USERNAME/EMAIL/PHONE 等",
        "identifier": "登录标识值（用户名/邮箱/手机号）",
        "verified": "标识是否已完成验证：1 是 / 0 否",
        "is_primary": "是否主登录标识：1 主标识 / 0 次标识",
        "bind_status": "绑定状态：BOUND/UNBOUND/PENDING 等",
    },
    "sys_account_oauth_binding": {
        "provider": "OAuth 提供方：wechat/github/google 等",
        "open_id": "第三方平台 OpenID",
        "union_id": "第三方平台 UnionID（跨应用统一标识）",
        "nickname": "第三方账号昵称快照",
        "avatar": "第三方账号头像 URL 快照",
        "bound_at": "与本系统账号绑定时间",
    },
    "sys_account_password_history": {
        "changed_by": "密码变更操作人（账户ID 或 system）",
        "change_reason": "变更原因：register/admin_reset/self_reset/password_expired",
        "created_at": "密码写入历史时间",
    },
    "sys_alert_log": {
        "rule_name": "触发告警的规则名称",
        "severity": "严重级别：INFO/WARNING/CRITICAL",
        "summary": "告警摘要（展示用）",
        "details": "告警详情上下文（JSON）",
        "notified_via": "通知渠道：email/webhook 等",
        "created_at": "告警产生/通知时间",
    },
    "sys_banner": {
        "image": "Banner 图片 object_name（由服务层解析访问 URL）",
        "url": "点击跳转链接地址",
        "link_type": "链接类型（字典 BANNER_LINK_TYPE）",
        "category": "Banner 分类（字典 BANNER_CATEGORY）",
        "type": "Banner 类型（字典 BANNER_TYPE）",
        "position": "展示位置（字典 BANNER_POSITION）",
        "target_account_types": "可见账户类型：ADMIN/PORTAL（JSON 数组）",
        "interaction_count": "用户交互次数统计",
        "start_at": "开始展示时间",
        "end_at": "结束展示时间",
        "status": "Banner 状态：ENABLED/DISABLED 等",
    },
    "sys_client_module": {
        "account_type": "适用账户体系：ADMIN/PORTAL",
        "description": "客户端模块描述",
        "status": "模块状态：ENABLED/DISABLED",
    },
    "sys_client_resource": {
        "parent_id": "父级客户端资源ID",
        "resource_type": "资源类型：MENU/BUTTON/API 等",
        "module_id": "所属客户端模块ID",
        "path": "前端路由路径",
        "component": "前端组件路径",
        "redirect": "路由重定向地址",
        "href": "外链跳转地址",
        "layout": "页面布局类型",
        "description": "客户端资源描述",
        "status": "资源状态：ENABLED/DISABLED",
    },
    "sys_codegen_field": {
        "plan_id": "所属代码生成方案ID",
        "table_role": "表角色：MASTER/SUB 等",
        "column_name": "数据库列名",
        "label": "字段展示标签（通常取自表注释）",
        "db_type": "数据库物理类型",
        "value_type": "语义值类型：str/int/bool/datetime/dict 等",
        "ui_type": "前端 UI 类型：string/number/boolean 等",
        "widget": "表单控件类型",
        "dict_code": "关联数据字典编码",
        "query_operator": "列表查询运算符：eq/like/between 等",
        "in_table": "是否在表格列展示：1 是 / 0 否",
        "in_form": "是否在表单展示：1 是 / 0 否",
        "in_detail": "是否在详情展示：1 是 / 0 否",
        "in_query": "是否作为查询条件：1 是 / 0 否",
        "primary_key": "是否主键列：1 是 / 0 否",
        "required": "是否必填：1 是 / 0 否",
        "unique_flag": "是否唯一：1 是 / 0 否",
        "nullable": "是否允许为空：1 可空 / 0 非空",
        "max_length": "字段最大长度限制",
    },
    "sys_codegen_plan": {
        "gen_type": "生成类型：CRUD/TREE/SUB_TABLE 等",
        "table_name": "主表数据库名",
        "pk_column": "主表主键列名",
        "entity_name": "生成的主实体类名",
        "module_path": "后端模块包路径",
        "business_name": "主业务中文名",
        "api_prefix": "REST API 路径前缀",
        "permission_prefix": "权限标识前缀",
        "resource_module_id": "挂载的资源模块ID",
        "parent_resource_id": "挂载的父菜单资源ID",
        "menu_name": "生成菜单名称",
        "menu_path": "生成菜单路由路径",
        "component_path": "生成前端组件路径",
        "tree_parent_field": "树表父级字段名",
        "tree_label_field": "树节点展示字段名",
        "sub_table": "子表数据库名",
        "sub_pk": "子表主键列名",
        "sub_foreign_key": "子表外键列名",
        "sub_entity_name": "子实体类名",
        "sub_business_name": "子业务中文名",
        "description": "代码生成方案描述",
    },
    "sys_config": {
        "config_key": "配置项唯一键",
        "config_value": "配置项值（按 value_type 解析）",
        "category": "配置分类/分组",
        "sort_code": "同分类下排序码",
        "value_type": "值类型：STRING/JSON/BOOL/NUMBER",
        "label": "配置项展示名称",
        "scope": "作用域账户类型：GLOBAL/ADMIN/PORTAL",
        "scene": "业务场景编码",
        "is_builtin": "是否内置配置：1 内置不可删 / 0 可维护",
    },
    "sys_workspace_shortcut": {
        "account_id": "所属账号ID",
        "resource_id": "快捷菜单资源ID（sys_resource）",
    },
    "sys_dept": {
        "master_id": "部门主管账户ID",
        "deputy_master_id": "部门副主管账户ID",
        "name": "部门名称",
        "category": "部门类别/层级类型",
        "status": "部门状态：ENABLED/DISABLED",
    },
    "sys_dict": {
        "code": "字典项编码（同父级下唯一）",
        "label": "字典项展示标签",
        "value": "字典项实际值",
        "color": "前端展示颜色",
        "category": "字典分类：SYSTEM（系统）/ BUSINESS（业务）",
        "parent_id": "父级字典项ID",
        "status": "字典项状态：ENABLED/DISABLED",
    },
    "sys_feedback": {
        "attach_object_names": "用户上传附件 object_name 列表",
        "status": "反馈状态：PENDING/REPLIED/CLOSED 等",
        "reply": "管理员回复内容",
        "replied_by": "回复人账户ID",
        "replied_at": "管理员回复时间",
        "submitter_account_type": "提交人账户类型",
        "submitter_account_id": "提交人账户ID",
    },
    "sys_file": {
        "object_name": "对象存储中的对象键/路径",
        "original_name": "用户上传时的原始文件名",
        "storage_provider": "存储服务商：minio/rustfs/oss/s3",
        "bucket": "对象存储桶名称",
        "content_type": "MIME 类型",
        "size": "文件大小（字节）",
        "url": "文件访问 URL（可为签名地址）",
    },
    "sys_group": {
        "name": "用户组名称",
        "description": "用户组描述",
        "status": "用户组状态：ENABLED/DISABLED",
    },
    "sys_iam_relation": {
        "subject_type": "主体类型：ACCOUNT/DEPT/ROLE/GROUP/POSITION",
        "subject_id": "主体记录ID",
        "relation_type": "关系类型：MEMBER/GRANT/OWN 等",
        "target_type": "目标类型：RESOURCE/ROLE/DEPT/DATA_SCOPE 等",
        "target_id": "目标记录ID",
        "target_key": "目标业务标识（如权限 code）",
        "grant_mode": "授权模式：DIRECT/INHERIT 等",
        "data_scope": "数据范围：ALL/DEPT/DEPT_AND_CHILD/CUSTOM/SELF",
        "is_primary": "是否主关系/主岗位：1 是 / 0 否",
        "status": "关系状态：ACTIVE/INACTIVE",
        "description": "IAM 关系说明",
        "reason": "授权/变更原因",
        "expired_at": "关系失效时间（空表示永久）",
    },
    "sys_job": {
        "handler": "任务处理器标识（Boot 为 JobHandler 全限定类名）",
        "trigger_type": "触发类型：CRON（表达式）/ FIXED（固定间隔秒）",
        "trigger_config": "触发配置：Cron 表达式或间隔秒数",
        "last_run_time": "上次调度执行时间",
        "next_run_time": "下次计划执行时间",
        "last_result": "上次执行结果摘要",
        "enabled": "是否启用调度：1 启用 / 0 停用",
    },
    "sys_job_log": {
        "job_id": "关联任务定义ID（sys_job.id）",
        "started_at": "本次执行开始时间",
        "result": "执行结果摘要或错误堆栈摘要",
        "executor": "执行人：人工触发为账户ID，调度为 system",
        "process_id": "执行进程 PID",
        "app_dir": "执行实例应用目录",
    },
    "sys_notice": {
        "kind": "消息种类：NOTIFICATION（通知）/ ANNOUNCEMENT（公告）",
        "content_type": "内容格式：TEXT/HTML/MARKDOWN 等",
        "category": "通知分类编码",
        "severity": "重要等级：INFO/WARNING/ERROR 等",
        "target_scope": "投放范围：ALL/ACCOUNT/DEPT/ROLE 等",
        "sender_account_type": "发送方账户类型",
        "sender_account_id": "发送方账户ID",
        "source_type": "来源业务模块标识",
        "source_id": "来源业务记录ID",
        "status": "发布状态：DRAFT/PUBLISHED/REVOKED 等",
        "publish_at": "计划/实际发布时间",
        "revoked_at": "撤回时间",
        "expire_at": "过期时间（公告有效截止）",
        "pinned_until": "置顶截止时间",
    },
    "sys_notice_read": {
        "notice_id": "公告/通知ID（sys_notice.id）",
        "read_at": "用户阅读时间",
    },
    "sys_operation_audit_log": {
        "module": "业务模块编码（如 sys、iam）",
        "resource_type": "资源类型编码（如 SysAccount）",
        "resource_id": "被操作资源主键ID",
        "action": "操作动作编码",
        "summary": "操作内容可读摘要",
        "account_id": "操作人账户ID",
        "account_type": "操作人账户类型：ADMIN/PORTAL",
        "operator_name": "操作人昵称快照（写入时落库）",
        "action_name": "操作名称（前端展示）",
        "action_type": "操作类型：CREATE/UPDATE/DELETE/QUERY/EXPORT/LOGIN/LOGOUT/OTHER",
        "module_label": "操作模块中文展示名",
    },
    "sys_operation_audit_outbox": {
        "status": "投递状态：PENDING/CLAIMED/DONE/DEAD",
        "claimed_at": "消费者认领时间",
    },
    "sys_position": {
        "name": "职位名称",
        "category": "职位类别",
        "description": "职位描述",
        "status": "职位状态：ENABLED/DISABLED",
    },
    "sys_resource": {
        "parent_id": "父级资源ID（菜单树）",
        "resource_type": "资源类型：MENU/BUTTON/API 等",
        "module_id": "所属资源模块ID",
        "component": "前端路由组件路径",
        "redirect": "路由重定向地址",
        "href": "外链地址",
        "layout": "页面布局类型",
        "description": "资源描述说明",
        "status": "资源状态：ENABLED/DISABLED",
    },
    "sys_resource_module": {
        "client": "所属客户端：admin/portal 等",
        "description": "资源模块描述",
        "status": "模块状态：ENABLED/DISABLED",
    },
    "sys_role": {
        "scope_type": "角色作用域：GLOBAL/DEPT 等",
        "category": "角色分类",
        "is_builtin": "是否内置角色：1 内置 / 0 自定义",
        "description": "角色描述",
        "status": "角色状态：ENABLED/DISABLED",
    },
    "sys_weak_password": {
        "password": "弱口令明文（用于注册/改密校验）",
    },
    "profile_user_admin": {
        "account_id": "关联系统账号ID（主键）",
        "nickname": "管理端显示昵称",
        "avatar": "头像 object_name 或 URL",
        "signature": "个性签名",
        "phone": "绑定手机号",
        "email": "绑定邮箱",
    },
    "profile_user_portal": {
        "account_id": "关联系统账号ID（主键）",
        "nickname": "门户端显示昵称",
        "avatar": "头像 object_name 或 URL",
        "signature": "个性签名",
        "phone": "绑定手机号",
        "email": "绑定邮箱",
    },
    "profile_identity": {
        "account_id": "关联系统账号ID（主键）",
        "status": "认证状态：UNVERIFIED/PENDING/VERIFIED/REJECTED",
        "real_name_cipher": "真实姓名密文（加密存储）",
        "document_no_cipher": "证件号码密文（加密存储）",
        "document_no_hash": "证件号码哈希（用于脱敏检索）",
        "verified_at": "实名认证通过时间",
        "source_case_id": "来源实名工单ID",
        "revoked_at": "实名认证撤销时间",
        "revoked_by": "撤销操作人账户ID",
    },
    "real_name_case": {
        "case_id": "实名工单ID（主键）",
        "business_type": "业务类型：ACCOUNT_VERIFY/ACCOUNT_RECOVERY",
        "status": "工单状态：PENDING/APPROVED/REJECTED 等",
        "target_account_hint_cipher": "目标账户提示信息密文",
        "applicant_contact_cipher": "申请人联系方式密文",
        "payload_cipher": "扩展业务载荷密文",
        "handler_dept_id": "受理部门ID",
        "submitter_id": "提交人账户ID",
        "reviewer_id": "审核人账户ID",
        "reviewed_at": "审核完成时间",
        "reject_reason": "审核驳回原因",
    },
    "real_name_case_record": {
        "record_id": "工单流水ID（主键）",
        "case_id": "关联实名工单ID",
        "action": "流水动作：SUBMIT/APPROVE/REJECT/REVOKE 等",
        "status_before": "动作前工单状态",
        "status_after": "动作后工单状态",
        "operator_id": "操作人账户ID",
        "dept_id": "操作所属部门ID",
    },
}


def iter_mysql_create_blocks(mysql_sql: str):
    for m in re.finditer(r"CREATE TABLE `([^`]+)` \(", mysql_sql):
        table = m.group(1)
        i = m.end()
        depth = 1
        while i < len(mysql_sql) and depth > 0:
            ch = mysql_sql[i]
            if ch == "(":
                depth += 1
            elif ch == ")":
                depth -= 1
            i += 1
        yield table, mysql_sql[m.end() : i - 1]


def parse_mysql_column_comments(mysql_sql: str) -> dict[str, dict[str, str]]:
    col_comments: dict[str, dict[str, str]] = {}
    comment_re = re.compile(r"COMMENT\s+'((?:''|[^'])*)'", re.I)
    for table, body in iter_mysql_create_blocks(mysql_sql):
        for line in body.splitlines():
            line = line.strip().rstrip(",")
            if not line or line.startswith(("PRIMARY KEY", "KEY ", "UNIQUE ", "CONSTRAINT ")):
                continue
            col_m = re.match(r"`([^`]+)`\s+", line)
            if not col_m:
                continue
            col = col_m.group(1)
            cm = comment_re.search(line)
            if cm:
                col_comments.setdefault(table, {})[col] = cm.group(1).replace("''", "'")
    return col_comments


def list_mysql_tables(mysql_sql: str) -> list[str]:
    return re.findall(r"CREATE TABLE `([^`]+)`", mysql_sql)


def list_mysql_columns(mysql_sql: str, table: str) -> list[str]:
    for name, body in iter_mysql_create_blocks(mysql_sql):
        if name != table:
            continue
        cols: list[str] = []
        for line in body.splitlines():
            line = line.strip().rstrip(",")
            if not line or line.startswith(("PRIMARY KEY", "KEY ", "UNIQUE ", "CONSTRAINT ")):
                continue
            match = re.match(r"`([^`]+)`\s+", line)
            if match:
                cols.append(match.group(1))
        return cols
    return []


def parse_pg_comments(pg_sql: str) -> dict[str, dict[str, str]]:
    col_comments: dict[str, dict[str, str]] = {}
    col_re = re.compile(
        r'COMMENT ON COLUMN "public"\."([^"]+)"\."([^"]+)" IS \'((?:\'\'|[^\'])*)\';'
    )
    for table, col, comment in col_re.findall(pg_sql):
        col_comments.setdefault(table, {})[col] = comment.replace("''", "'")
    return col_comments


def list_pg_tables(pg_sql: str) -> list[str]:
    return re.findall(r'CREATE TABLE "public"\."([^"]+)"', pg_sql)


def list_pg_columns(pg_sql: str, table: str) -> list[str]:
    block = re.search(
        rf'CREATE TABLE "public"\."{re.escape(table)}" \(([\s\S]*?)\)\s*;',
        pg_sql,
    )
    if not block:
        return []
    cols: list[str] = []
    for line in block.group(1).splitlines():
        line = line.strip().rstrip(",")
        if not line or line.startswith("CONSTRAINT"):
            continue
        match = re.match(r'"([^"]+)"\s+', line)
        if match:
            cols.append(match.group(1))
    return cols


WEAK_EXACT = {
    "描述",
    "状态",
    "模块",
    "操作",
    "摘要",
    "标题",
    "内容",
    "编码",
    "类型",
    "分类",
    "备注",
    "名称",
    "路径",
    "图标",
    "颜色",
    "排序",
    "等级",
    "主键",
    "主关系",
}


def humanize_column(column: str) -> str:
    parts = column.split("_")
    mapping = {
        "id": "ID",
        "ip": "IP",
        "url": "URL",
        "no": "编号",
        "at": "时间",
        "by": "人",
        "ms": "毫秒",
        "pk": "主键",
        "sku": "SKU",
        "api": "API",
        "ui": "UI",
        "db": "数据库",
        "json": "JSON",
        "oauth": "OAuth",
    }
    out: list[str] = []
    for p in parts:
        if p in mapping:
            out.append(mapping[p])
        elif p.startswith("is"):
            out.append("是否" + humanize_column(p[2:]) if len(p) > 2 else "是否")
        else:
            out.append(p)
    text = "".join(out)
    return text if text else column


def enhance_label(table: str, column: str, existing: str) -> str:
    if table in TABLE_COLUMN_LABELS and column in TABLE_COLUMN_LABELS[table]:
        return TABLE_COLUMN_LABELS[table][column]
    if column in COMMON_COLUMN_LABELS:
        common = COMMON_COLUMN_LABELS[column]
        if not existing or existing in WEAK_EXACT or len(existing) <= 4:
            return common
        if existing == common:
            return existing
        # 保留更具体的 PG 注释（含枚举、括号说明）
        if "：" in existing or "(" in existing or "（" in existing:
            return existing
        return common
    table_label = TABLE_LABELS.get(table, table)
    if not existing:
        return f"{table_label}-{humanize_column(column)}"
    if existing in WEAK_EXACT:
        if existing == "主键":
            return "主键ID"
        return f"{table_label}{existing}"
    if existing == "主键":
        return "主键ID"
    if column in ("created_by", "updated_by") and existing in ("创建人", "更新人"):
        return COMMON_COLUMN_LABELS[column]
    return existing


def build_full_catalog(sql: str) -> dict[str, dict[str, str]]:
    if "CREATE TABLE `" in sql:
        comments = parse_mysql_column_comments(sql)
        list_tables = list_mysql_tables
        list_columns = list_mysql_columns
    else:
        comments = parse_pg_comments(sql)
        list_tables = list_pg_tables
        list_columns = list_pg_columns
    catalog: dict[str, dict[str, str]] = {}
    for table in list_tables(sql):
        catalog[table] = {}
        for column in list_columns(sql, table):
            existing = comments.get(table, {}).get(column, "")
            catalog[table][column] = enhance_label(table, column, existing)
    return catalog


def emit_module(catalog: dict[str, dict[str, str]]) -> str:
    lines = [
        '"""权威字段中文注释：从 MySQL db.sql 解析并增强（补齐历史不完整 COMMENT）。"""',
        "",
        "from __future__ import annotations",
        "",
        "# 由 scripts/generate_column_labels.py 生成，勿手改。",
        "# 调整规则后执行: python scripts/generate_column_labels.py",
        "",
        "COMMON_COLUMN_LABELS: dict[str, str] = " + repr(COMMON_COLUMN_LABELS),
        "",
        "TABLE_COLUMN_LABELS: dict[str, dict[str, str]] = " + repr(TABLE_COLUMN_LABELS),
        "",
        "WEAK_EXACT = " + repr(WEAK_EXACT),
        "",
        "FULL_COLUMN_LABELS: dict[str, dict[str, str]] = " + repr(catalog),
        "",
        "",
        "def resolve_column_label(table: str, column: str, pg_comment: str = \"\") -> str:",
        "    if table in FULL_COLUMN_LABELS and column in FULL_COLUMN_LABELS[table]:",
        "        return FULL_COLUMN_LABELS[table][column]",
        "    if table in TABLE_COLUMN_LABELS and column in TABLE_COLUMN_LABELS[table]:",
        "        return TABLE_COLUMN_LABELS[table][column]",
        "    if column in COMMON_COLUMN_LABELS:",
        "        return COMMON_COLUMN_LABELS[column]",
        "    return pg_comment or column",
        "",
        "",
        "def all_column_labels() -> dict[str, dict[str, str]]:",
        "    return FULL_COLUMN_LABELS",
        "",
    ]
    return "\n".join(lines)


def main() -> None:
    sql = SRC.read_text(encoding="utf-8")
    catalog = build_full_catalog(sql)
    OUT.write_text(emit_module(catalog), encoding="utf-8")
    total = sum(len(v) for v in catalog.values())
    changed = 0
    if "CREATE TABLE `" in sql:
        raw_comments = parse_mysql_column_comments(sql)
    else:
        raw_comments = parse_pg_comments(sql)
    for table, cols in catalog.items():
        for col, label in cols.items():
            if raw_comments.get(table, {}).get(col, "") != label:
                changed += 1
    print(f"wrote {OUT.name}: columns={total}, enhanced={changed}")


if __name__ == "__main__":
    main()
