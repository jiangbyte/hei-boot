# PostgreSQL / Flyway

**权威迁移目录（运行时加载）：**

`app/admin/src/main/resources/db/migration/`

Spring 配置：`spring.flyway.locations=classpath:db/migration`。

Schema 与 hei-fastapi Alembic revision `27c193fc4b22` 对齐（33 张业务表）。  
`V1__init_schema.sql` 为 squash 基线；`V2__seed_bootstrap.sql` 为结构种子（资源树/client/字典/配置/IAM，对齐 fastapi
seed，无演示业务行）。  
旧 V1–V9 已移除，**开发库需重置**后再启动。

本目录**不再镜像** `V*.sql`，避免双份漂移。查阅或 diff 请直接打开上述 classpath 路径。

启动 `app/admin` 且 `spring.flyway.enabled=true` 时自动应用。

本地构建若遇已删除模块的幽灵类（如历史 IM），请先 `mvn clean` 再编译。

XXL-JOB Admin（本地，与业务共用 PostgreSQL）使用：

`app/xxl-job/src/main/resources/db/tables_xxl_job.sql`

Compose 挂载见 [../../docker/README.md](../../docker/README.md)；业务侧种子任务见 [`xxl_job_hei_seed.sql`](xxl_job_hei_seed.sql)。
