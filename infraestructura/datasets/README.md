# Datasets de la PoC

Esta carpeta contiene scripts para precargar datos de prueba en PostgreSQL y Redis Cluster.

# PostgreSQL

La aplicación ejecuta automáticamente los scripts Flyway:

- `src/main/resources/db/migration/V1__create_schema.sql`
- `src/main/resources/db/migration/V2__seed_merchants.sql`

También se deja una copia manual en:

```bash
infraestructura/datasets/postgres/01_seed_merchants.sql
```

Para recargar manualmente merchants:

```bash
docker exec -i payment-postgres psql -U payment_user -d paymentdb < infraestructura/datasets/postgres/01_seed_merchants.sql
```

# Redis Cluster

Redis se usa para:

- Variables distribuidas por merchant: `payment:variables:{merchantId}`.
- Idempotencia distribuida: `payment:idempotency:{merchantId}:{idempotencyKey}`.
- Cache distribuido de `@Cacheable`: `merchant-risk-profiles`.

Cargar variables demo:

```bash
bash infraestructura/datasets/redis/load-variables.sh
```

Ver variables:

```bash
redis-cli -c -h redis-node-1 -p 7000 HGETALL 'payment:variables:{merchant-001}'
```

# Datos precargados

- `merchant-001`: bajo riesgo, límite 1500 PEN/USD.
- `merchant-002`: riesgo medio, límite 800 PEN/USD.
- `merchant-003`: alto riesgo, límite 250 PEN.

# Validación rápida

1. Ejecuta infraestructura.
2. Ejecuta el microservicio.
3. Ejecuta dos veces el mismo request con igual `idempotencyKey`.
4. La segunda respuesta debe indicar `REPLAYED_FROM_REDIS_IDEMPOTENCY`.
5. Consulta dos veces `/api/v1/merchants/merchant-001/risk-profile`; en logs solo la primera debería cargar desde PostgreSQL porque el resultado queda en Redis con `@Cacheable`.
