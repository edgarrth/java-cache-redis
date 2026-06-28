package com.edgarrt.poc.payments.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PaymentMethodHttpRequest(
        @NotBlank @Pattern(regexp = "CARD|WALLET") String type,
        @NotBlank String token
) {
}
