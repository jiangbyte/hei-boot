# HEI Portal

![React](https://img.shields.io/badge/React-19-61DAFB?logo=react&logoColor=black)
![Vite](https://img.shields.io/badge/Vite-Supported-646CFF?logo=vite&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-5-3178C6?logo=typescript&logoColor=white)
![Ant Design](https://img.shields.io/badge/UI-Ant%20Design-0170FE?logo=antdesign&logoColor=white)

门户 Web（React）。账号体系为 **PORTAL**，请求前缀 `/api/v1/portal/*`。

> 需配合仓库根目录的后端 [`app/admin`](../../app/admin) 使用。默认演示账号见根 [README](../../README.md)。

## 功能

- 全页认证：登录 / 注册 / 找回与重置密码（`/auth/*`），可配置三方登录入口
- Cookie 会话（可关，仅 Header）
- 首页、公告、意见反馈
- 个人主页、账号中心（资料、密码、邮箱、手机、消息、OAuth 绑定）

## 技术栈

React 19 · Vite · TypeScript · Ant Design 6 · React Router · Zustand · axios · UnoCSS

主题 token：[`src/theme/tokens.ts`](src/theme/tokens.ts)。

## 快速开始

```bash
# 建议先启动后端：http://127.0.0.1:8000
pnpm install
pnpm dev
```

开发地址默认：http://127.0.0.1:5174

### 环境变量

参考 [`.env.example`](.env.example)：

| 变量 | 说明 | 默认 |
| --- | --- | --- |
| `VITE_APP_TITLE` | 站点标题 | `HEI` |
| `VITE_PORT` | 开发端口 | `5174` |
| `VITE_HOME_PATH` | 登录后首页 | `/` |
| `VITE_API_URL` | API 基址；**留空**则走同源 `/api` | 空 |
| `VITE_API_PROXY_TARGET` | Vite 代理目标 | `http://127.0.0.1:8000` |
| `VITE_COPYRIGHT_INFO` 等 | 页脚版权 / 备案 / 联系方式（可选） | — |

生产构建使用 [`.env.production`](.env.production)：`VITE_API_URL` 置空，由 nginx 反代 `/api`。

## 常用命令

```bash
pnpm dev          # 本地开发
pnpm build        # 类型检查 + 构建
pnpm preview      # 预览构建产物
pnpm lint         # ESLint
pnpm format       # Prettier
```

## Docker

本目录提供 `Dockerfile` + `nginx/`（监听 **80**）。仓库根目录当前无 docker-compose。

```bash
pnpm build   # 可选；镜像内也会执行 vite build

docker build -t hei-boot-portal .
docker run -d \
  -e BACKEND_URL="http://host.docker.internal:8000" \
  -p 8082:80 \
  hei-boot-portal
```

常用环境变量：`BACKEND_URL`、`CLIENT_MAX_BODY_SIZE`（默认 `10m`）。

## 目录结构

```text
src/
  api/          接口封装
  assets/       静态资源
  components/   通用组件（含登录 / 注册弹窗等）
  constants/    常量
  hooks/        Hooks
  layouts/      布局
  pages/        页面（auth / home / announcements / feedback / profile / usercenter）
  router/       路由与守卫
  stores/       Zustand
  styles/       全局样式
  theme/        主题 token
  typing/       类型
  utils/        工具
nginx/          生产 nginx 模板
```

## 说明

- 开发期 Cookie 会话依赖 Vite 同源代理；跨域直连后端需自行处理 CORS 与 Cookie 策略
- 与管理端共用同一后端进程，端口与 Cookie Path 按端隔离（`/api/v1/portal`）
