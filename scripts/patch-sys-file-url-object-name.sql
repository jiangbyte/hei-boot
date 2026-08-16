-- 将仍存预签名的 sys_file.url 回写为 object_name（已有库增量）

UPDATE "public"."sys_file"
SET "url" = "object_name",
    "updated_at" = now()
WHERE "url" IS NOT NULL
  AND (
    "url" ILIKE '%X-Amz-%'
    OR "url" ILIKE '%X-OSS-%'
    OR "url" ILIKE '%Signature=%'
  );
