# HEI Admin

![Vue](https://img.shields.io/badge/Vue-3-4FC08D?logo=vuedotjs&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-Supported-646CFF?logo=vite&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-5-3178C6?logo=typescript&logoColor=white)
![Naive UI](https://img.shields.io/badge/UI-Naive%20UI-18A058)

管理端 Web（Vue 3）。账号体系为 **ADMIN**，请求前缀 `/api/v1/admin/*`。

> 需配合仓库根目录的后端 [`app/admin`](../../app/admin) 使用。默认演示账号见根 [README](../../README.md)。

## 功能

- 认证：账号 / 邮箱 / 手机号登录，Cookie 会话，忘记 / 重置密码，可配置三方登录
- 用户中心：头像、资料、改密、联系方式、OAuth 绑定
- 动态路由与菜单（`/sys/resources/current`），按资源授权收束
- IAM：账号、角色、部门、用户组、岗位、资源 / 资源模块、客户端资源
- 系统：字典、配置、Banner、文件、任务、弱口令、操作审计、登录日志、在线会话、代码生成、OAuth 配置
- 消息：公告、通知、意见反馈
- 运营工作台（Dashboard）

## 技术栈

Vue 3 · Vite · TypeScript · Naive UI / Pro Naive UI · Pinia · Vue Router · axios · UnoCSS · AntV G2

## 快速开始

```bash
# 建议先启动后端：http://127.0.0.1:8000
pnpm install
pnpm dev
```

开发地址默认：http://127.0.0.1:5173

### 环境变量

参考 [`.env.example`](.env.example)：

| 变量 | 说明 | 默认 |
| --- | --- | --- |
| `VITE_PORT` | 开发端口 | `5173` |
| `VITE_HOME_PATH` | 登录后首页 | `/dashboard` |
| `VITE_ROUTE_LOAD_MODE` | `dynamic` 拉后端资源；`static` 用本地静态路由 | `dynamic` |
| `VITE_API_URL` | API 基址；**留空**则走同源 `/api` | 空 |
| `VITE_API_PROXY_TARGET` | Vite 代理目标 | `http://127.0.0.1:8000` |

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

本目录提供 `Dockerfile` + `nginx/`（监听 **81**）。仓库根目录当前无 docker-compose。

```bash
pnpm build   # 可选；镜像内也会执行 vite build

docker build -t hei-boot-admin .
docker run -d \
  -e BACKEND_URL="http://host.docker.internal:8000" \
  -p 8081:81 \
  hei-boot-admin
```

常用环境变量：`BACKEND_URL`、`CLIENT_MAX_BODY_SIZE`（默认 `10m`）。

## 目录结构

```text
src/
  api/          接口封装
  components/   通用组件
  constants/    常量
  hooks/        组合式函数
  layouts/      布局
  plugins/      插件
  router/       路由与守卫
  stores/       Pinia
  typing/       类型
  utils/        工具
  views/        页面（auth / iam / sys / dashboard / …）
nginx/          生产 nginx 模板
```

## 说明

- `VITE_ROUTE_LOAD_MODE=dynamic` 时，侧栏与可访问路由来自后端资源树；按钮权限对应 permission key
- 开发期 Cookie 会话依赖 Vite 同源代理；勿轻易把 `VITE_API_URL` 指到跨域后端，除非已配好 CORS 与 Cookie `SameSite`
