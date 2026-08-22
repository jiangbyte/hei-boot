-- sys_job / sys_job_log generic field migration (PostgreSQL)
-- Author: Charlie

ALTER TABLE sys_job RENAME COLUMN job_name TO name;
ALTER TABLE sys_job RENAME COLUMN execute_class TO handler;
ALTER TABLE sys_job RENAME COLUMN execute_type TO trigger_type;
ALTER TABLE sys_job RENAME COLUMN execute_param TO params;
ALTER TABLE sys_job RENAME COLUMN last_execute_result TO last_result;

ALTER TABLE sys_job_log RENAME COLUMN execute_param TO params;
ALTER TABLE sys_job_log RENAME COLUMN execute_time TO started_at;
ALTER TABLE sys_job_log RENAME COLUMN execute_duration_ms TO duration_ms;
ALTER TABLE sys_job_log RENAME COLUMN execute_result TO result;
ALTER TABLE sys_job_log DROP COLUMN IF EXISTS job_name;

DROP INDEX IF EXISTS idx_sys_job_log_execute_time;
CREATE INDEX IF NOT EXISTS idx_sys_job_log_started_at ON sys_job_log (started_at);

COMMENT ON COLUMN sys_job.name IS '任务名称';
COMMENT ON COLUMN sys_job.handler IS '处理器标识（Boot 为 JobHandler 全限定类名，其他栈为注册 key）';
COMMENT ON COLUMN sys_job.trigger_type IS '触发类型：CRON（表达式）/ FIXED（固定间隔）';
COMMENT ON COLUMN sys_job.params IS '执行参数（JSON）';
COMMENT ON COLUMN sys_job.last_result IS '上次执行结果摘要';
COMMENT ON COLUMN sys_job_log.params IS '执行参数快照（JSON）';
COMMENT ON COLUMN sys_job_log.started_at IS '执行开始时间';
COMMENT ON COLUMN sys_job_log.duration_ms IS '执行用时（毫秒）';
COMMENT ON COLUMN sys_job_log.result IS '执行结果摘要 / 错误信息';
