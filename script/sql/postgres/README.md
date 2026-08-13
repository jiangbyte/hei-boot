# PostgreSQL / Flyway

**权威迁移目录（运行时加载）：**

`app/admin/src/main/resources/db/migration/`

Spring 配置：`spring.flyway.locations=classpath:db/migration`。

Schema 与 hei-fastapi Alembic revision `27c193fc4b22` 对齐（33 张业务表）。  
`V1__init_schema.sql` 为 squash 基线；`V2__seed_bootstrap.sql` 为结构种子（资源树/client/字典/配置/IAM，对齐 fastapi
seed，无演示业务行）。  
旧 V1–V9 已移除，**开发库需重置**后再启动。

本目录**不再镜像**业务 `V*.sql`，避免双份漂移。查阅或 diff 请直接打开上述 classpath 路径。

启动 `app/admin` 且 `spring.flyway.enabled=true` 时自动应用。

本地构建若遇已删除模块的幽灵类（如历史 IM），请先 `mvn clean` 再编译。

## SnailJob（独立库）

调度中心库与业务库分离：库名 `snail_job`，JDBC 用户 `admin` / `123456`（在已有 Postgres 上建角色/库，勿改容器配置）。

| 文件 | 说明 |
|------|------|
| [`snailjob/V1__snail_job_schema.sql`](snailjob/V1__snail_job_schema.sql) | 上游 2.0.0 schema（Flyway） |
| [`snailjob/V2__hei_seed.sql`](snailjob/V2__hei_seed.sql) | 控制台 admin/123456、group `hei_boot_admin`、三任务 |
| [`drop_xxl_job.sql`](drop_xxl_job.sql) | 曾混部 XXL 表时的一次性清理 |

一键迁移：[`../../docker/snailjob-flyway.sh`](../../docker/snailjob-flyway.sh)。  
Server 编排：[`../../docker/docker-compose.snailjob.yml`](../../docker/docker-compose.snailjob.yml)。
