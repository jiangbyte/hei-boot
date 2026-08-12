-- 门户多通道注册 + 强制绑定：ALLOW_* / FORCE_BIND_*；补齐绑定 OTP 模板；废弃 REQUIRE 假开关。

-- 门户注册通道
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_reg_portal_allow_account', 'AUTH_REGISTER_PORTAL_ALLOW_ACCOUNT', 'TRUE', 'AUTH_REGISTER',
        'PORTAL 允许用户名注册', 11, 'BOOL', '允许账号注册', 'PORTAL', NULL, TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_reg_portal_allow_email', 'AUTH_REGISTER_PORTAL_ALLOW_EMAIL', 'TRUE', 'AUTH_REGISTER',
        'PORTAL 允许邮箱注册', 12, 'BOOL', '允许邮箱注册', 'PORTAL', NULL, TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_reg_portal_allow_phone', 'AUTH_REGISTER_PORTAL_ALLOW_PHONE', 'FALSE', 'AUTH_REGISTER',
        'PORTAL 允许手机注册', 13, 'BOOL', '允许手机注册', 'PORTAL', NULL, TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

-- 强制绑定（PORTAL / ADMIN）
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_force_bind_portal_email', 'AUTH_FORCE_BIND_PORTAL_EMAIL', 'FALSE', 'AUTH_FORCE_BIND',
        'PORTAL 强制绑定邮箱', 1, 'BOOL', '强制绑定邮箱', 'PORTAL', NULL, TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_force_bind_portal_phone', 'AUTH_FORCE_BIND_PORTAL_PHONE', 'FALSE', 'AUTH_FORCE_BIND',
        'PORTAL 强制绑定手机', 2, 'BOOL', '强制绑定手机', 'PORTAL', NULL, TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_force_bind_admin_email', 'AUTH_FORCE_BIND_ADMIN_EMAIL', 'FALSE', 'AUTH_FORCE_BIND',
        'ADMIN 强制绑定邮箱', 3, 'BOOL', '强制绑定邮箱', 'ADMIN', NULL, TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_force_bind_admin_phone', 'AUTH_FORCE_BIND_ADMIN_PHONE', 'FALSE', 'AUTH_FORCE_BIND',
        'ADMIN 强制绑定手机', 4, 'BOOL', '强制绑定手机', 'ADMIN', NULL, TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

-- 废弃 REQUIRE 开关备注（保留键以免旧配置页报错，业务改读 ALLOW_* / FORCE_BIND_*）
UPDATE sys_config
SET remark = '已废弃：请使用 AUTH_REGISTER_PORTAL_ALLOW_EMAIL'
WHERE config_key = 'AUTH_REGISTER_PORTAL_REQUIRE_EMAIL';

UPDATE sys_config
SET remark = '已废弃：请使用 AUTH_REGISTER_PORTAL_ALLOW_PHONE / AUTH_FORCE_BIND_*'
WHERE config_key = 'AUTH_REGISTER_PORTAL_REQUIRE_PHONE';

UPDATE sys_config
SET remark = '已废弃：管理员无自助注册'
WHERE config_key IN ('AUTH_REGISTER_ADMIN_REQUIRE_EMAIL', 'AUTH_REGISTER_ADMIN_REQUIRE_PHONE');

-- 绑定 OTP 模板
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_mail_bind_email_code', 'MAIL_TEMPLATE_BIND_EMAIL_CODE',
        '{"subject": "{{app_name}} 绑定邮箱验证码", "body": "您的绑定验证码是 {{code}}，{{expire_minutes}} 分钟内有效。"}',
        'MAIL_TEMPLATE', '绑定邮箱验证码邮件模板', 20, 'JSON', '绑定邮箱验证码邮件模板', NULL, 'BIND_EMAIL_CODE', TRUE,
        '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sms_bind_phone_code', 'SMS_TEMPLATE_BIND_PHONE_CODE',
        '{"code": "", "content": "绑定验证码 {{code}}，{{expire_minutes}} 分钟内有效"}',
        'SMS_TEMPLATE', '绑定手机验证码短信模板', 20, 'JSON', '绑定手机验证码短信模板', NULL, 'BIND_PHONE_CODE', TRUE,
        '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
