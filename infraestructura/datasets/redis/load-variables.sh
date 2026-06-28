#!/usr/bin/env bash
set -euo pipefail

REDIS_HOST="${REDIS_HOST:-redis-node-1}"
REDIS_PORT="${REDIS_PORT:-7000}"

redis_hset() {
  local key="$1"
  shift
  if command -v redis-cli >/dev/null 2>&1; then
    redis-cli -c -h "$REDIS_HOST" -p "$REDIS_PORT" HSET "$key" "$@"
  else
    docker exec redis-node-1 redis-cli -c -p 7000 HSET "$key" "$@"
  fi
}

redis_hset 'payment:variables:{merchant-001}'   pspRoutingKey 'niubiz-primary'   threeDsRequired 'true'   maxAmountWithoutReview '500.00'   tokenAudience 'demo-card-gateway'

redis_hset 'payment:variables:{merchant-002}'   pspRoutingKey 'izipay-secondary'   threeDsRequired 'true'   maxAmountWithoutReview '300.00'   tokenAudience 'demo-card-gateway'

redis_hset 'payment:variables:{merchant-003}'   pspRoutingKey 'manual-review-route'   threeDsRequired 'true'   maxAmountWithoutReview '100.00'   tokenAudience 'demo-card-gateway'

echo "Redis merchant variables loaded"
