package com.edgarrt.poc.payments.application.usecase;

import com.edgarrt.poc.payments.domain.model.Payment;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentView(
        String paymentId,
        String merchantId,
        String customerId,
        BigDecimal amount,
        String currency,
        String paymentMethodType,
        String status,
        String authorizationCode,
        String declineReason,
        Instant createdAt,
        Instant updatedAt
) {
    public static PaymentView from(Payment payment) {
        return new PaymentView(
                payment.id().value(),
                payment.merchantId().value(),
                payment.customerId().value(),
                payment.money().amount(),
                payment.money().currency().getCurrencyCode(),
                payment.paymentMethod().type().name(),
                payment.status().name(),
                payment.authorizationCode(),
                payment.declineReason(),
                payment.createdAt(),
                payment.updatedAt()
        );
    }
}
