## 目标
重写 `README.md` 为一份正式、结构清晰的中文 README，并用浏览器重新登录截图，替换 `docs/images/` 下的界面预览图。按磁盘真实状态编写（不引用已删除的 `script/docker`、`deploy/helm` 等路径），并新增一个从 Redis 读取登录图形验证码的小脚本辅助自动化登录。

## 1. 环境核实
- 确认 5173 / 5174 / 8000 / 6379 可达，Redis 密码与 db（dev 配置默认 127.0.0.1:6379, password 123456, db 0）。

## 2. 新增脚本 `scripts/read_captcha.py`（读取 Redis 验证码）
- 纯 Python 3 标准库（socket + hashlib，走 RESP2 协议直连 Redis，无需 redis-cli / pip 依赖）。
- 列出 Redis 中 `captcha:*` 的 key；由于库里存的是验证码的 SHA-256 hex，脚本用字母表 `23456789ABCDEFGHJKLMNPQRSTUVWXYZ`（32⁴≈104 万组合，约 1–2 秒）爆破还原 4 位明文，输出 `captchaId → 验证码`。
- 支持参数覆盖 host/port/password/db；`--new` 可选：先调 `GET /api/v1/admin/captcha` 生成新验证码再读取。
- 用于后续浏览器登录时填入验证码（验证码一次性使用，页面每次渲染后需重新读取）。

## 3. 浏览器截图（使用 browser-use:control-browser 技能，主 agent 亲自操作）
统一视口（如 1440×900），每页等待加载完成后再截图，输出到 `docs/images/`（覆盖同名旧图）。

**Admin（http://localhost:5173，superadmin/123456）：**
| 页面 | 路由 | 输出文件 |
|---|---|---|
| 登录页 | `/auth/login` | `admin-login.png` |
| 运营工作台 | `/dashboard` | `admin-dashboard.png` |
| 账号管理 | `/iam/account` | `admin-iam-account.png` |
| 角色管理 | `/iam/role` | `admin-iam-role.png` |
| 资源授权 | `/resource-auth` | `admin-iam-resource.png` |
| 系统配置 | `/sys/config` | `admin-sys-config.png` |
| 字典管理 | `/sys/dict` | `admin-sys-dict.png` |
| 操作审计 | `/sys/audit` | `admin-sys-audit.png` |
| 代码生成 | `/sys/codegen` | `admin-sys-codegen.png` |

登录流程：页面加载出验证码后立即运行 `read_captcha.py` 取码 → 填入表单（superadmin/123456）→ 登录 → 逐个访问页面截图。

**Portal（http://localhost:5174，user/123456）：**
| 页面 | 路由 | 输出文件 |
|---|---|---|
| 登录页 | `/auth/login` | `portal-login.png` |
| 首页 | `/`（登录后） | `portal-home.png` |

说明：`docs/images/` 中未被精选集引用的旧图（如 dept/group/position/banner/session/login-log/消息页等）保留不动、不删除；README 只引用精选 11 张。

## 4. 重写 README.md（中文、正式风格）
建议结构（只引用磁盘上真实存在的路径）：
- 标题 + 徽章 + 一句话简介
- 功能特性：双端登录（Sa-Token + Redis 会话）、OTP/图形验证码/OAuth（GitHub/Gitee/微信）、IAM/RBAC、系统管理（字典/配置/Banner/文件/弱口令/审计/代码生成）、消息（公告/反馈）、Redis Stream 操作审计、SnailJob 任务
- 技术栈表：JDK 21 / Spring Boot 4.1 / Maven 多模块 / PostgreSQL / Redis / Sa-Token / MyBatis-Plus / JustAuth / SnailJob / Redisson；前端 Vue3+Naive UI、React+Ant Design、uni-app
- 架构：`app/admin` 单应用同时提供 admin/portal 双端 API + 模块一览表
- 快速开始：环境要求 → 用 `scripts/db.sql` 初始化数据库（psql/Navicat 导入，默认 `jdbc:postgresql://127.0.0.1:5432/hei_boot`，dev 配置 postgres/123456）→ `mvn -pl app/admin -am spring-boot:run` 启动后端 → `pnpm dev` 启动前端 → 默认账号表
- 界面预览：11 张精选截图（表格排版，如现 README）
- 项目结构（按磁盘现状，无 helm/script/docker）
- 主要 API 前缀、配置说明（profile / 环境变量）、生产部署（Dockerfile + 必填环境变量表）、二次开发、代码贡献、Apache-2.0 协议
- 移除：姊妹项目表、已删除路径（`script/docker/*`、`deploy/helm`）、缺失图片引用（`admin-sys-notice.png`/`admin-sys-feedback.png`）、与实际配置不符的库账号（`hei/hei`→按 dev yml 的 postgres/123456）、SnailJob group 不一致（dev 为 `BOOT`）

## 5. 自检
- 校验 README 中所有相对路径（图片、文档链接）真实存在；`git status`/`git diff` 概览向用户汇报改动。