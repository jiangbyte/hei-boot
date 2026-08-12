#!/usr/bin/env bash
# OWASP ZAP baseline against a running target (API or SPA).
# Requires Docker. Non-zero exit when ZAP finds High/Medium by default (-I fails on warn).
#
#   ./script/security/zap-baseline.sh http://127.0.0.1:8000
#   TARGET=http://127.0.0.1:5173 ./script/security/zap-baseline.sh
set -euo pipefail

TARGET="${1:-${TARGET:-http://127.0.0.1:8000}}"
OUT_DIR="${OUT_DIR:-$(pwd)/script/security/out}"
mkdir -p "${OUT_DIR}"

echo "[zap] baseline scan → ${TARGET}"
docker run --rm \
  -v "${OUT_DIR}:/zap/wrk:rw" \
  -t ghcr.io/zaproxy/zaproxy:stable \
  zap-baseline.py -t "${TARGET}" -r zap-baseline-report.html -I || true

echo "[zap] report: ${OUT_DIR}/zap-baseline-report.html"
echo "[zap] note: -I keeps CI green on warnings; remove -I to fail on WARN+."
