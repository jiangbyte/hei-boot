-- 对象存储直连：去掉本地引擎 / 代理路径，增加桶公开配置（已有库增量）

DELETE FROM "public"."sys_job" WHERE "id" = '7541000000000000005';

DELETE FROM "public"."sys_config"
WHERE "config_key" LIKE 'STORAGE_LOCAL_%'
   OR "config_key" LIKE 'STORAGE_%_PUBLIC_PATH';

UPDATE "public"."sys_config"
SET "config_value" = 'RUSTFS', "updated_at" = now()
WHERE "config_key" = 'DEFAULT_FILE_ENGINE'
  AND upper("config_value") = 'LOCAL';

INSERT INTO "public"."sys_config" (
  "id", "config_key", "config_value", "category", "name", "sort", "value_type",
  "description", "scope", "scene", "is_public", "meta", "created_at", "created_by", "updated_at", "updated_by"
)
SELECT * FROM (VALUES
  ('cfg_sto_aliyun_pub', 'STORAGE_ALIYUN_BUCKET_PUBLIC', 'FALSE', 'STORAGE', '阿里云桶是否公开', 14, 'BOOL', NULL, NULL, NULL, 'f', '{}'::json, now(), NULL, now(), '1'),
  ('cfg_sto_tencent_pub', 'STORAGE_TENCENT_BUCKET_PUBLIC', 'FALSE', 'STORAGE', '腾讯云桶是否公开', 20, 'BOOL', NULL, NULL, NULL, 'f', '{}'::json, now(), NULL, now(), '1'),
  ('cfg_sto_minio_pub', 'STORAGE_MINIO_BUCKET_PUBLIC', 'FALSE', 'STORAGE', 'MinIO 桶是否公开', 26, 'BOOL', NULL, NULL, NULL, 'f', '{}'::json, now(), NULL, now(), '1'),
  ('cfg_sto_rustfs_pub', 'STORAGE_RUSTFS_BUCKET_PUBLIC', 'FALSE', 'STORAGE', 'RustFS 桶是否公开', 47, 'BOOL', NULL, NULL, NULL, 'f', '{}'::json, now(), NULL, now(), '1')
) AS v("id", "config_key", "config_value", "category", "name", "sort", "value_type",
       "description", "scope", "scene", "is_public", "meta", "created_at", "created_by", "updated_at", "updated_by")
WHERE NOT EXISTS (
  SELECT 1 FROM "public"."sys_config" c WHERE c."config_key" = v."config_key"
);
