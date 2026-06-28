package com.edgarrt.poc.payments.infrastructure.adapter.out.psp;

import com.edgarrt.poc.payments.application.port.out.TokenProviderPort;
import com.edgarrt.poc.payments.application.usecase.GatewayAccessToken;
import com.edgarrt.poc.payments.domain.model.MerchantId;
import com.edgarrt.poc.payments.infrastructure.config.CacheSettings;
import com.edgarrt.poc.payments.infrastructure.config.PspSettings;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class CaffeineGatewayTokenProvider implements TokenProviderPort {
    private final Cache<String, GatewayAccessToken> tokenCache;
    private final CacheSettings cacheSettings;
    private final PspSettings pspSettings;

    public CaffeineGatewayTokenProvider(Cache<String, GatewayAccessToken> tokenCache, CacheSettings cacheSettings, PspSettings pspSettings) {

        this.tokenCache = tokenCache;
        this.cacheSettings = cacheSettings;
        this.pspSettings = pspSettings;
    }

    @Override
    public GatewayAccessToken getToken(MerchantId merchantId) {
        String key = pspSettings.getClientId() + ":" + pspSettings.getTokenAudience() + ":" + merchantId.value();
        GatewayAccessToken cached = tokenCache.getIfPresent(key);
        if (cached != null) {
            return cached.withSource("CAFFEINE_LOCAL_HIT");
        }
        GatewayAccessToken generated = new GatewayAccessToken(
                "psp_token_" + UUID.randomUUID(),
                Instant.now().plus(cacheSettings.getGatewayTokenTtl()),
                "PSP_SIMULATED_OAUTH_TOKEN_GENERATED_AND_STORED_IN_CAFFEINE"
        );
        tokenCache.put(key, generated);
        return generated;
    }
}
