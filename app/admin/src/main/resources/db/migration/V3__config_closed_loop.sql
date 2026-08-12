-- 系统配置闭环：补齐审计告警收件邮箱；对齐门户注册邮箱必填；标注未实现审计规则备注。

INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_audit_notify_email_to', 'AUDIT_ALERT_NOTIFY_EMAIL_TO', '', 'AUDIT_ALERT', '审计告警收件邮箱', 2,
        'STRING', '告警收件邮箱', NULL, NULL, TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

-- 门户注册产品对齐：邮箱必填、不要求手机
UPDATE sys_config
SET config_value = 'TRUE',
    remark      = 'PORTAL 注册要求邮箱（产品固定必填）'
WHERE config_key = 'AUTH_REGISTER_PORTAL_REQUIRE_EMAIL';

UPDATE sys_config
SET config_value = 'FALSE',
    remark      = 'PORTAL 注册不要求手机（产品已移除）'
WHERE config_key = 'AUTH_REGISTER_PORTAL_REQUIRE_PHONE';

-- 未实现规则保留种子但不参与 Job（备注说明，避免误以为已生效）
UPDATE sys_config
SET remark = remark || '（暂未实现，配置页已隐藏）'
WHERE config_key IN (
                      'AUDIT_ALERT_RULE_UNUSUAL_HOURS',
                      'AUDIT_ALERT_RULE_SENSITIVE_OPS',
                      'AUDIT_ALERT_RULE_BULK_DELETE',
                      'AUDIT_ALERT_RULE_IP_ANOMALY',
                      'AUDIT_ALERT_BULK_DELETE_THRESHOLD',
                      'AUDIT_ALERT_IP_ANOMALY_THRESHOLD'
    )
  AND remark NOT LIKE '%暂未实现%';
