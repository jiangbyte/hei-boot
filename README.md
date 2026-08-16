# HEI Boot

![JDK](https://img.shields.io/badge/JDK-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Supported-4169E1?logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-Supported-DC382D?logo=redis&logoColor=white)
![Vue](https://img.shields.io/badge/Admin-Vue%203-4FC08D?logo=vuedotjs&logoColor=white)
![React](https://img.shields.io/badge/Portal-React-61DAFB?logo=react&logoColor=black)
![License](https://img.shields.io/badge/License-Apache_2.0-blue)
![Version](https://img.shields.io/badge/version-1.0.0--beta-orange)

**HEI Boot** 是一套开箱即用的 Spring Boot 工程化脚手架：单个后端应用同时提供 **Admin** 与 **Portal** 双端 API，同仓维护 Vue 3 / React / uni-app 前端，覆盖认证授权、组织权限、系统运维、消息通知与运营看板等常见后台能力。

> 当前版本：`1.0.0-beta` · 协议：[Apache License 2.0](LICENSE)

## 目录

- [界面预览](#界面预览)
- [功能特性](#功能特性)
- [技术栈](#技术栈)
- [工程结构](#工程结构)
- [快速开始](#快速开始)
- [默认账号](#默认账号)
- [相关文档](#相关文档)
- [姊妹项目](#姊妹项目)
- [License](#license)

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

### 管理端 · 登录 / 工作台

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

### 管理端 · 组织权限

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
    <td width="50%"><img src="docs/images/admin-iam-dept.png" alt="部门管理" /></td>
    <td width="50%"><img src="docs/images/admin-iam-group.png" alt="用户组管理" /></td>
  </tr>
  <tr>
    <td align="center">部门管理</td>
    <td align="center">用户组管理</td>
  </tr>
  <tr>
    <td width="50%"><img src="docs/images/admin-iam-position.png" alt="岗位管理" /></td>
    <td width="50%"><img src="docs/images/admin-iam-resource.png" alt="资源授权" /></td>
  </tr>
  <tr>
    <td align="center">岗位管理</td>
    <td align="center">资源授权</td>
  </tr>
  <tr>
    <td width="50%"><img src="docs/images/admin-iam-resource-module.png" alt="资源模块" /></td>
    <td width="50%"><img src="docs/images/admin-iam-client-resource.png" alt="客户端资源" /></td>
  </tr>
  <tr>
    <td align="center">资源模块</td>
    <td align="center">客户端资源</td>
  </tr>
</table>

### 管理端 · 系统运维

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
  <tr>
    <td width="50%"><img src="docs/images/admin-sys-session.png" alt="在线会话" /></td>
    <td width="50%"><img src="docs/images/admin-sys-login-log.png" alt="登录日志" /></td>
  </tr>
  <tr>
    <td align="center">在线会话</td>
    <td align="center">登录日志</td>
  </tr>
</table>

### 管理端 · 消息与文件

<table>
  <tr>
    <td width="50%"><img src="docs/images/admin-sys-banner.png" alt="Banner 管理" /></td>
    <td width="50%"><img src="docs/images/admin-message-notice.png" alt="公告通知" /></td>
  </tr>
  <tr>
    <td align="center">Banner 管理</td>
    <td align="center">公告通知</td>
  </tr>
  <tr>
    <td width="50%"><img src="docs/images/admin-message-feedback.png" alt="意见反馈" /></td>
    <td width="50%"><img src="docs/images/admin-sys-file.png" alt="文件管理" /></td>
  </tr>
  <tr>
    <td align="center">意见反馈</td>
    <td align="center">文件管理</td>
  </tr>
</table>

### 管理端 · 业务示例

<table>
  <tr>
    <td width="50%"><img src="docs/images/admin-biz-order.png" alt="订单示例" /></td>
    <td></td>
  </tr>
  <tr>
    <td align="center">订单示例</td>
    <td></td>
  </tr>
</table>

## 功能特性

- **双端账号体系**：ADMIN / PORTAL 独立会话（Sa-Token）；密码 RSA 传输、验证码登录、失败锁定与限流；JustAuth 三方登录（可配置）
- **RBAC 权限**：账号 / 角色 / 部门 / 用户组 / 岗位；菜单、按钮与 API 资源授权；在线会话踢出
- **系统管理**：字典、动态配置（敏感项 Fernet 加密）、Banner、公告 / 通知、意见反馈、弱口令库
- **对象存储**：S3 兼容存储（如 RustFS / MinIO / 阿里云 OSS 等），直链或预签名访问
- **运维能力**：操作审计与告警、登录日志、运营工作台（概览与近 7 日趋势）、内置任务调度（`sys_job`）
- **代码生成**：单表 / 树表 / 主子表方案，预览与 ZIP 下载（含前端与菜单权限 SQL）
- **三端前端**：`web/admin`（Vue 3 + Naive UI）、`web/portal`（React + Ant Design）、`web/admin-uniapp`（uni-app）

## 技术栈

| 层级 | 技术 |
| --- | --- |
| 后端 | JDK 21 · Spring Boot 4.1 · Maven 多模块 · 虚拟线程 |
| 持久化 | PostgreSQL · MyBatis-Plus · Dynamic Datasource · Druid |
| 缓存 / 会话 | Redis · Redisson · Sa-Token |
| 文档 | Knife4j / SpringDoc |
| 其他 | JustAuth · AWS SDK v2（S3）· Hutool · MapStruct |
| 管理端 | Vue 3 · Vite · TypeScript · Naive UI · Pinia · UnoCSS |
| 门户 | React 19 · Vite · TypeScript · Ant Design · Zustand · UnoCSS |
| 移动端 | uni-app 3 · Vue 3 · TypeScript · uview-pro |

## 工程结构

```text
hei-boot
├── app/admin                 # 可启动应用（Admin + Portal API）
├── common/                   # 公共能力（web / security / mybatis / redis / oss / job / log …）
├── module-api/               # 模块对外 API 契约
├── module/                   # 业务实现（auth / iam / sys / profile / dashboard / biz）
├── web/
│   ├── admin                 # 管理端（Vue 3）
│   ├── portal                # 门户（React）
│   └── admin-uniapp          # 管理端 uni-app
├── scripts/db.sql            # 数据库结构 + 种子数据
└── docs/images               # README 截图
```

## 快速开始

### 环境要求

- JDK **21**、Maven **3.9+**
- PostgreSQL、Redis
- Node.js **22+**、pnpm **9+**（前端）

### 1. 初始化数据库

```bash
createdb -U postgres -h 127.0.0.1 hei_boot
psql -U postgres -h 127.0.0.1 -d hei_boot -f scripts/db.sql
```

> Flyway 默认关闭；本地/演示环境以 `scripts/db.sql` 为准重建库表与种子数据。

### 2. 启动后端

按需修改 [`app/admin/src/main/resources/application-dev.yml`](app/admin/src/main/resources/application-dev.yml) 中的数据源、Redis 与对象存储配置，然后：

```bash
mvn -pl app/admin -am spring-boot:run
```

| 项 | 地址 |
| --- | --- |
| API | http://127.0.0.1:8000 |
| 接口文档 | http://127.0.0.1:8000/doc.html |

### 3. 启动前端

```bash
# 管理端 → http://127.0.0.1:5173
cd web/admin && pnpm install && pnpm dev

# 门户 → http://127.0.0.1:5174
cd web/portal && pnpm install && pnpm dev
```

uni-app 端见 [`web/admin-uniapp/README.md`](web/admin-uniapp/README.md)。

## 默认账号

| 端 | 地址 | 账号 | 密码 |
| --- | --- | --- | --- |
| Admin | http://localhost:5173 | `superadmin` | `123456` |
| Portal | http://localhost:5174 | `user` | `123456` |

> 仅供本地演示。部署到非本机环境后请立即修改默认密码，并更换配置加密密钥、对象存储凭证等敏感项。

## 相关文档

| 文档 | 说明 |
| --- | --- |
| [`web/admin/README.md`](web/admin/README.md) | 管理端前端说明与环境变量 |
| [`web/portal/README.md`](web/portal/README.md) | 门户前端说明与环境变量 |
| [`web/admin-uniapp/README.md`](web/admin-uniapp/README.md) | uni-app 端说明 |
| [`app/admin/src/main/resources/application-dev.yml`](app/admin/src/main/resources/application-dev.yml) | 开发环境配置 |
| [`scripts/db.sql`](scripts/db.sql) | 数据库结构与种子数据 |

## 姊妹项目

| 项目 | 说明 | 协议 |
| --- | --- | --- |
| [**hei-boot**](https://github.com/jiangbyte/hei-boot) | Spring Boot 工程化脚手架（本仓库） | Apache License 2.0 |
| [**hei-gin**](https://github.com/jiangbyte/hei-gin) | Go 轻量级后端框架 | Apache License 2.0 |
| [**hei-fastapi**](https://github.com/jiangbyte/hei-fastapi) | FastAPI 原型项目（早期阶段，仅供参考） | Apache License 2.0 |

## License

本项目基于 [Apache License 2.0](LICENSE) 开源。完整条款见 [LICENSE](LICENSE)，版权声明见 [NOTICE](NOTICE)。
