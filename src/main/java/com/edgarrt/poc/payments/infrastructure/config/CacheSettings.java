package com.edgarrt.poc.payments.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "payment-processing.cache")
public class CacheSettings {
    private Duration merchantRiskTtl = Duration.ofMinutes(10);
    private Duration idempotencyTtl = Duration.ofHours(24);
    private Duration gatewayTokenTtl = Duration.ofMinutes(2);

    public Duration getMerchantRiskTtl() {
        return merchantRiskTtl;
    }

    public void setMerchantRiskTtl(Duration merchantRiskTtl) {
        this.merchantRiskTtl = merchantRiskTtl;
    }

    public Duration getIdempotencyTtl() {
        return idempotencyTtl;
    }

    public void setIdempotencyTtl(Duration idempotencyTtl) {
        this.idempotencyTtl = idempotencyTtl;
    }

    public Duration getGatewayTokenTtl() {
        return gatewayTokenTtl;
    }

    public void setGatewayTokenTtl(Duration gatewayTokenTtl) {
        this.gatewayTokenTtl = gatewayTokenTtl;
    }
}
