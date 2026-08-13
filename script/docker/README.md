# Docker scripts

## Local infra notes

Prefer the **already running** local containers (`dev-postgres`, `dev-redis`, …). Do **not** recreate or reconfigure them for SnailJob.

The repo `docker-compose.yml` is an optional documented stack for greenfield machines only:

| Service   | Port(s) | Purpose                                      |
|-----------|---------|----------------------------------------------|
| postgres  | 5432    | Business DB `hei_boot` only                  |
| redis     | 6379    | Cache / Sa-Token sessions / Redis Stream audit |

SnailJob uses a **separate** DB `snail_job` on the same Postgres host (role `admin` / `123456`).

## Optional greenfield compose

```bash
docker compose -f script/docker/docker-compose.yml up -d
```

## SnailJob (local)

1. One-time on existing Postgres (superuser): create role `admin` / `123456` and DB `snail_job`.
2. Flyway migrate (one-shot container; does not touch Postgres container config):

```bash
bash script/docker/snailjob-flyway.sh
```

3. Start SnailJob Server (does **not** manage postgres/redis):

```bash
docker compose -f script/docker/docker-compose.snailjob.yml up -d
```

Console: `http://127.0.0.1:9189/snail-job` — **admin / 123456**  
RPC: `17888`

> Upstream `opensnail/snail-job:2.0.0` packs Java 21 bytecode with a JDK 17 runtime.
> Compose still takes `app.jar` from that image and runs it with Temurin 21 (SWR).

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

## Local app start order

1. Existing Postgres + Redis (or optional `docker-compose.yml`)
2. `bash script/docker/snailjob-flyway.sh` (once)
3. `docker compose -f script/docker/docker-compose.snailjob.yml up -d`
4. `mvn -pl app/admin -am spring-boot:run` — API + SnailJob client (port **8000**)

## Production note

Do **not** ship SnailJob Server inside `app/admin`. Point the client at an external Server:

```bash
export SNAIL_JOB_ENABLED=true
export SNAIL_JOB_SERVER_HOST=snail-job.example.com
export SNAIL_JOB_SERVER_PORT=17888
export SNAIL_JOB_NAMESPACE=764d604ec6fc45f68cd92514c40e9e1a
export SNAIL_JOB_GROUP=hei_boot_admin
export SNAIL_JOB_TOKEN=your_token
```
