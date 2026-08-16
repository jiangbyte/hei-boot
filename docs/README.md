# HEI Boot 文档索引

本目录为文档入口；详细说明分散在仓库各处，避免重复维护。

| 主题 | 位置 |
|------|------|
| 总览、启动、API、约定 | [README.md](../README.md) |
| 本地基础设施（Compose） | [script/docker/README.md](../scripts/docker/README.md) |
| TLS 边缘示例 | [script/docker/tls/README.md](../scripts/docker/tls/README.md) |
| PostgreSQL / Flyway | [script/sql/postgres/README.md](../scripts/sql/postgres/README.md)（业务库权威：`scripts/db.sql`） |
| 性能压测（k6） | [script/perf/README.md](../scripts/perf/README.md) |
| 安全基线（ZAP） | [script/security/README.md](../scripts/security/README.md) |
| Kubernetes 参考 Chart | [deploy/helm/hei-boot/README.md](../deploy/helm/hei-boot/README.md) |
| Admin 前端 | [web/admin/README.md](../web/admin/README.md) |
| Portal 前端 | [web/portal/README.md](../web/portal/README.md) |
| Admin UniApp | [web/admin-uniapp/README.md](../web/admin-uniapp/README.md) |
| 界面截图 | [docs/images/](./images/) |
| IAM 字典待补 | [iam-dict-todo.md](./iam-dict-todo.md) |

## 职责边界（简）

- **`app/admin`**：唯一可运行应用（Admin / Portal API + 内置任务调度）；数据库以 `scripts/db.sql` 为权威。
- **`module/sys` 任务调度**：任务定义在 `sys_job` 表（CRON / 固定间隔），随应用进程运行，Redis 锁防多实例重复执行。
- **`module/*` / `module-api/*`**：业务实现与跨模块窄接口；`module/auth` 含登录 / OTP / 找回密码 / JustAuth OAuth；样板业务在 `module/biz`，生产打包用 `-P'!with-biz'` 排除。
- **`web/admin` / `web/portal` / `web/admin-uniapp`**：各自独立前端工程（无 `web/packages` 共享层）。
- **`script/`**：本地脚本与辅助资产；**`deploy/`**：部署参考（Helm）。
