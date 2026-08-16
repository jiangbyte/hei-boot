-- 任务执行记录独立页路由资源（已有库增量）
INSERT INTO "public"."sys_resource" (
  "id", "parent_id", "code", "name", "resource_type", "module_id",
  "path", "component", "redirect", "icon", "color", "href",
  "sort", "is_visible", "is_cache", "is_affix", "status",
  "description", "layout", "meta", "created_at", "created_by", "updated_at", "updated_by"
)
VALUES (
  '204024', '204001', 'sys-job-log-page', '任务执行记录页', 'PAGE', '210001',
  '/sys/job/log', '/sys/job/log.vue', NULL, NULL, NULL, NULL,
  10, 'f', 'f', 'f', 'ENABLED',
  NULL, NULL, '{}', NOW(), NULL, NOW(), NULL
)
ON CONFLICT ("id") DO NOTHING;
