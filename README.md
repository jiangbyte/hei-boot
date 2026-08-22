# HEI Boot

![JDK](https://img.shields.io/badge/JDK-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Supported-4169E1?logo=postgresql&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-Supported-4479A1?logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-Supported-DC382D?logo=redis&logoColor=white)
![License](https://img.shields.io/badge/License-Apache_2.0-blue)
![Version](https://img.shields.io/badge/version-1.0.0--beta-orange)

**HEI Boot** 是一套面向中后台场景的 Spring Boot 工程化脚手架：单个应用同时提供 **Admin** 与 **Portal** 双端 API，统一认证、权限、运维与消息能力，并与 [hei-gin](https://github.com/jiangbyte/hei-gin)、[hei-fastapi](https://github.com/jiangbyte/hei-fastapi) 等姊妹后端保持契约一致。

> 当前版本：`1.0.0-beta` · 协议：[Apache License 2.0](LICENSE)

## 目录

- [功能特性](#功能特性)
- [前端姊妹项目](#前端姊妹项目)
- [技术栈](#技术栈)
- [工程结构](#工程结构)
- [快速开始](#快速开始)
- [默认账号](#默认账号)
- [姊妹项目](#姊妹项目)
- [License](#license)

## 功能特性

API 前缀统一为 `/api/v1/admin/*` 与 `/api/v1/portal/*`，常见中后台能力按模块划分如下：

| 模块 | 说明 |
| --- | --- |
| 双端账号体系 | ADMIN / PORTAL 独立会话（Sa-Token）；密码 RSA 传输、验证码登录、失败锁定与限流；JustAuth 三方登录（可配置） |
| RBAC 权限 | 账号 / 角色 / 部门 / 用户组 / 岗位；菜单、按钮与 API 资源授权；在线会话踢出 |
| 系统管理 | 字典、动态配置（敏感项加密存储）、Banner、公告 / 通知、意见反馈、弱口令库 |
| 对象存储 | S3 兼容存储（MinIO / RustFS / 阿里云 OSS 等），直链或预签名访问 |
| 运维能力 | 操作审计与告警、登录日志、运营工作台概览、内置任务调度（`sys_job`） |
| 代码生成 | 单表 / 树表 / 主子表方案，预览与 ZIP 下载（含菜单权限 SQL） |
| 实名认证 | 工单提交与审核、敏感字段脱敏审计、可扩展第三方 Provider |
| 业务扩展 | `module/biz` 示例模块，可按同样模式横向扩展 |

## 前端姊妹项目

| 项目 | 说明 | 界面预览 |
| --- | --- | --- |
| [**hei-admin**](https://github.com/jiangbyte/hei-admin) | Vue 3 管理端，对接 `/api/v1/admin/*` | [README 截图](https://github.com/jiangbyte/hei-admin#界面预览) |
| [**hei-portal**](https://github.com/jiangbyte/hei-portal) | React 门户，对接 `/api/v1/portal/*` | [README 截图](https://github.com/jiangbyte/hei-portal) |
| [**hei-admin-uniapp**](https://github.com/jiangbyte/hei-admin-uniapp) | uni-app 管理端移动端 | [README](https://github.com/jiangbyte/hei-admin-uniapp) |

## 技术栈

| 层级 | 技术 |
| --- | --- |
| 后端 | JDK 21 · Spring Boot 4.1 · Maven 多模块 · 虚拟线程 |
| 持久化 | PostgreSQL / MySQL · MyBatis-Plus · Dynamic Datasource |
| 缓存 / 会话 | Redis · Redisson · Sa-Token |
| 文档 | Knife4j / SpringDoc |
| 其他 | JustAuth · AWS SDK v2（S3）· Hutool · MapStruct |

## 工程结构

```text
hei-boot/
├── app/admin/              # 可启动应用（Admin + Portal API）
├── common/                 # 公共能力（web / security / mybatis / redis / oss / job / log …）
├── module-api/             # 模块对外 API 契约
├── module/                 # 业务实现（auth / iam / sys / profile / workspace / biz）
└── scripts/                # 数据库脚本与工具
```

## 快速开始

### 环境要求

- JDK **21**、Maven **3.9+**
- PostgreSQL **或** MySQL 8+、Redis

### 1. 初始化数据库

**PostgreSQL：**

```bash
createdb -U postgres -h 127.0.0.1 hei_boot
psql -U postgres -h 127.0.0.1 -d hei_boot -f scripts/db.postgresql.sql
```

**MySQL 8+：**

```bash
mysql -u root -p -e "CREATE DATABASE hei_boot DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
mysql -u root -p hei_boot < scripts/db.mysql.sql
```

方言由 JDBC URL 自动识别。开发配置见 [`app/admin/src/main/resources/application-dev.yml`](app/admin/src/main/resources/application-dev.yml)（`DB_WRITE_*` / Redis / 对象存储）。

> 本地 / 演示环境以 SQL 脚本全量初始化；Flyway 默认关闭。

### 2. 启动后端

```bash
mvn -pl app/admin -am spring-boot:run
```

| 项 | 地址 |
| --- | --- |
| API | http://127.0.0.1:8000 |
| 接口文档 | http://127.0.0.1:8000/doc.html |

### 3. 启动前端（可选）

前端为独立仓库，默认将 `/api` 代理到本后端 `http://127.0.0.1:8000`：

```bash
# 管理端 → http://127.0.0.1:5173
git clone https://github.com/jiangbyte/hei-admin.git && cd hei-admin
pnpm install && pnpm dev

# 门户 → http://127.0.0.1:5174
git clone https://github.com/jiangbyte/hei-portal.git && cd hei-portal
pnpm install && pnpm dev
```

详见 [hei-admin](https://github.com/jiangbyte/hei-admin) / [hei-portal](https://github.com/jiangbyte/hei-portal) 各仓库 README。

## 默认账号

| 端 | 前端仓库 | 地址 | 账号 | 密码 |
| --- | --- | --- | --- | --- |
| Admin | [hei-admin](https://github.com/jiangbyte/hei-admin) | http://127.0.0.1:5173 | `superadmin` | `123456` |
| Portal | [hei-portal](https://github.com/jiangbyte/hei-portal) | http://127.0.0.1:5174 | `user` | `123456` |

> 仅供本地演示。部署后请修改默认密码，并更换配置加密密钥、对象存储凭证等敏感项。

## 姊妹项目

| 项目 | 说明 | 协议 |
| --- | --- | --- |
| [**hei-boot**](https://github.com/jiangbyte/hei-boot) | Spring Boot 脚手架（本仓库） | Apache License 2.0 |
| [**hei-admin**](https://github.com/jiangbyte/hei-admin) | Vue 3 管理端前端 | Apache License 2.0 |
| [**hei-portal**](https://github.com/jiangbyte/hei-portal) | React 门户前端 | Apache License 2.0 |
| [**hei-admin-uniapp**](https://github.com/jiangbyte/hei-admin-uniapp) | uni-app 管理端移动端 | Apache License 2.0 |
| [**hei-gin**](https://github.com/jiangbyte/hei-gin) | Go / Gin 后端 | Apache License 2.0 |
| [**hei-fastapi**](https://github.com/jiangbyte/hei-fastapi) | FastAPI 后端 | Apache License 2.0 |

## License

本项目基于 [Apache License 2.0](LICENSE) 开源。完整条款见 [LICENSE](LICENSE)，版权声明见 [NOTICE](NOTICE)。
