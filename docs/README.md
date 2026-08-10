# HEI Boot 文档索引

本目录为文档入口；详细说明分散在仓库各处，避免重复维护。

| 主题 | 位置 |
|------|------|
| 总览、启动、API、约定 | [README.md](../README.md) |
| 本地基础设施（Compose） | [script/docker/README.md](../script/docker/README.md) |
| TLS 边缘示例 | [script/docker/tls/README.md](../script/docker/tls/README.md) |
| PostgreSQL / Flyway | [script/sql/postgres/README.md](../script/sql/postgres/README.md)（权威迁移见 `app/admin/.../db/migration`） |
| 性能压测（k6） | [script/perf/README.md](../script/perf/README.md) |
| 安全基线（ZAP） | [script/security/README.md](../script/security/README.md) |
| Kubernetes 参考 Chart | [deploy/helm/hei-boot/README.md](../deploy/helm/hei-boot/README.md) |
| Admin 前端 | [web/admin/README.md](../web/admin/README.md) |
| Portal 前端 | [web/portal/README.md](../web/portal/README.md) |
| Admin UniApp | [web/admin-uniapp/README.md](../web/admin-uniapp/README.md) |
| IAM 字典待补 | [iam-dict-todo.md](./iam-dict-todo.md) |

## 职责边界（简）

- **`app/admin`**：可运行业务 API + 内嵌 XXL-JOB Executor；Flyway 权威源。
- **`app/xxl-job`**：仅本地调试的 XXL-JOB Admin。
- **`module/*` / `module-api/*`**：业务实现与跨模块窄接口（无样例 biz 模块）。
- **`web/admin` / `web/portal` / `web/admin-uniapp`**：各自独立前端工程（无 `web/packages` 共享层）。
- **`script/`**：本地脚本与辅助资产；**`deploy/`**：部署参考（Helm）。
