package com.edgarrt.poc.payments.application.usecase;

public record GatewayAuthorization(boolean approved, String authorizationCode, String reason) {
    public static GatewayAuthorization approved(String authorizationCode) {
        return new GatewayAuthorization(true, authorizationCode, null);
    }

    public static GatewayAuthorization declined(String reason) {
        return new GatewayAuthorization(false, null, reason);
    }
}
