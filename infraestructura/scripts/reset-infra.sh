#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."
docker compose down -v --remove-orphans
docker compose up -d postgres redis-node-1 redis-node-2 redis-node-3 redis-node-4 redis-node-5 redis-node-6 redis-cluster-init
