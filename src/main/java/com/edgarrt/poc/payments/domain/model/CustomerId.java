package com.edgarrt.poc.payments.domain.model;

public record CustomerId(String value) {
    public CustomerId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("customerId is required");
        }
    }
}
