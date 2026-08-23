#!/usr/bin/env bash
# 将 scripts/db.sql 同步到 WSL dev-mysql。
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"

echo "==> import dev-mysql from scripts/db.sql"
bash scripts/import-mysql-wsl.sh

echo "MIGRATE_DEV_OK"
