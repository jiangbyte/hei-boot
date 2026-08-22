-- MySQL: widen operation content for multi-field Chinese diffs
ALTER TABLE sys_operation_audit_log MODIFY COLUMN summary varchar(2000) NULL COMMENT '操作内容';
