# Place TLS certs here for docker-compose.prod.yml edge-tls profile:
#   fullchain.pem
#   privkey.pem
#
# Generate a local self-signed pair (dev only):
#   openssl req -x509 -nodes -newkey rsa:2048 -days 365 \
#     -keyout privkey.pem -out fullchain.pem -subj "/CN=localhost"
