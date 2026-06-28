package com.edgarrt.poc.payments.domain.model;

import java.util.Objects;
import java.util.UUID;

public record PaymentId(String value) {
    public PaymentId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("paymentId is required");
        }
    }

    public static PaymentId newId() {
        return new PaymentId("pay_" + UUID.randomUUID());
    }

    @Override
    public String toString() {
        return Objects.toString(value);
    }
}
