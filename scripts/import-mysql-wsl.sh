#!/usr/bin/env bash
set -euo pipefail
PASS=123456
DB=hei_boot
SQL=/mnt/e/projects/mine/hei/hei-boot/scripts/db.mysql.sql

docker start dev-mysql dev-redis >/dev/null
for i in $(seq 1 40); do
  if docker exec dev-mysql mysqladmin ping -h localhost -uroot -p"$PASS" --silent; then
    break
  fi
  sleep 2
done

docker exec -i dev-mysql mysql -uroot -p"$PASS" -e "DROP DATABASE IF EXISTS ${DB}; CREATE DATABASE ${DB} CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
docker exec -i dev-mysql mysql -uroot -p"$PASS" --default-character-set=utf8mb4 "$DB" < "$SQL"
echo IMPORT_OK
docker exec -i dev-mysql mysql -uroot -p"$PASS" -N -e "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='${DB}';"
