import http from 'k6/http'
import { check, sleep } from 'k6'

/**
 * Baseline HTTP load against hei-boot admin API.
 *
 *   k6 run -e BASE_URL=http://127.0.0.1:8000 -e TOKEN=<sa-token> script/perf/k6-api-baseline.js
 *
 * Without TOKEN, only public/health probes run.
 */
export const options = {
  scenarios: {
    smoke: {
      executor: 'constant-vus',
      vus: Number(__ENV.VUS || 5),
      duration: __ENV.DURATION || '30s',
    },
  },
  thresholds: {
    http_req_failed: ['rate<0.05'],
    http_req_duration: ['p(95)<1500'],
  },
}

const BASE = (__ENV.BASE_URL || 'http://127.0.0.1:8000').replace(/\/$/, '')
const TOKEN = __ENV.TOKEN || ''

export default function () {
  const health = http.get(`${BASE}/actuator/health`)
  check(health, {
    'health reachable or gated': (r) => [200, 401, 403, 404].includes(r.status),
  })

  if (TOKEN) {
    const headers = {
      Authorization: TOKEN,
      satoken: TOKEN,
      'Content-Type': 'application/json',
    }
    const me = http.get(`${BASE}/api/v1/admin/auth/me`, { headers })
    check(me, {
      'auth me ok-ish': (r) => [200, 401, 403].includes(r.status),
    })
    const dash = http.get(`${BASE}/api/v1/admin/dashboard/overview`, { headers })
    check(dash, {
      'dashboard overview ok-ish': (r) => [200, 401, 403].includes(r.status),
    })
  }

  sleep(1)
}
