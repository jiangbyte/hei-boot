# HEI Boot Helm reference chart

Minimal Deployment + Service + Ingress for `app/admin` (HTTP).

```bash
helm upgrade --install hei-boot deploy/helm/hei-boot \
  -n hei --create-namespace \
  --set image.repository=ghcr.io/example/hei-boot-admin \
  --set image.tag=0.1.0 \
  --set ingress.host=hei.example.com
```

Provide DB/Redis via `Secret` (`hei-boot-secrets`) with keys matching
`application-prod.yml` env vars (`DB_URL`, `REDIS_HOST`, `HEI_CONFIG_CRYPTO_KEY`, …).

Audit uses Redis Stream (not RabbitMQ). Enable `hei.security.trust-forwarded-headers`
only behind a trusted ingress/proxy; prefer HSTS at the ingress (this chart sets an HSTS
annotation when TLS is terminated at the edge).

**Network / exposure:** do not publish `/api/*/internal/**` or `/actuator/**` to the public
Internet. Prefer NetworkPolicy (or Ingress path allow-lists) so those endpoints stay
cluster-internal; the sample Ingress that prefixes `/actuator` is for lab use only.
Tighten before production.
