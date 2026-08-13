-- Author: Charlie
-- 公告查询 / 已读 / 账号身份 / 审计幂等 性能索引

CREATE INDEX IF NOT EXISTS idx_msg_notice_status_kind_publish
    ON msg_notice (status, kind, publish_at);

CREATE INDEX IF NOT EXISTS idx_msg_notice_status_pinned_publish
    ON msg_notice (status, is_pinned, publish_at DESC);

CREATE INDEX IF NOT EXISTS idx_msg_notice_target_account_types_gin
    ON msg_notice USING GIN ((target_account_types::jsonb));

CREATE INDEX IF NOT EXISTS idx_msg_notice_target_account_ids_gin
    ON msg_notice USING GIN ((target_account_ids::jsonb));

CREATE INDEX IF NOT EXISTS idx_msg_notice_read_account
    ON msg_notice_read (account_type, account_id);

CREATE INDEX IF NOT EXISTS idx_sys_account_identity_account_id
    ON sys_account_identity (account_id);

CREATE UNIQUE INDEX IF NOT EXISTS uq_sys_operation_audit_log_request_id
    ON sys_operation_audit_log (request_id)
    WHERE request_id IS NOT NULL AND btrim(request_id) <> '';
