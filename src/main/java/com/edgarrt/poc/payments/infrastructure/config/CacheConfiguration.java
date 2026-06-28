package com.edgarrt.poc.payments.infrastructure.config;

import com.edgarrt.poc.payments.application.usecase.GatewayAccessToken;
import com.edgarrt.poc.payments.domain.model.MerchantRiskProfile;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.Map;

@Configuration
public class CacheConfiguration {

    @Bean
    CacheManager cacheManager(
            RedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper,
            CacheSettings cacheSettings
    ) {
        JacksonJsonRedisSerializer<MerchantRiskProfile> merchantRiskSerializer =
                new JacksonJsonRedisSerializer<>(objectMapper, MerchantRiskProfile.class);

        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .disableCachingNullValues();

        RedisCacheConfiguration merchantRiskConfig = defaultConfig
                .entryTtl(cacheSettings.getMerchantRiskTtl())
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(merchantRiskSerializer)
                );

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(
                        Map.of("merchant-risk-profiles", merchantRiskConfig)
                )
                .transactionAware()
                .build();
    }

    @Bean
    Cache<String, GatewayAccessToken> gatewayTokenCache(CacheSettings cacheSettings) {
        return Caffeine.newBuilder()
                .expireAfterWrite(cacheSettings.getGatewayTokenTtl())
                .maximumSize(1_000)
                .recordStats()
                .build();
    }
}