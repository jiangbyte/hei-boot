-- Alter sys_operation_audit_log for enriched operation log model (MySQL)
ALTER TABLE `sys_operation_audit_log`
  ADD COLUMN IF NOT EXISTS `operator_name` varchar(128) NULL AFTER `error_message`,
  ADD COLUMN IF NOT EXISTS `action_name` varchar(128) NULL AFTER `operator_name`,
  ADD COLUMN IF NOT EXISTS `action_type` varchar(32) NULL AFTER `action_name`,
  ADD COLUMN IF NOT EXISTS `module_label` varchar(128) NULL AFTER `action_type`,
  ADD COLUMN IF NOT EXISTS `duration_ms` int NULL AFTER `module_label`,
  MODIFY COLUMN `summary` varchar(512) NULL;
