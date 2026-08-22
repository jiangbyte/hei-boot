-- Alter sys_operation_audit_log for enriched operation log model (PostgreSQL)
ALTER TABLE sys_operation_audit_log ADD COLUMN IF NOT EXISTS operator_name varchar(128);
ALTER TABLE sys_operation_audit_log ADD COLUMN IF NOT EXISTS action_name varchar(128);
ALTER TABLE sys_operation_audit_log ADD COLUMN IF NOT EXISTS action_type varchar(32);
ALTER TABLE sys_operation_audit_log ADD COLUMN IF NOT EXISTS module_label varchar(128);
ALTER TABLE sys_operation_audit_log ADD COLUMN IF NOT EXISTS duration_ms int4;
ALTER TABLE sys_operation_audit_log ALTER COLUMN summary TYPE varchar(512);
