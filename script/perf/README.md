# Performance & security baseline scripts

## k6 API smoke/load

```bash
# Install k6: https://k6.io/docs/get-started/installation/
k6 run script/perf/k6-api-baseline.js
k6 run -e BASE_URL=http://127.0.0.1:8000 -e TOKEN=<token> -e VUS=20 -e DURATION=1m script/perf/k6-api-baseline.js
```

## OWASP ZAP baseline

```bash
chmod +x script/security/zap-baseline.sh
./script/security/zap-baseline.sh http://127.0.0.1:8000
```

Reports land in `script/security/out/`.

## Admin Playwright E2E

```bash
cd web/admin
pnpm install
pnpm add -D @playwright/test@1.55.0
npx playwright install chromium
E2E_BASE_URL=http://127.0.0.1:5173 pnpm exec playwright test
```
