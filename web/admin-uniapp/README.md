# HEI Admin uni-app

![uni-app](https://img.shields.io/badge/uni--app-3-2A993B)
![Vue](https://img.shields.io/badge/Vue-3-4FC08D?logo=vuedotjs&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-5-3178C6?logo=typescript&logoColor=white)

管理端移动端（uni-app：H5 / 小程序等）。账号体系为 **ADMIN**，请求前缀 `/api/v1/admin/*`。

会话使用本地存储的 `Authorization` token（不以 Cookie 为主）。

> 需配合仓库根目录的后端 [`app/admin`](../../app/admin) 使用。默认演示账号见根 [README](../../README.md)。

## 功能

- 登录（账号 / 邮箱 / 手机号）
- Dashboard、工作台、用户中心
- 基于后端资源的动态能力入口（`/sys/resources/current`）
- 通用资源页能力（列表、详情、表单、筛选、分页）——具体业务页按资源配置扩展

## 技术栈

uni-app 3 · Vue 3 · Vite · TypeScript · Pinia · uview-pro · UnoCSS

## 快速开始

```bash
# 建议先启动后端：http://127.0.0.1:8000
pnpm install
pnpm dev:h5
```

默认环境见 [`.env`](.env)：

| 变量 | 说明 | 默认 |
| --- | --- | --- |
| `VITE_APP_TITLE` | 应用标题 | `HEI Admin` |
| `VITE_API_URL` | 后端基址（H5 / 小程序直连） | `http://127.0.0.1:8000` |
| `VITE_PORT` | H5 开发端口 | `5174` |

> 若本机同时跑门户（也默认 `5174`），请改本项目 `VITE_PORT`（例如 `5175`）避免冲突。

### 微信小程序

```bash
pnpm dev:mp-weixin
pnpm build:mp-weixin
```

其它平台命令见 [`package.json`](package.json)。发布前在 `src/manifest.json` 配置各端 appid，并在小程序后台配置合法请求域名。

## 常用命令

```bash
pnpm dev:h5         # H5 开发
pnpm build:h5       # H5 构建
pnpm type-check     # 类型检查
pnpm lint           # ESLint
pnpm format         # Prettier
```

## 生产构建

```bash
pnpm build:h5
```

[`.env.production`](.env.production) 中 `VITE_API_URL` 为空时，请求走同源 `/api/`，需由网关或 nginx 反代到后端。本目录**不提供** Dockerfile。

## 目录结构

```text
src/
  api/            接口封装
  components/     通用组件
  constants/      常量
  layouts/        布局
  pages/          页面（auth / dashboard / workbench / profile）
  static/         静态资源
  stores/         Pinia
  utils/          工具
  pages.json      页面路由
  manifest.json   应用配置
```

## 说明

- 菜单与权限以 `/api/v1/admin/sys/resources/current` 为准
- 小程序无法使用 Cookie 会话，请保持 Header `Authorization` 方案
- 真机调试时将 `VITE_API_URL` 改为可访问的后端地址（勿使用仅本机回环且设备不可达的地址）
