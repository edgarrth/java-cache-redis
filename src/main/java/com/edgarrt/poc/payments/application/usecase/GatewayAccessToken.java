package com.edgarrt.poc.payments.application.usecase;

import java.time.Instant;

public record GatewayAccessToken(String token, Instant expiresAt, String source) {
    public GatewayAccessToken withSource(String newSource) {
        return new GatewayAccessToken(token, expiresAt, newSource);
    }
}
