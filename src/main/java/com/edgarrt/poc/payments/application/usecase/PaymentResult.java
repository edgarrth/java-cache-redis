package com.edgarrt.poc.payments.application.usecase;

import com.edgarrt.poc.payments.domain.model.Payment;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResult(
        String paymentId,
        String merchantId,
        String customerId,
        BigDecimal amount,
        String currency,
        String status,
        String authorizationCode,
        String declineReason,
        String idempotencyState,
        String redisUsage,
        String caffeineUsage,
        Instant processedAt
) {
    public static PaymentResult from(Payment payment, String idempotencyState, String redisUsage, String caffeineUsage) {
        return new PaymentResult(
                payment.id().value(),
                payment.merchantId().value(),
                payment.customerId().value(),
                payment.money().amount(),
                payment.money().currency().getCurrencyCode(),
                payment.status().name(),
                payment.authorizationCode(),
                payment.declineReason(),
                idempotencyState,
                redisUsage,
                caffeineUsage,
                payment.updatedAt()
        );
    }
}
