-- Drop legacy XXL-JOB tables if they were created on a shared Postgres (e.g. hei_boot).
-- Run manually once on environments that previously used XXL-JOB. Does not touch snail_job.

DROP TABLE IF EXISTS xxl_job_logglue CASCADE;
DROP TABLE IF EXISTS xxl_job_log CASCADE;
DROP TABLE IF EXISTS xxl_job_log_report CASCADE;
DROP TABLE IF EXISTS xxl_job_registry CASCADE;
DROP TABLE IF EXISTS xxl_job_info CASCADE;
DROP TABLE IF EXISTS xxl_job_group CASCADE;
DROP TABLE IF EXISTS xxl_job_lock CASCADE;
DROP TABLE IF EXISTS xxl_job_user CASCADE;
