-- OAuth 前端回调：空值回填本地绝对默认（已有库增量）

UPDATE "public"."sys_config"
SET "config_value" = 'http://localhost:5174/auth/oauth/callback',
    "updated_at" = now()
WHERE "config_key" = 'AUTH_OAUTH_FRONTEND_CALLBACK_PORTAL'
  AND (trim(coalesce("config_value", '')) = '');

UPDATE "public"."sys_config"
SET "config_value" = 'http://localhost:5173/auth/oauth/callback',
    "updated_at" = now()
WHERE "config_key" = 'AUTH_OAUTH_FRONTEND_CALLBACK_ADMIN'
  AND (trim(coalesce("config_value", '')) = '');
