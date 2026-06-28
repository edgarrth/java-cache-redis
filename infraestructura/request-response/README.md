# Request / Response de la PoC

Archivo ejecutable desde IntelliJ IDEA HTTP Client:

```bash
infraestructura/request-response/payment-processing.http
```

# POST /api/v1/payments

Request:

```json
{
  "idempotencyKey": "idem-demo-merchant-001-0001",
  "merchantId": "merchant-001",
  "customerId": "customer-9001",
  "amount": 125.50,
  "currency": "PEN",
  "paymentMethod": {
    "type": "CARD",
    "token": "tok_card_demo_visa"
  }
}
```

Response esperado:

```json
{
  "paymentId": "pay_...",
  "merchantId": "merchant-001",
  "customerId": "customer-9001",
  "amount": 125.50,
  "currency": "PEN",
  "status": "APPROVED",
  "authorizationCode": "AUTH-NIUBIZ_PRIMARY-...",
  "declineReason": null,
  "idempotencyState": "CREATED_AND_REGISTERED_IN_REDIS_IDEMPOTENCY",
  "redisUsage": "Redis Cluster: @Cacheable merchant-risk-profiles + payment:variables hash",
  "caffeineUsage": "Caffeine local: PSP token generated/cached per service instance",
  "processedAt": "2026-06-26T...Z"
}
```

# Replay por idempotencia

Si repites el mismo request con el mismo `idempotencyKey`, Redis Cluster devuelve el payment previamente procesado:

```json
{
  "idempotencyState": "REPLAYED_FROM_REDIS_IDEMPOTENCY"
}
```

# Redis @Cacheable

Endpoint:

```http
GET /api/v1/merchants/{merchantId}/risk-profile
```

La primera llamada carga desde PostgreSQL y guarda en Redis. La segunda llamada regresa desde Redis porque el método usa:

```java
@Cacheable(cacheNames = "merchant-risk-profiles", key = "#merchantId")
```

# Caffeine local

Endpoint:

```http
GET /api/v1/cache/observability
```

Retorna `estimatedSize` y `stats` del cache local de tokens PSP. Este cache no es distribuido: cada instancia JVM tiene su propio Caffeine.
