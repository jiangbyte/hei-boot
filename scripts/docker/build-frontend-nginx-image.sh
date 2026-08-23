#!/usr/bin/env bash
# 本地已 pnpm vite build 后，打 nginx 前端镜像（含完整 CSP 等 ENV）
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/../../.." && pwd)"
REGISTRY="${REGISTRY:-registry.cn-beijing.aliyuncs.com/czbyte}"

build_one() {
  local name="$1"
  local port="$2"
  local csp="$3"
  local src="$ROOT/$name"
  local stage="/tmp/hei-docker-stage-$name"

  rm -rf "$stage"
  mkdir -p "$stage/html" "$stage/templates"
  cp -r "$src/dist/"* "$stage/html/"
  cp "$src/nginx/default.conf.template" "$stage/templates/"

  cat > "$stage/Dockerfile" <<EOF
FROM swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/nginx:1.27-alpine
COPY html /usr/share/nginx/html
COPY templates/default.conf.template /etc/nginx/templates/default.conf.template
ENV BACKEND_HOST=127.0.0.1 \\
    BACKEND_PORT=8000 \\
    CLIENT_MAX_BODY_SIZE=10m \\
    CONTENT_SECURITY_POLICY="$csp" \\
    HSTS_HEADER=
EXPOSE $port
EOF

  docker build -t "$REGISTRY/$name:latest" "$stage"
}

ADMIN_CSP='default-src '\''self'\''; script-src '\''self'\''; style-src '\''self'\'' '\''unsafe-inline'\''; img-src '\''self'\'' data: blob:; font-src '\''self'\'' data:; connect-src '\''self'\''; frame-ancestors '\''none'\''; base-uri '\''self'\''; form-action '\''self'\'''
PORTAL_CSP='default-src '\''self'\''; script-src '\''self'\'' '\''unsafe-inline'\''; style-src '\''self'\'' '\''unsafe-inline'\''; img-src '\''self'\'' data: blob:; font-src '\''self'\'' data:; connect-src '\''self'\''; frame-ancestors '\''none'\''; base-uri '\''self'\''; form-action '\''self'\'''

build_one hei-admin 81 "$ADMIN_CSP"
build_one hei-portal 80 "$PORTAL_CSP"

docker push "$REGISTRY/hei-admin:latest"
docker push "$REGISTRY/hei-portal:latest"

echo "Pushed $REGISTRY/hei-admin:latest and $REGISTRY/hei-portal:latest"
