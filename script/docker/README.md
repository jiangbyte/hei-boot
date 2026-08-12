# Docker scripts

`docker-compose.yml` starts local infra for `hei-boot`:

| Service   | Port(s)        | Purpose                          |
|-----------|----------------|----------------------------------|
| postgres  | 5432           | 业务库 + XXL-JOB Admin 表（`hei_boot`） |
| redis     | 6379           | Cache / Sa-Token sessions / Redis Stream 审计 |

## Start

```bash
docker compose -f script/docker/docker-compose.yml up -d
```

## TLS edge (optional)

```bash
# Put fullchain.pem + privkey.pem under script/docker/tls/
docker compose -f script/docker/docker-compose.yml \
  -f script/docker/docker-compose.prod.yml --profile tls up -d
```

SPA containers should set `HSTS_HEADER=max-age=31536000; includeSubDomains` when served over HTTPS.
Prefer HSTS at the TLS ingress/edge. Backend may set `HEI_SECURITY_HSTS_MAX_AGE=31536000` for API responses
when the API itself terminates TLS (unusual behind ingress).

Enable `HEI_SECURITY_TRUST_FORWARDED_HEADERS=true` only behind a trusted reverse proxy that rewrites
`X-Forwarded-*` / `X-Real-IP`.

PostgreSQL init mounts（仅空库首次初始化生效）：

- `app/xxl-job/src/main/resources/db/tables_xxl_job.sql` — XXL-JOB 表结构
- `script/sql/postgres/xxl_job_hei_seed.sql` — 执行器 `hei-boot-admin` + 3 个 Job Handler

若库已存在，可手动执行：

```bash
psql "postgresql://hei:hei@127.0.0.1:5432/hei_boot" \
  -f app/xxl-job/src/main/resources/db/tables_xxl_job.sql
psql "postgresql://hei:hei@127.0.0.1:5432/hei_boot" \
  -f script/sql/postgres/xxl_job_hei_seed.sql
```

## Local app start order

1. `docker compose -f script/docker/docker-compose.yml up -d`
2. `mvn -pl app/xxl-job -am spring-boot:run` — local-only Admin (`http://127.0.0.1:9004/xxl-job-admin`)
3. `mvn -pl app/admin -am spring-boot:run` — API + embedded XXL-JOB executor (default port **8000**)

## Production note

Do **not** deploy `app/xxl-job`. Point admin at an external cluster:

```bash
export XXL_JOB_ADMIN_ADDRESSES=http://xxl-job-admin.example.com/xxl-job-admin
```
