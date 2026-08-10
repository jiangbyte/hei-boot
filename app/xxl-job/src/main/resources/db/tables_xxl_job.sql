-- XXL-JOB Admin 表结构（PostgreSQL）
-- 与 app/admin 共用同一 PostgreSQL 实例；默认可建在 hei_boot 库中。

CREATE TABLE IF NOT EXISTS xxl_job_group (
    id           SERIAL PRIMARY KEY,
    app_name     varchar(64) NOT NULL,
    title        varchar(64) NOT NULL,
    address_type smallint    NOT NULL DEFAULT 0,
    address_list text,
    update_time  timestamp   DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS xxl_job_registry (
    id             BIGSERIAL PRIMARY KEY,
    registry_group varchar(50)  NOT NULL,
    registry_key   varchar(255) NOT NULL,
    registry_value varchar(255) NOT NULL,
    update_time    timestamp DEFAULT NULL,
    CONSTRAINT i_g_k_v UNIQUE (registry_group, registry_key, registry_value)
);

CREATE TABLE IF NOT EXISTS xxl_job_info (
    id                        SERIAL PRIMARY KEY,
    job_group                 int          NOT NULL,
    job_desc                  varchar(255) NOT NULL,
    add_time                  timestamp             DEFAULT NULL,
    update_time               timestamp             DEFAULT NULL,
    author                    varchar(64)           DEFAULT NULL,
    alarm_email               varchar(255)          DEFAULT NULL,
    schedule_type             varchar(50)  NOT NULL DEFAULT 'NONE',
    schedule_conf             varchar(128)          DEFAULT NULL,
    misfire_strategy          varchar(50)  NOT NULL DEFAULT 'DO_NOTHING',
    executor_route_strategy   varchar(50)           DEFAULT NULL,
    executor_handler          varchar(255)          DEFAULT NULL,
    executor_param            text                  DEFAULT NULL,
    executor_block_strategy   varchar(50)           DEFAULT NULL,
    executor_timeout          int          NOT NULL DEFAULT 0,
    executor_fail_retry_count int          NOT NULL DEFAULT 0,
    glue_type                 varchar(50)  NOT NULL,
    glue_source               text,
    glue_remark               varchar(128)          DEFAULT NULL,
    glue_updatetime           timestamp             DEFAULT NULL,
    child_jobid               varchar(255)          DEFAULT NULL,
    trigger_status            smallint     NOT NULL DEFAULT 0,
    trigger_last_time         bigint       NOT NULL DEFAULT 0,
    trigger_next_time         bigint       NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS xxl_job_logglue (
    id          SERIAL PRIMARY KEY,
    job_id      int          NOT NULL,
    glue_type   varchar(50)  DEFAULT NULL,
    glue_source text,
    glue_remark varchar(128) NOT NULL,
    add_time    timestamp    DEFAULT NULL,
    update_time timestamp    DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS xxl_job_log (
    id                        BIGSERIAL PRIMARY KEY,
    job_group                 int       NOT NULL,
    job_id                    int       NOT NULL,
    executor_address          varchar(255) DEFAULT NULL,
    executor_handler          varchar(255) DEFAULT NULL,
    executor_param            text         DEFAULT NULL,
    executor_sharding_param   varchar(20)  DEFAULT NULL,
    executor_fail_retry_count int       NOT NULL DEFAULT 0,
    trigger_time              timestamp    DEFAULT NULL,
    trigger_code              int       NOT NULL,
    trigger_msg               text,
    handle_time               timestamp    DEFAULT NULL,
    handle_code               int       NOT NULL,
    handle_msg                text,
    alarm_status              smallint  NOT NULL DEFAULT 0
);
CREATE INDEX IF NOT EXISTS i_trigger_time ON xxl_job_log (trigger_time);
CREATE INDEX IF NOT EXISTS i_handle_code ON xxl_job_log (handle_code);
CREATE INDEX IF NOT EXISTS i_jobgroup ON xxl_job_log (job_group);
CREATE INDEX IF NOT EXISTS i_jobid ON xxl_job_log (job_id);

CREATE TABLE IF NOT EXISTS xxl_job_log_report (
    id            SERIAL PRIMARY KEY,
    trigger_day   timestamp DEFAULT NULL,
    running_count int NOT NULL DEFAULT 0,
    suc_count     int NOT NULL DEFAULT 0,
    fail_count    int NOT NULL DEFAULT 0,
    update_time   timestamp DEFAULT NULL,
    CONSTRAINT i_trigger_day UNIQUE (trigger_day)
);

CREATE TABLE IF NOT EXISTS xxl_job_lock (
    lock_name varchar(50) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS xxl_job_user (
    id         SERIAL PRIMARY KEY,
    username   varchar(50)  NOT NULL,
    password   varchar(100) NOT NULL,
    token      varchar(100) DEFAULT NULL,
    role       smallint     NOT NULL,
    permission varchar(255) DEFAULT NULL,
    CONSTRAINT i_username UNIQUE (username)
);

-- 默认管理员 admin / 123456（SHA256）
INSERT INTO xxl_job_user (id, username, password, role, permission)
VALUES (1, 'admin', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, NULL)
ON CONFLICT (id) DO NOTHING;
SELECT setval(pg_get_serial_sequence('xxl_job_user', 'id'), GREATEST((SELECT MAX(id) FROM xxl_job_user), 1));

INSERT INTO xxl_job_lock (lock_name) VALUES ('schedule_lock') ON CONFLICT DO NOTHING;

-- 默认示例执行器（可被 hei 种子覆盖/扩展）
INSERT INTO xxl_job_group (id, app_name, title, address_type, address_list, update_time)
VALUES (1, 'xxl-job-executor-sample', '通用执行器Sample', 0, NULL, now())
ON CONFLICT (id) DO NOTHING;
SELECT setval(pg_get_serial_sequence('xxl_job_group', 'id'), GREATEST((SELECT MAX(id) FROM xxl_job_group), 1));
