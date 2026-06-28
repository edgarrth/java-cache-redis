#!/usr/bin/env bash
set -euo pipefail

LINE="127.0.0.1 redis-node-1 redis-node-2 redis-node-3 redis-node-4 redis-node-5 redis-node-6"
if grep -q "redis-node-1" /etc/hosts; then
  echo "Redis host aliases already exist in /etc/hosts"
  exit 0
fi

echo "Adding Redis Cluster host aliases to /etc/hosts"
echo "$LINE" | sudo tee -a /etc/hosts
