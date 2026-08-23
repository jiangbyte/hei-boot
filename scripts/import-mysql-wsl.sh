#!/usr/bin/env bash
# 在 WSL Docker dev-mysql 中初始化 hei_boot 库（含表/列中文注释）。
set -euo pipefail

PASS="${MYSQL_ROOT_PASSWORD:-123456}"
DB="${MYSQL_DATABASE:-hei_boot}"
SQL="${HEI_MYSQL_SQL:-/mnt/e/projects/mine/hei/hei-boot/scripts/db.sql}"

if [[ ! -f "$SQL" ]]; then
  echo "schema not found: $SQL" >&2
  exit 1
fi

docker start dev-mysql dev-redis >/dev/null
for i in $(seq 1 40); do
  if docker exec dev-mysql mysqladmin ping -h localhost -uroot -p"$PASS" --silent; then
    break
  fi
  sleep 2
done

docker exec -i dev-mysql mysql -uroot -p"$PASS" -e \
  "DROP DATABASE IF EXISTS \`${DB}\`; CREATE DATABASE \`${DB}\` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
docker exec -i dev-mysql mysql -uroot -p"$PASS" --default-character-set=utf8mb4 "$DB" < "$SQL"

tables=$(docker exec -i dev-mysql mysql -uroot -p"$PASS" -N -e \
  "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='${DB}';")
commented=$(docker exec -i dev-mysql mysql -uroot -p"$PASS" -N -e \
  "SELECT COUNT(*) FROM information_schema.columns WHERE table_schema='${DB}' AND column_comment <> '';")
echo "IMPORT_OK ${DB}: tables=${tables}, commented_columns=${commented}"
