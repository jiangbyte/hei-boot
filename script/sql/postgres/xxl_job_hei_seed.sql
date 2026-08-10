-- hei-boot-admin 执行器与 Job Handler 种子（PostgreSQL，在 tables_xxl_job.sql 之后执行）。
-- AppName 须与 hei.xxl-job.executor.appname 一致（默认：hei-boot-admin）。

INSERT INTO xxl_job_group (id, app_name, title, address_type, address_list, update_time)
VALUES (2, 'hei-boot-admin', 'HEI Admin', 0, NULL, now())
ON CONFLICT (id) DO UPDATE SET
    app_name = EXCLUDED.app_name,
    title = EXCLUDED.title,
    update_time = EXCLUDED.update_time;
SELECT setval(pg_get_serial_sequence('xxl_job_group', 'id'), GREATEST((SELECT MAX(id) FROM xxl_job_group), 1));

-- Java Handler：accountPurgeCancelledJob / bannerStatusJob / auditAlertJob
INSERT INTO xxl_job_info (
    id, job_group, job_desc, add_time, update_time, author, alarm_email,
    schedule_type, schedule_conf, misfire_strategy, executor_route_strategy,
    executor_handler, executor_param, executor_block_strategy, executor_timeout,
    executor_fail_retry_count, glue_type, glue_source, glue_remark, glue_updatetime,
    child_jobid, trigger_status, trigger_last_time, trigger_next_time
) VALUES
(100, 2, '清理超期已注销账号', now(), now(), 'hei', '',
 'CRON', '0 0 3 * * ?', 'DO_NOTHING', 'FIRST',
 'accountPurgeCancelledJob', '15', 'SERIAL_EXECUTION', 0,
 0, 'BEAN', '', 'GLUE代码初始化', now(),
 '', 0, 0, 0),
(101, 2, '按 start_at/end_at 同步 Banner 状态', now(), now(), 'hei', '',
 'CRON', '0 */5 * * * ?', 'DO_NOTHING', 'FIRST',
 'bannerStatusJob', '', 'SERIAL_EXECUTION', 0,
 0, 'BEAN', '', 'GLUE代码初始化', now(),
 '', 0, 0, 0),
(102, 2, '审计量级告警写入 sys_alert_log', now(), now(), 'hei', '',
 'CRON', '0 */2 * * * ?', 'DO_NOTHING', 'FIRST',
 'auditAlertJob', '', 'SERIAL_EXECUTION', 0,
 0, 'BEAN', '', 'GLUE代码初始化', now(),
 '', 0, 0, 0)
ON CONFLICT (id) DO UPDATE SET
    job_desc = EXCLUDED.job_desc,
    executor_handler = EXCLUDED.executor_handler,
    schedule_conf = EXCLUDED.schedule_conf,
    update_time = EXCLUDED.update_time;
SELECT setval(pg_get_serial_sequence('xxl_job_info', 'id'), GREATEST((SELECT MAX(id) FROM xxl_job_info), 1));
