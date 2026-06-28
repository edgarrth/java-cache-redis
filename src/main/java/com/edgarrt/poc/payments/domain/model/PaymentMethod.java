package com.edgarrt.poc.payments.domain.model;

public record PaymentMethod(PaymentMethodType type, String token) {
    public PaymentMethod {
        if (type == null) {
            throw new IllegalArgumentException("payment method type is required");
        }
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("payment method token is required");
        }
    }
}
