-- Widen operation content column for multi-field Chinese diffs
ALTER TABLE sys_operation_audit_log ALTER COLUMN summary TYPE varchar(2000);
