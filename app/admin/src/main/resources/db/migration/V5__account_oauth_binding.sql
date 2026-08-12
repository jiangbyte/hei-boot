-- 三方登录绑定表 + OAUTH_PROVIDER 字典 + AUTH_OAUTH 配置种子

CREATE TABLE IF NOT EXISTS sys_account_oauth_binding
(
    id          VARCHAR(64)  NOT NULL,
    account_id  VARCHAR(64)  NOT NULL,
    provider    VARCHAR(32)  NOT NULL,
    open_id     VARCHAR(128) NOT NULL,
    union_id    VARCHAR(128),
    nickname    VARCHAR(128),
    avatar      TEXT,
    raw_profile JSON         NOT NULL DEFAULT '{}'::json,
    bound_at    TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by  VARCHAR(64),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_by  VARCHAR(64),
    PRIMARY KEY (id),
    CONSTRAINT uq_oauth_provider_open_id UNIQUE (provider, open_id),
    CONSTRAINT uq_oauth_account_provider UNIQUE (account_id, provider)
);

CREATE INDEX IF NOT EXISTS idx_oauth_binding_account ON sys_account_oauth_binding (account_id);
CREATE INDEX IF NOT EXISTS idx_oauth_binding_union ON sys_account_oauth_binding (union_id)
    WHERE union_id IS NOT NULL AND union_id <> '';

-- 字典
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('dict_oauth_provider', 'OAUTH_PROVIDER', '三方登录提供商', 'OAUTH_PROVIDER', NULL, 'SYSTEM', NULL, 'ENABLED', 90)
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('dict_oauth_github', 'GITHUB', 'GitHub', 'GITHUB', NULL, 'OAUTH_PROVIDER', 'dict_oauth_provider', 'ENABLED', 1)
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('dict_oauth_gitee', 'GITEE', 'Gitee', 'GITEE', NULL, 'OAUTH_PROVIDER', 'dict_oauth_provider', 'ENABLED', 2)
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('dict_oauth_qq', 'QQ', 'QQ', 'QQ', NULL, 'OAUTH_PROVIDER', 'dict_oauth_provider', 'ENABLED', 3)
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('dict_oauth_wechat_open', 'WECHAT_OPEN', '微信开放平台', 'WECHAT_OPEN', NULL, 'OAUTH_PROVIDER',
        'dict_oauth_provider', 'ENABLED', 4)
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('dict_oauth_wechat_mp', 'WECHAT_MP', '微信小程序', 'WECHAT_MP', NULL, 'OAUTH_PROVIDER', 'dict_oauth_provider',
        'ENABLED', 5)
ON CONFLICT (id) DO NOTHING;

-- PORTAL / ADMIN × 提供商开关与凭据（默认关闭）
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_github_en', 'AUTH_OAUTH_PORTAL_GITHUB_ENABLED', 'FALSE', 'AUTH_OAUTH', '门户 GitHub 登录', 1,
        'BOOL', '启用', 'PORTAL', 'GITHUB', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_github_cid', 'AUTH_OAUTH_PORTAL_GITHUB_CLIENT_ID', '', 'AUTH_OAUTH', '门户 GitHub ClientId', 2,
        'STRING', 'Client ID', 'PORTAL', 'GITHUB', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_github_sec', 'AUTH_OAUTH_PORTAL_GITHUB_CLIENT_SECRET', '', 'AUTH_OAUTH',
        '门户 GitHub ClientSecret', 3, 'STRING', 'Client Secret', 'PORTAL', 'GITHUB', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_github_ru', 'AUTH_OAUTH_PORTAL_GITHUB_REDIRECT_URI', '', 'AUTH_OAUTH', '门户 GitHub 回调', 4,
        'STRING', 'Redirect URI', 'PORTAL', 'GITHUB', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_gitee_en', 'AUTH_OAUTH_PORTAL_GITEE_ENABLED', 'FALSE', 'AUTH_OAUTH', '门户 Gitee 登录', 11,
        'BOOL', '启用', 'PORTAL', 'GITEE', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_gitee_cid', 'AUTH_OAUTH_PORTAL_GITEE_CLIENT_ID', '', 'AUTH_OAUTH', '门户 Gitee ClientId', 12,
        'STRING', 'Client ID', 'PORTAL', 'GITEE', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_gitee_sec', 'AUTH_OAUTH_PORTAL_GITEE_CLIENT_SECRET', '', 'AUTH_OAUTH',
        '门户 Gitee ClientSecret', 13, 'STRING', 'Client Secret', 'PORTAL', 'GITEE', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_gitee_ru', 'AUTH_OAUTH_PORTAL_GITEE_REDIRECT_URI', '', 'AUTH_OAUTH', '门户 Gitee 回调', 14,
        'STRING', 'Redirect URI', 'PORTAL', 'GITEE', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_qq_en', 'AUTH_OAUTH_PORTAL_QQ_ENABLED', 'FALSE', 'AUTH_OAUTH', '门户 QQ 登录', 21, 'BOOL',
        '启用', 'PORTAL', 'QQ', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_qq_cid', 'AUTH_OAUTH_PORTAL_QQ_CLIENT_ID', '', 'AUTH_OAUTH', '门户 QQ ClientId', 22, 'STRING',
        'Client ID', 'PORTAL', 'QQ', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_qq_sec', 'AUTH_OAUTH_PORTAL_QQ_CLIENT_SECRET', '', 'AUTH_OAUTH', '门户 QQ ClientSecret', 23,
        'STRING', 'Client Secret', 'PORTAL', 'QQ', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_qq_ru', 'AUTH_OAUTH_PORTAL_QQ_REDIRECT_URI', '', 'AUTH_OAUTH', '门户 QQ 回调', 24, 'STRING',
        'Redirect URI', 'PORTAL', 'QQ', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_wxopen_en', 'AUTH_OAUTH_PORTAL_WECHAT_OPEN_ENABLED', 'FALSE', 'AUTH_OAUTH', '门户微信网页登录',
        31, 'BOOL', '启用', 'PORTAL', 'WECHAT_OPEN', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_wxopen_cid', 'AUTH_OAUTH_PORTAL_WECHAT_OPEN_CLIENT_ID', '', 'AUTH_OAUTH',
        '门户微信开放平台 AppId', 32, 'STRING', 'AppId', 'PORTAL', 'WECHAT_OPEN', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_wxopen_sec', 'AUTH_OAUTH_PORTAL_WECHAT_OPEN_CLIENT_SECRET', '', 'AUTH_OAUTH',
        '门户微信开放平台 Secret', 33, 'STRING', 'AppSecret', 'PORTAL', 'WECHAT_OPEN', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_wxopen_ru', 'AUTH_OAUTH_PORTAL_WECHAT_OPEN_REDIRECT_URI', '', 'AUTH_OAUTH',
        '门户微信开放平台回调', 34, 'STRING', 'Redirect URI', 'PORTAL', 'WECHAT_OPEN', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_wxmp_en', 'AUTH_OAUTH_PORTAL_WECHAT_MP_ENABLED', 'FALSE', 'AUTH_OAUTH', '门户微信小程序登录', 41,
        'BOOL', '启用', 'PORTAL', 'WECHAT_MP', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_wxmp_cid', 'AUTH_OAUTH_PORTAL_WECHAT_MP_APP_ID', '', 'AUTH_OAUTH', '门户小程序 AppId', 42,
        'STRING', 'AppId', 'PORTAL', 'WECHAT_MP', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_portal_wxmp_sec', 'AUTH_OAUTH_PORTAL_WECHAT_MP_APP_SECRET', '', 'AUTH_OAUTH', '门户小程序 AppSecret',
        43, 'STRING', 'AppSecret', 'PORTAL', 'WECHAT_MP', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

-- ADMIN（无 WECHAT_MP UI，仍可配置）
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_github_en', 'AUTH_OAUTH_ADMIN_GITHUB_ENABLED', 'FALSE', 'AUTH_OAUTH', '管理端 GitHub 登录', 101,
        'BOOL', '启用', 'ADMIN', 'GITHUB', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_github_cid', 'AUTH_OAUTH_ADMIN_GITHUB_CLIENT_ID', '', 'AUTH_OAUTH', '管理端 GitHub ClientId',
        102, 'STRING', 'Client ID', 'ADMIN', 'GITHUB', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_github_sec', 'AUTH_OAUTH_ADMIN_GITHUB_CLIENT_SECRET', '', 'AUTH_OAUTH',
        '管理端 GitHub ClientSecret', 103, 'STRING', 'Client Secret', 'ADMIN', 'GITHUB', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_github_ru', 'AUTH_OAUTH_ADMIN_GITHUB_REDIRECT_URI', '', 'AUTH_OAUTH', '管理端 GitHub 回调', 104,
        'STRING', 'Redirect URI', 'ADMIN', 'GITHUB', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_gitee_en', 'AUTH_OAUTH_ADMIN_GITEE_ENABLED', 'FALSE', 'AUTH_OAUTH', '管理端 Gitee 登录', 111,
        'BOOL', '启用', 'ADMIN', 'GITEE', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_gitee_cid', 'AUTH_OAUTH_ADMIN_GITEE_CLIENT_ID', '', 'AUTH_OAUTH', '管理端 Gitee ClientId', 112,
        'STRING', 'Client ID', 'ADMIN', 'GITEE', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_gitee_sec', 'AUTH_OAUTH_ADMIN_GITEE_CLIENT_SECRET', '', 'AUTH_OAUTH',
        '管理端 Gitee ClientSecret', 113, 'STRING', 'Client Secret', 'ADMIN', 'GITEE', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_gitee_ru', 'AUTH_OAUTH_ADMIN_GITEE_REDIRECT_URI', '', 'AUTH_OAUTH', '管理端 Gitee 回调', 114,
        'STRING', 'Redirect URI', 'ADMIN', 'GITEE', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_qq_en', 'AUTH_OAUTH_ADMIN_QQ_ENABLED', 'FALSE', 'AUTH_OAUTH', '管理端 QQ 登录', 121, 'BOOL',
        '启用', 'ADMIN', 'QQ', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_qq_cid', 'AUTH_OAUTH_ADMIN_QQ_CLIENT_ID', '', 'AUTH_OAUTH', '管理端 QQ ClientId', 122, 'STRING',
        'Client ID', 'ADMIN', 'QQ', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_qq_sec', 'AUTH_OAUTH_ADMIN_QQ_CLIENT_SECRET', '', 'AUTH_OAUTH', '管理端 QQ ClientSecret', 123,
        'STRING', 'Client Secret', 'ADMIN', 'QQ', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_qq_ru', 'AUTH_OAUTH_ADMIN_QQ_REDIRECT_URI', '', 'AUTH_OAUTH', '管理端 QQ 回调', 124, 'STRING',
        'Redirect URI', 'ADMIN', 'QQ', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_wxopen_en', 'AUTH_OAUTH_ADMIN_WECHAT_OPEN_ENABLED', 'FALSE', 'AUTH_OAUTH', '管理端微信网页登录',
        131, 'BOOL', '启用', 'ADMIN', 'WECHAT_OPEN', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_wxopen_cid', 'AUTH_OAUTH_ADMIN_WECHAT_OPEN_CLIENT_ID', '', 'AUTH_OAUTH',
        '管理端微信开放平台 AppId', 132, 'STRING', 'AppId', 'ADMIN', 'WECHAT_OPEN', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_wxopen_sec', 'AUTH_OAUTH_ADMIN_WECHAT_OPEN_CLIENT_SECRET', '', 'AUTH_OAUTH',
        '管理端微信开放平台 Secret', 133, 'STRING', 'AppSecret', 'ADMIN', 'WECHAT_OPEN', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_admin_wxopen_ru', 'AUTH_OAUTH_ADMIN_WECHAT_OPEN_REDIRECT_URI', '', 'AUTH_OAUTH',
        '管理端微信开放平台回调', 134, 'STRING', 'Redirect URI', 'ADMIN', 'WECHAT_OPEN', TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;

INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_frontend_portal_cb', 'AUTH_OAUTH_FRONTEND_CALLBACK_PORTAL', '', 'AUTH_OAUTH',
        '门户 OAuth 前端回调页（空则用默认）', 200, 'STRING', '门户前端回调', NULL, NULL, TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_oauth_frontend_admin_cb', 'AUTH_OAUTH_FRONTEND_CALLBACK_ADMIN', '', 'AUTH_OAUTH',
        '管理端 OAuth 前端回调页（空则用默认）', 201, 'STRING', '管理端前端回调', NULL, NULL, TRUE, '{}'::json)
ON CONFLICT (config_key) DO NOTHING;
