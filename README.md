# HEI Boot

![JDK](https://img.shields.io/badge/JDK-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Multi--Module-C71A36?logo=apachemaven&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Supported-4169E1?logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-Supported-DC382D?logo=redis&logoColor=white)
![License](https://img.shields.io/badge/License-Apache%202.0-blue)

HEI Boot 是一个 Spring Boot 一体化应用脚手架：**一个后端应用同时提供管理端（Admin）与门户（Portal）两套 API**，配合同仓维护的三个前端工程，覆盖账号认证、组织权限（RBAC）、系统管理、消息反馈与运营工作台等常用能力，开箱即用、可按需裁剪。

- **后端**：JDK 21 · Spring Boot 4.1 · Maven 多模块 · PostgreSQL · Redis · Sa-Token · MyBatis-Plus · JustAuth · SnailJob
- **前端**：`web/admin`（Vue 3 / Naive UI）· `web/portal`（React / Ant Design）· `web/admin-uniapp`（uni-app）
- **数据约定**：Java 层驼峰命名；对外 JSON 字段使用 `snake_case`，标量（含 boolean / 数字）统一按字符串收发

## 功能特性

**认证与账号（`module/auth`）**

- 双端登录：ADMIN / PORTAL 两套独立账号体系与会话（Sa-Token + Redis）
- 账号 / 邮箱 / 手机号多种身份登录，密码登录（RSA 加密传输）与验证码登录（OTP）
- 图形验证码（SVG / PNG）、登录失败锁定与限流防护
- 忘记 / 重置密码、注册（门户）
- 三方登录（JustAuth）：GitHub、Gitee、QQ、微信开放平台、微信小程序；管理员可绑定 / 解绑

**组织与权限（`module/iam`）**

- 账号、角色、部门、用户组、岗位管理
- 菜单资源、资源模块、客户端资源多层授权（RBAC）
- 在线会话查询与强制下线

**系统管理（`module/sys`）**

- 数据字典、系统配置（含敏感配置加密存储）、Banner、文件存储（S3 / 本地）
- 弱口令清单、操作审计（Redis Stream 异步落库）、代码生成
- 公告 / 通知、意见反馈（管理端 + 门户双端）

**运营与调度**

- 运营工作台（`module/dashboard`）：账号、会话、审计、文件等核心指标概览与 7 日趋势
- SnailJob 分布式任务：注销账号清理、Banner 定时上下架、审计量级告警

## 技术栈

| 分类 | 选型 |
| :--- | :--- |
| 语言 / 框架 | JDK 21、Spring Boot 4.1、Spring MVC、Maven 多模块（Flatten + CI-Friendly 版本） |
| 数据 | PostgreSQL、MyBatis-Plus（MyBatis-Plus-Join）、dynamic-datasource（读写分离）、Druid |
| 缓存 / 会话 | Redis、Redisson、Sa-Token、lock4j |
| 安全 | Sa-Token 双端鉴权、JustAuth、图形验证码、登录锁定 / 限流、AES / RSA 加密 |
| 观测 / 运维 | springdoc + Knife4j、Actuator + Prometheus、结构化日志、操作审计 |
| 任务 | SnailJob 2.0（Executor 客户端内嵌） |
| 其他 | easy-trans（关联回显）、hutool、AWS SDK S3、spring-cloud-vault（可选） |

| 前端 | 技术 |
| :--- | :--- |
| `web/admin` | Vue 3.5、Naive UI 2、Pinia、Vue Router、Vite 8、TypeScript |
| `web/portal` | React 19、Ant Design 6、zustand、Vite 8、TypeScript |
| `web/admin-uniapp` | uni-app 3（H5 / 小程序） |

## 架构

后端只有**一个可运行应用** `app/admin`，按请求前缀区分管理端与门户两套接口，双账号体系会话相互隔离；业务能力按模块划分，由 `app/admin` 显式依赖装配。

| 分层 | 说明 |
| :--- | :--- |
| `common/*` | 通用能力：web、mybatis、redis、satoken、security、log、oss、notify、job、doc |
| `module-api/*` | 跨模块窄接口（auth-api / iam-api / sys-api / profile-api） |
| `module/*` | 业务模块：auth、iam、sys、profile、dashboard，以及样板模块 biz |
| `web/*` | 独立前端工程（无共享依赖层） |

## 快速开始

### 环境要求

- JDK 21、Maven 3.9+
- PostgreSQL、Redis
- Node.js 22+ 与 pnpm 9+（前端）

### 1. 初始化数据库

以 `scripts/db.sql` 为权威库表与种子数据源（含全部表结构、菜单、权限、字典、配置与 `superadmin` 账号）。

```bash
# 创建数据库
createdb -U postgres -h 127.0.0.1 hei_boot

# 导入库表与种子数据（也可用 Navicat / DataGrip 等工具直接执行该文件）
psql -U postgres -h 127.0.0.1 -d hei_boot -f scripts/db.sql
```

### 2. 启动后端

开发默认配置见 `app/admin/src/main/resources/application-dev.yml`：

- 数据库：`jdbc:postgresql://127.0.0.1:5432/hei_boot`（`postgres` / `123456`）
- Redis：`127.0.0.1:6379`（密码 `123456`，库 0）

```bash
mvn -pl app/admin -am spring-boot:run
```

启动后可访问：

| 地址 | 说明 |
| :--- | :--- |
| http://127.0.0.1:8000 | Admin API |
| http://127.0.0.1:8000/doc.html | Knife4j 接口文档 |
| http://127.0.0.1:8000/actuator/health | 健康检查 |

> SnailJob 客户端在开发环境默认启用（连接 `127.0.0.1:17888`）。本地未启动 SnailJob Server 时任务不会执行，但不影响主流程；如需关闭可设置 `SNAIL_JOB_ENABLED=false`。

### 3. 启动前端

```bash
cd web/admin && pnpm install && pnpm dev    # http://127.0.0.1:5173
cd web/portal && pnpm install && pnpm dev   # http://127.0.0.1:5174
```

前端开发模式通过 Vite 将 `/api` 代理到后端 `http://127.0.0.1:8000`。

### 默认账号

| 端 | 地址 | 账号 | 密码 |
| :--- | :--- | :--- | :--- |
| Admin | http://localhost:5173 | `superadmin` | `123456` |
| Portal | http://localhost:5174 | `user` | `123456` |

> 登录需要图形验证码（后端将验证码明文小写的 SHA-256 存入 Redis `captcha:{id}`，TTL 5 分钟）。本地自动化调试可用 `scripts/read_captcha.py` 从 Redis 还原验证码明文。**生产环境首次启动后请立即修改默认密码。**

## 界面预览

### 门户 Portal

<table>
  <tr>
    <td width="50%"><img src="docs/images/portal-login.png" alt="门户登录" /></td>
    <td width="50%"><img src="docs/images/portal-home.png" alt="门户首页" /></td>
  </tr>
  <tr>
    <td align="center">登录</td>
    <td align="center">首页</td>
  </tr>
</table>

### 管理端 Admin · 登录 / 工作台

<table>
  <tr>
    <td width="50%"><img src="docs/images/admin-login.png" alt="管理端登录" /></td>
    <td width="50%"><img src="docs/images/admin-dashboard.png" alt="运营工作台" /></td>
  </tr>
  <tr>
    <td align="center">登录</td>
    <td align="center">运营工作台</td>
  </tr>
</table>

### 管理端 Admin · 组织权限

<table>
  <tr>
    <td width="50%"><img src="docs/images/admin-iam-account.png" alt="账号管理" /></td>
    <td width="50%"><img src="docs/images/admin-iam-role.png" alt="角色管理" /></td>
  </tr>
  <tr>
    <td align="center">账号管理</td>
    <td align="center">角色管理</td>
  </tr>
  <tr>
    <td width="50%"><img src="docs/images/admin-iam-resource.png" alt="资源授权" /></td>
    <td></td>
  </tr>
  <tr>
    <td align="center">资源授权</td>
    <td></td>
  </tr>
</table>

### 管理端 Admin · 系统运维

<table>
  <tr>
    <td width="50%"><img src="docs/images/admin-sys-config.png" alt="系统配置" /></td>
    <td width="50%"><img src="docs/images/admin-sys-dict.png" alt="字典管理" /></td>
  </tr>
  <tr>
    <td align="center">系统配置</td>
    <td align="center">字典管理</td>
  </tr>
  <tr>
    <td width="50%"><img src="docs/images/admin-sys-audit.png" alt="操作审计" /></td>
    <td width="50%"><img src="docs/images/admin-sys-codegen.png" alt="代码生成" /></td>
  </tr>
  <tr>
    <td align="center">操作审计</td>
    <td align="center">代码生成</td>
  </tr>
</table>

## 项目结构

```text
hei-boot
├── app
│   └── admin                    # 唯一可运行应用：Admin / Portal API + SnailJob 客户端
├── common                       # 通用能力（core / web / mybatis / redis / satoken / security / log / oss / notify / job / doc）
├── module-api                   # 跨模块窄接口（auth / iam / sys / profile）
├── module                       # 业务模块（auth / iam / sys / profile / dashboard / biz 样板）
├── web                          # 前端（admin / portal / admin-uniapp）
├── docs                         # 文档与界面截图
└── scripts
    ├── db.sql                   # 权威库表与种子数据
    └── read_captcha.py          # 开发辅助：从 Redis 还原登录图形验证码
```

## 主要 API

| 前缀 | 用途 |
| :--- | :--- |
| `/api/v1/admin/**` | 管理端接口 |
| `/api/v1/portal/**` | 门户接口 |
| `/api/v1/files/**` | 公开文件读取（可配置） |
| `/api/*/internal/**` | 集群内部接口（勿对公网暴露） |
| `/actuator/**` | 健康与指标（勿对公网暴露） |
| `/doc.html`、`/v3/api-docs` | OpenAPI / Knife4j 接口文档 |

常用接口：`/api/v1/{admin|portal}/login`、`/captcha`、`/oauth/**`、`/sys/**`（账号、角色、字典、配置、公告、反馈等）、`/profile/**`、`/dashboard/overview`。

## 配置说明

配置文件位于 `app/admin/src/main/resources/`，包含 `application.yml` 与 `application-dev.yml` / `application-local.yml` / `application-prod.yml` 三个 profile（默认 `dev`，通过 `SPRING_PROFILES_ACTIVE` 切换）。

| 配置项 | 说明 | 默认（dev） |
| :--- | :--- | :--- |
| `DB_WRITE_URL` / `DB_WRITE_USERNAME` / `DB_WRITE_PASSWORD` | 主库连接（可用 `DB_READ_*` 配置读库） | `127.0.0.1:5432/hei_boot` / `postgres` / `123456` |
| `REDIS_HOST` / `REDIS_PORT` / `REDIS_PASSWORD` / `REDIS_DATABASE` | 会话、验证码与审计 | `127.0.0.1` / `6379` / `123456` / `0` |
| `HEI_CONFIG_CRYPTO_KEY` | 敏感配置加密密钥（Fernet），无默认值 | 开发内置默认 |
| `SNAIL_JOB_ENABLED` | 是否启用 SnailJob 客户端 | dev 开 / prod 关 |
| `HEI_LOG_AUDIT_CONSUME_ENABLED` | 操作审计异步消费开关 | `true` |
| `LOG_JSON` | 日志格式（JSON / 键值） | 键值 |

## 生产部署

### 构建镜像

仓库为 `app/admin`、`web/admin`、`web/portal` 分别提供 Dockerfile：

```bash
# 后端镜像（生产包排除样板业务模块 biz）
mvn -pl app/admin -am -P'!with-biz' package -DskipTests
docker build -f app/admin/Dockerfile -t hei-boot-admin .
```

前端镜像由各自 Dockerfile 构建（nginx 托管静态资源，`/api` 反向代理到后端，`BACKEND_URL` 可配）。

### 生产必填环境变量

| 变量 | 说明 |
| :--- | :--- |
| `DB_URL` / `DB_USERNAME` / `DB_PASSWORD` | 主库连接（也可用 `DB_WRITE_*` / `DB_READ_*`） |
| `REDIS_HOST`（可选 port / password / database） | 会话与 Redis Stream 审计 |
| `HEI_CONFIG_CRYPTO_KEY` | 敏感配置 Fernet 密钥（无默认值，首次上线必须设置） |

可选：`SNAIL_JOB_ENABLED` / `SNAIL_JOB_SERVER_HOST` / `SNAIL_JOB_NAMESPACE` / `SNAIL_JOB_GROUP` / `SNAIL_JOB_TOKEN`、`HEI_LOG_AUDIT_CONSUME_ENABLED`、`LOG_JSON`、`HEI_SECURITY_TRUST_FORWARDED_HEADERS`。

### 上线检查清单

- 轮换 `superadmin` 默认密码与 `HEI_CONFIG_CRYPTO_KEY`
- 关闭文档 / Actuator / Druid 控制台公网暴露
- 仅在可信反向代理后开启 `hei.security.trust-forwarded-headers`
- 生产建议 `SNAIL_JOB_ENABLED=false`，由独立 SnailJob Server 调度

## 二次开发

1. 在 `module/` 新增业务模块：`@AutoConfiguration` + `@ComponentScan`，跨模块契约放入 `module-api/`
2. 在根 `pom.xml` `dependencyManagement` 登记版本，并加入 `module/pom.xml` 的 modules
3. 在 `app/admin/pom.xml` **显式依赖**新模块（样板 `biz` 走 `with-biz` profile）
4. 库表 / 菜单 / 权限 / 字典 / 配置种子统一维护在 `scripts/db.sql`
5. 配置改动走 `application-*.yml` 或环境变量（如 `hei.security.ignore-urls`）

开发约定：业务表继承 `BaseEntity`（`id` + 审计四字段）；领域服务 `XxxService` / `XxxServiceImpl`；权限用 `@SaCheckPermission` / `@SaCheckLogin`；关联回显用 easy-trans，联表查询用 MyBatis-Plus-Join，只读库用 `@ReadDataSource`。

## 代码贡献

欢迎 Issue 与 PR。提交前请确认：

- Controller 入参与出参符合 `snake_case` 字符串线格式约定
- 遵守模块边界：`app` / `common` / `module-api` / `module` / `web/*`
- 兼容 JDK 21、Spring Boot 4、Jakarta 体系；敏感配置走环境变量；文档随行为同步

```bash
git checkout -b feature/your-change
mvn clean package -DskipTests
git commit -m "feat: describe your change"
```

## 许可证

本项目使用 [Apache License 2.0](LICENSE) 开源协议。
