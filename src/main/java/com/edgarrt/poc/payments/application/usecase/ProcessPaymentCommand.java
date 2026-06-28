package com.edgarrt.poc.payments.application.usecase;

import java.math.BigDecimal;

public record ProcessPaymentCommand(
        String idempotencyKey,
        String merchantId,
        String customerId,
        BigDecimal amount,
        String currency,
        PaymentMethodCommand paymentMethod
) {
}
