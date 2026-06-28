package com.edgarrt.poc.payments.domain.model;

public record MerchantId(String value) {
    public MerchantId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("merchantId is required");
        }
    }
}
