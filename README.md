# HEI Boot

![JDK](https://img.shields.io/badge/JDK-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.x-6DB33F?logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Multi--Module-C71A36?logo=apachemaven&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Supported-4169E1?logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-Supported-DC382D?logo=redis&logoColor=white)
![MyBatis-Plus](https://img.shields.io/badge/MyBatis--Plus-3.5.x-blue)
![Sa-Token](https://img.shields.io/badge/Sa--Token-1.45.x-orange)
![License](https://img.shields.io/badge/License-MIT-green)

HEI Boot 是 HEI 项目的 Spring Boot 后端模板，使用 JDK 21、Spring Boot 4、Maven 多模块和
PostgreSQL 构建。采用常见的 Java 多模块分层与 Sa-Token / MyBatis-Plus 生态组件，目标是为中后台、
门户和通用业务系统提供一套**开源可改的一体化脚手架**（非黑盒平台）：源码全在仓库内，一般挂业务、改配置即可跑；复杂场景可直接改 `common/*`。

当前仓库包含后端与前端（`web/admin` / `web/portal` / `web/admin-uniapp`）。
Java 字段保持驼峰命名，对外 JSON 字段统一 `snake_case`。
HTTP JSON 线格式仅允许字符串 / 对象 / 数组：`boolean` 与数字一律以字符串收发
（见 `StringlyTypedJacksonModule`），避免前端大整型溢出与类型不一致。

文档索引见 [docs/README.md](docs/README.md)。

> **请注意：** 生产仍需自行加固密钥、外部 XXL-JOB、对象存储与高可用配置后再上线。已内置生产 profile
> 收紧（关闭文档/Actuator 匿名暴露、文件上传白名单、MQ 重试/DLQ、CI 与后端镜像）；上线前仍请核对密钥轮换、TLS/HSTS、网络隔离与压测。

## 架构

可运行应用只有两个：

| 应用 | 路径 | 说明 |
|------|------|------|
| Admin API + Executor | `app/admin` | 业务 API；内嵌 XXL-JOB Executor（`hei-boot-admin`） |
| XXL-JOB Admin | `app/xxl-job` | **仅本地调试**；生产不要部署，改连外部 Admin |

业务模块由 `app/admin` **显式依赖**装配；新增业务模块时登记 reactor 后，在 `app/admin/pom.xml` 增加依赖即可。

## 功能概览

- 基于 JDK 21、Spring Boot 4.1.x、Spring Framework 7、Maven 多模块。
- Sa-Token 双端登录（ADMIN / PORTAL），会话持久化到 Redis。
- IAM/RBAC、用户中心、系统（字典/Banner/文件/审计）、消息（公告/通知/反馈）。
- RabbitMQ 操作审计异步落库；可选关闭消费者 `hei.mq.audit.consume-enabled=false`。
- XXL-JOB：账号注销清理、Banner 状态同步、审计量级告警。
- MyBatis-Plus、Druid 连接池、动态数据源、Flyway；Actuator + Micrometer/Prometheus；Knife4j（`/doc.html`）。

## 运行要求

- JDK 21、Maven 3.9+
- PostgreSQL、Redis、RabbitMQ
- 本地 XXL-JOB Admin 另需 MySQL（见 docker-compose）

## 快速启动

### 1. 基础设施

```bash
docker compose -f script/docker/docker-compose.yml up -d
```

详情见 [script/docker/README.md](script/docker/README.md)。

### 2. 本地启动顺序

```bash
# 1) infra（上一步）
# 2) 本地 XXL-JOB Admin（仅本地）
mvn -pl app/xxl-job -am spring-boot:run

# 3) 管理端（含 Executor）
mvn -pl app/admin -am spring-boot:run
```

默认地址：

```text
Admin API:        http://127.0.0.1:8080
XXL-JOB Admin:    http://127.0.0.1:9004/xxl-job-admin   (admin / 123456)
API docs (Knife4j): http://127.0.0.1:8080/doc.html
Actuator health:    http://127.0.0.1:8080/actuator/health
```

### 消息模块

`module/message` 提供公告、通知、反馈（REST）。即时通讯（Netty IM / 会话 / 好友 / 群）已移除。

### 默认账号

| 账号 | 密码 | 说明 |
|------|------|------|
| `superadmin` | `123456` | Flyway seed 超管（ADMIN） |

### 会话 Cookie / Header（对齐 fastapi Web）

- **双通道**：`token-name=Authorization`；`is-read-header=true` 始终开启；Cookie 由 `SA_TOKEN_IS_READ_COOKIE` 开关（对应
  fastapi `AUTH__SESSION_COOKIE_ENABLED`）。
- Cookie 开：HttpOnly `Authorization` Cookie，Path 按端隔离为 `/api/vN/{admin|portal}`；登录 JSON 仍返回 `token`。
- Cookie 关：不写/不读 Cookie，仅认 opaque `Authorization` 头（非 Bearer）；Web 需本地存 token（fastapi admin/portal 已支持）。
- 生产 Cookie：`SA_TOKEN_IS_READ_COOKIE=true`、`SA_TOKEN_COOKIE_SECURE=true`、`SameSite=Lax`（同源 nginx / Vite 代理）。
- **CSRF**：默认关闭（`hei.security.cookie-csrf-enabled=false`）。若开启，变更类请求须带 `X-Requested-With` 或 `X-HEI-CSRF`。
- **CORS**：Sa-Token `SaStrategy.corsHandle`；默认放行本地 5173/5174/5163；`hei.security.cors-allowed-origins: ["*"]`
  时通配且关闭 credentials。

### 生产部署

- **不要部署** `app/xxl-job`。
- Kubernetes 参考 Chart：[deploy/helm/hei-boot](deploy/helm/hei-boot/)（需自行配置镜像与密钥）。
- 将 Executor 指向外部 Admin：

```bash
export XXL_JOB_ADMIN_ADDRESSES=http://xxl-job-admin.example.com/xxl-job-admin
export XXL_JOB_ACCESS_TOKEN=your_token
```

`application-prod.yml` 中 `hei.xxl-job.admin.addresses` 默认占位为
`${XXL_JOB_ADMIN_ADDRESSES:http://xxl-job-admin.example.com/xxl-job-admin}`。

## 主要 API 前缀

| 前缀 | 用途 |
|------|------|
| `/api/v1/admin/**` | 管理端 |
| `/api/v1/portal/**` | 门户端 |
| `/api/v1/internal/**` | 内部/健康等 |
| `/files/**` | 本地文件公开访问（可配置） |
| `/actuator/**` | 健康与指标 |
| `/swagger-ui/**`、`/v3/api-docs` | OpenAPI |

常用管理端子路径示例：`/api/v1/admin/login`、`/captcha`、`/iam/**`、`/sys/**`、`/user-center/**`、`/message/**`。

## 二次开发（社区）

本仓库是一体化脚手架：业务在 `module/*`，框架在 `common/*`，可运行壳在 `app/admin`。

### 一般情况：挂业务、改配置

按下列 checklist 即可（可参考现成样板 [`module/biz`](module/biz)）：

1. 在 `module/` 下新增业务模块：提供 `@AutoConfiguration` + `@ComponentScan`，并注册
   `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`。
   跨模块契约时再在 `module-api/` 增加窄接口。
2. 在 `module/pom.xml`（及必要时 `module-api/pom.xml`）登记 reactor 子模块。
3. 在根 `pom.xml` 的 `dependencyManagement` 增加该模块版本条目（`${revision}`）。
4. 在 `app/admin/pom.xml` **显式增加**对该业务模块的依赖。
5. **库表 / 菜单权限种子**：只在 `app/admin/src/main/resources/db/migration/` **追加**
   `V{n}__*.sql`（建议业务用较高序号，如 `V100__biz_xxx.sql`）。**勿改**已发布的 `V1` / `V2`。
6. **配置**：改 `app/admin/src/main/resources/application-*.yml` 或环境变量即可
  （例如 `hei.security.ignore-urls` 放行匿名路径）。

### 复杂场景：改框架

会话（Sa-Token）、过滤器、安全装配、MyBatis 等均在 `common/*`，**允许且欢迎直接改源码**，不是锁死黑盒。
业务侧尽量只动自己的 `module`；改过 `common` 后跟上游合并时冲突需自行解决。

### 跟上游升级

1. 先合入上游对 `common/`、`app/admin` 壳与共享低序号 migration 的变更。
2. 再合入自己的业务模块与高序号 `V{n}` 脚本。
3. 跑 Flyway / 本地冒烟，确认权限注解与菜单种子仍匹配。

## XXL-JOB 任务注册

Executor AppName：`hei-boot-admin`（与 `hei.xxl-job.executor.appname` 一致）。

| Handler | 模块 | 作用 |
|---------|------|------|
| `accountPurgeCancelledJob` | iam | 清理超过保留期的注销账号（默认 15 天，可用 job 参数覆盖） |
| `bannerStatusJob` | sys | 按 `start_at` / `end_at` 启用或停用 Banner |
| `auditAlertJob` | sys | 审计量超阈值写入 `sys_alert_log` |

本地 PostgreSQL 初始化会执行 `app/xxl-job/.../tables_xxl_job.sql` 与 `script/sql/postgres/xxl_job_hei_seed.sql`（与业务共用 `hei_boot`）。也可在 XXL-JOB Admin UI 手动新增执行器与上述三个 Bean 任务，然后启动调度。

## RabbitMQ 审计

- 生产者：`OperationAuditAspect` → `hei.audit.exchange` / `hei.audit.queue`
- 消费者：`AuditEventConsumer`（`hei.mq.audit.consume-enabled`，默认 true）
- 开发配置见 `application-dev.yml`；生产通过 `RABBITMQ_*` 环境变量注入（见 `application-prod.yml`）

## 配置

配置文件位于 `app/admin/src/main/resources`：

- `application.yml`：通用配置
- `application-dev.yml`：默认开发环境
- `application-local.yml`：本机调试
- `application-prod.yml`：生产模板

常用环境变量：

- `SPRING_PROFILES_ACTIVE`：激活配置，默认 `dev`
- `DB_WRITE_*` / `DB_READ_*` / `DB_*`：数据源
- `REDIS_HOST` / `REDIS_PORT` / `REDIS_PASSWORD` / `REDIS_DATABASE`
- `RABBITMQ_HOST` / `RABBITMQ_PORT` / `RABBITMQ_USERNAME` / `RABBITMQ_PASSWORD` / `RABBITMQ_VHOST`
- `XXL_JOB_ADMIN_ADDRESSES` / `XXL_JOB_ACCESS_TOKEN`
- `HEI_MQ_AUDIT_CONSUME_ENABLED`：是否启用审计消费者

### 日志（SLF4J + Logback，对齐 hei-fastapi）

- 技术栈：Spring Boot structured logging + MDC（`common-web` / `common-log`）
- 控制台 / 滚动文件：`./logs/hei-boot-admin.log`（可用 `LOGGING_FILE_NAME` 覆盖）
- 滚动策略：50MB / 保留 30 天 / 总上限 3GB（`LOGGING_MAX_FILE_SIZE` / `LOGGING_MAX_HISTORY` / `LOGGING_TOTAL_SIZE_CAP`）
- 格式开关：`hei.logging.json` / 环境变量 `LOG_JSON`（**默认 `true`** → Logstash JSON；`false` → 键值可读格式）
- 每请求访问日志：logger `access`，消息 `http.access`（含 `status_code`、`duration_ms`）
- MDC 字段（snake_case）：`request_id`、`trace_id`、`span_id`、`method`、`path`、`client_ip`、`user_agent`、`account_id`、
  `account_type`；JSON 另含 `service` / `service_version` / `environment`
- Profile：
    - `local`：业务包 `debug`，并可看 MyBatis SQL（`LOGGING_LEVEL_IBATIS`）；默认 JSON
    - `dev`：业务包 `info`；默认 JSON
    - `prod`：业务包 `warn`；默认 JSON

常用环境变量：`LOG_JSON`、`LOGGING_LEVEL_HEI`、`LOGGING_LEVEL_ROOT`、`LOGGING_FILE_NAME`、`LOGGING_LEVEL_ACCESS`

默认本地库：

```text
jdbc:postgresql://127.0.0.1:5432/hei_boot
username: hei
password: hei
```

## 常用命令

```bash
# 编译 admin 及其依赖
mvn -pl app/admin -am package -DskipTests

# 编译本地 XXL-JOB Admin
mvn -pl app/xxl-job -am package -DskipTests

# 运行不依赖基础设施的单元测试
mvn -pl app/admin -am test

# 启动 admin
mvn -pl app/admin -am spring-boot:run
```

## 项目结构

```text
hei-boot
├── app
│   ├── admin                  # 管理端 API + XXL-JOB Executor + Flyway
│   └── xxl-job                # 本地-only XXL-JOB Admin
├── common                     # 通用能力（mq / job / satoken / observability …）
├── module-api                 # 跨模块窄接口
├── module                     # auth / iam / sys / user / message / dashboard / biz（样板）
├── web                        # admin / portal / admin-uniapp（各自独立，无 packages 层）
├── docs                       # 文档索引
├── deploy/helm                # K8s 参考 Chart
└── script
    ├── docker                 # compose + 说明
    ├── sql                    # 说明与 XXL seed（Flyway 权威在 app/admin）
    ├── perf                   # k6
    └── security               # ZAP baseline
```

## 模型与 Controller 约定

- ID 使用 `String`，时间使用 `OffsetDateTime`；JSON 结构化字段用 `Map`/`List`。
- 库字段与对外 JSON 使用下划线（Jackson `SNAKE_CASE`）；Java 驼峰 + Lombok。
- **入参**：模块 `param` 包下 `*Param`（通用 `IdParam`/`IdsParam`）。
- **出参**：优先返回 **entity**；字段差大或聚合时用模块 `result` 包下 `*Result`。
- 不引入软删标记 unless 业务表本身需要。

### 实体继承（单继承）

| 基类             | 用途                                           |
|----------------|----------------------------------------------|
| `CommonEntity` | 业务表基类：`id` + `createdAt/By` + `updatedAt/By` |

表无完整审计列时，在 `@TableName(excludeProperty = {...})` 中排除多余字段（如 `MsgNoticeRead`、审计日志/outbox）。

审计字段由 MyBatis-Plus `HeiMetaObjectHandler` 自动填充。

## 开发约定

- Maven artifact 不加 `hei-` 前缀，保持 `auth`、`iam`、`common-core` 这类短名称。
- 包名统一使用 `github.jiangbyte.io`；业务实现布局为 `github.jiangbyte.io.{module}.modules.{feature}.{layer}`。
- 领域 Service：`XxxService extends IService<Entity>`（接口）+ `service/impl/XxxServiceImpl extends ServiceImpl<Mapper, Entity>`；无单一主实体的编排服务可不挂 `IService`。Controller / 同模块协作 / `*ApiProvider` 只注入接口。
- `module-api` 只放跨模块窄接口与必要的跨模块值对象（如 `FileInfo`、`AccountInfo`）；不放 entity / HTTP `param`/`result`。
- 跨模块实现用 `*ApiProvider`（`@Service`）委托本模块 `*Service`；Controller 只依赖本模块 Service。消费者 Maven 只依赖对方 `*-api`，不依赖对方 impl。
- 权限注解使用 Sa-Token 官方 `@SaCheckPermission` / `@SaCheckLogin`（`type = StpKit.TYPE_ADMIN|PORTAL`）。
- API 文档：Knife4j，`http://127.0.0.1:8080/doc.html`。
- 关联 id 回显优先 Dromara easy-trans（`@Trans` / `TransPojo`）。
- Mapper Join 优先 `BaseJoinMapper<T>`；读写分离 `@ReadDataSource` / `@WriteDataSource`。
- 新增业务模块：见上文「二次开发（社区）」checklist（reactor → `dependencyManagement` → **`app/admin` 显式依赖** → 追加 Flyway）。

## 代码贡献

欢迎提交 Issue、讨论和 Pull Request。由于当前项目仍在模板与模型迁移阶段，贡献代码时请优先保证
结构稳定和模型一致性。

建议流程：

```bash
git checkout -b feature/your-change
mvn clean package -DskipTests
git commit -m "feat: describe your change"
```

提交 PR 前请确认：

- Controller：入参 `*Param`，出参优先 entity，必要时 `result` 包 `*Result`。
- JSON 对外字段保持 `snake_case`；线格式标量为字符串（见 stringly Jackson）。
- 新增模块遵守 `app`、`common`、`module-api`、`module`、`web/*` 边界；前端不建 `web/packages`。
- 公共能力优先 `common/*`，跨模块窄接口优先 `module-api/*`。
- 不引入与 Spring Boot 4、JDK 21、Jakarta 生态不兼容的依赖。
- 配置项提供本地默认值，生产敏感配置走环境变量；文档与脚本随行为同步更新。

## 开源协议

本项目使用 [MIT License](LICENSE) 开源协议。
