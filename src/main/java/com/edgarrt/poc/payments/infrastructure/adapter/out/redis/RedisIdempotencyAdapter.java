package com.edgarrt.poc.payments.infrastructure.adapter.out.redis;

import com.edgarrt.poc.payments.application.port.out.IdempotencyPort;
import com.edgarrt.poc.payments.domain.model.MerchantId;
import com.edgarrt.poc.payments.infrastructure.config.CacheSettings;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RedisIdempotencyAdapter implements IdempotencyPort {
    private final StringRedisTemplate redisTemplate;
    private final CacheSettings cacheSettings;

    public RedisIdempotencyAdapter(StringRedisTemplate redisTemplate, CacheSettings cacheSettings) {
        this.redisTemplate = redisTemplate;
        this.cacheSettings = cacheSettings;
    }

    @Override
    public boolean tryStart(MerchantId merchantId, String idempotencyKey, String paymentId) {
        String key = key(merchantId, idempotencyKey);
        Boolean stored = redisTemplate.opsForValue().setIfAbsent(key, paymentId, cacheSettings.getIdempotencyTtl());
        return Boolean.TRUE.equals(stored);
    }

    @Override
    public Optional<String> findPaymentId(MerchantId merchantId, String idempotencyKey) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key(merchantId, idempotencyKey)));
    }

    private String key(MerchantId merchantId, String idempotencyKey) {
        return "payment:idempotency:{" + merchantId.value() + "}:" + idempotencyKey;
    }
}
