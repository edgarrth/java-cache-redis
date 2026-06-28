package com.edgarrt.poc.payments.infrastructure.adapter.in.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record ProcessPaymentHttpRequest(
        @NotBlank String idempotencyKey,
        @NotBlank String merchantId,
        @NotBlank String customerId,
        @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
        @NotBlank @Pattern(regexp = "[A-Z]{3}") String currency,
        @Valid @NotNull PaymentMethodHttpRequest paymentMethod
) {
}
