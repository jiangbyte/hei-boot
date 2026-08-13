-- HEI Boot SnailJob seed: group + three jobs + console password admin/123456
-- namespace unique_id matches default schema: 764d604ec6fc45f68cd92514c40e9e1a
-- Console hashes as SHA256(MD5(plain)); default admin was SHA256(MD5('admin')).

UPDATE sj_system_user
SET password = 'cdf4a007e2b02a0c49fc9b7ccfbb8a10c644f635e1765dcf2a7ab794ddc7edac',
    update_dt = now()
WHERE username = 'admin';

INSERT INTO sj_group_config (
    namespace_id, group_name, description, token, group_status, version,
    group_partition, id_generator_mode, init_scene, create_dt, update_dt
)
SELECT
    '764d604ec6fc45f68cd92514c40e9e1a',
    'hei_boot_admin',
    'HEI Boot Admin executor group',
    'SJ_heiBootAdminToken1234567890abcd',
    1,
    1,
    0,
    1,
    1,
    now(),
    now()
WHERE NOT EXISTS (
    SELECT 1 FROM sj_group_config
    WHERE namespace_id = '764d604ec6fc45f68cd92514c40e9e1a'
      AND group_name = 'hei_boot_admin'
);

-- trigger_type=1 CRON; job_status=1 enabled; task_type=1 cluster; route_key=4 round-robin-ish default
INSERT INTO sj_job (
    namespace_id, biz_id, group_name, job_name, args_str, args_type,
    next_trigger_at, job_status, task_type, route_key, executor_type, executor_info,
    trigger_type, trigger_interval, block_strategy, executor_timeout, max_retry_times,
    parallel_num, retry_interval, bucket_index, resident, notify_ids, description, deleted,
    create_dt, update_dt
)
SELECT
    '764d604ec6fc45f68cd92514c40e9e1a',
    'hei-accountPurgeCancelledJob',
    'hei_boot_admin',
    '清理超期已注销账号',
    '15',
    1,
    (EXTRACT(EPOCH FROM now()) * 1000)::bigint,
    1, 1, 4, 1, 'accountPurgeCancelledJob',
    1, '0 0 3 * * ?', 1, 0, 0,
    1, 0, 0, 0, '', 'Purge cancelled accounts past retention', 0,
    now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM sj_job
    WHERE namespace_id = '764d604ec6fc45f68cd92514c40e9e1a'
      AND biz_id = 'hei-accountPurgeCancelledJob'
);

INSERT INTO sj_job (
    namespace_id, biz_id, group_name, job_name, args_str, args_type,
    next_trigger_at, job_status, task_type, route_key, executor_type, executor_info,
    trigger_type, trigger_interval, block_strategy, executor_timeout, max_retry_times,
    parallel_num, retry_interval, bucket_index, resident, notify_ids, description, deleted,
    create_dt, update_dt
)
SELECT
    '764d604ec6fc45f68cd92514c40e9e1a',
    'hei-bannerStatusJob',
    'hei_boot_admin',
    '同步 Banner 状态',
    NULL,
    1,
    (EXTRACT(EPOCH FROM now()) * 1000)::bigint,
    1, 1, 4, 1, 'bannerStatusJob',
    1, '0 */5 * * * ?', 1, 0, 0,
    1, 0, 0, 0, '', 'Sync banner ENABLED/DISABLED by start_at/end_at', 0,
    now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM sj_job
    WHERE namespace_id = '764d604ec6fc45f68cd92514c40e9e1a'
      AND biz_id = 'hei-bannerStatusJob'
);

INSERT INTO sj_job (
    namespace_id, biz_id, group_name, job_name, args_str, args_type,
    next_trigger_at, job_status, task_type, route_key, executor_type, executor_info,
    trigger_type, trigger_interval, block_strategy, executor_timeout, max_retry_times,
    parallel_num, retry_interval, bucket_index, resident, notify_ids, description, deleted,
    create_dt, update_dt
)
SELECT
    '764d604ec6fc45f68cd92514c40e9e1a',
    'hei-auditAlertJob',
    'hei_boot_admin',
    '审计量级告警',
    NULL,
    1,
    (EXTRACT(EPOCH FROM now()) * 1000)::bigint,
    1, 1, 4, 1, 'auditAlertJob',
    1, '0 */2 * * * ?', 1, 0, 0,
    1, 0, 0, 0, '', 'Audit volume alert into sys_alert_log', 0,
    now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM sj_job
    WHERE namespace_id = '764d604ec6fc45f68cd92514c40e9e1a'
      AND biz_id = 'hei-auditAlertJob'
);
