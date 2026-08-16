-- 任务执行日志清理：索引 + 定时任务种子（已有库增量）
CREATE INDEX IF NOT EXISTS "idx_sys_job_log_execute_time"
  ON "public"."sys_job_log" USING btree ("execute_time");

INSERT INTO "public"."sys_job" (
  "id", "job_name", "execute_class", "execute_type", "trigger_config", "execute_param",
  "last_run_time", "next_run_time", "last_execute_result", "enabled", "description", "sort",
  "created_at", "created_by", "updated_at", "updated_by"
)
SELECT
  '7541000000000000007',
  '任务执行日志清理',
  'github.jiangbyte.io.sys.modules.job.cleanup.SysJobLogCleanupJob',
  'CRON',
  '0 30 3 * * *',
  '{"retentionDays": 30, "batchSize": 1000}',
  NULL,
  now(),
  NULL,
  't',
  '按保留天数批量清理过期 sys_job_log',
  7,
  now(),
  NULL,
  now(),
  NULL
WHERE NOT EXISTS (
  SELECT 1 FROM "public"."sys_job" WHERE "id" = '7541000000000000007'
);
