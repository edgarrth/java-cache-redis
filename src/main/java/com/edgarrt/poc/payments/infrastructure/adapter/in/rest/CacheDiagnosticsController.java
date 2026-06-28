package com.edgarrt.poc.payments.infrastructure.adapter.in.rest;

import com.edgarrt.poc.payments.application.usecase.GatewayAccessToken;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/cache")
public class CacheDiagnosticsController {
    private final Cache<String, GatewayAccessToken> gatewayTokenCache;
    private final RedisConnectionFactory redisConnectionFactory;

    public CacheDiagnosticsController(Cache<String, GatewayAccessToken> gatewayTokenCache, RedisConnectionFactory redisConnectionFactory) {
        this.gatewayTokenCache = gatewayTokenCache;
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @GetMapping("/observability")
    public Map<String, Object> observe() {
        String redisMode = "unknown";
        try {
            redisConnectionFactory.getClusterConnection();
            redisMode = "redis-cluster";
        } catch (Exception ex) {
            redisMode = "not-a-cluster-or-not-available: " + ex.getClass().getSimpleName();
        }
        return Map.of(
                "redisDistributedCache", Map.of(
                        "mode", redisMode,
                        "usedFor", "@Cacheable merchant-risk-profiles, payment:variables hash, distributed idempotency"
                ),
                "caffeineLocalCache", Map.of(
                        "estimatedSize", gatewayTokenCache.estimatedSize(),
                        "stats", gatewayTokenCache.stats().toString(),
                        "usedFor", "short-lived PSP OAuth-like access tokens per JVM instance"
                )
        );
    }
}
