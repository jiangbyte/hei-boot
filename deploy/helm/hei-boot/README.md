# HEI Boot Helm reference chart

Minimal Deployment + Service + Ingress for `app/admin` (HTTP).

```bash
helm upgrade --install hei-boot deploy/helm/hei-boot \
  -n hei --create-namespace \
  --set image.repository=ghcr.io/example/hei-boot-admin \
  --set image.tag=0.1.0 \
  --set ingress.host=hei.example.com
```

Provide DB/Redis/RabbitMQ via `Secret` (`hei-boot-secrets`) with keys matching
`application-prod.yml` env vars (`DB_URL`, `REDIS_HOST`, …).
