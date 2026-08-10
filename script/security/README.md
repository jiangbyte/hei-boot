# Security scripts

| 文件 | 说明 |
|------|------|
| `zap-baseline.sh` | OWASP ZAP baseline 扫描入口；由 `.github/workflows/security-perf-nightly.yml` 调用 |

输出目录默认写入工作区临时路径（见脚本内变量）。完整压测说明见 [../perf/README.md](../perf/README.md)。
