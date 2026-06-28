package com.edgarrt.poc.payments.application.usecase;

public record PaymentMethodCommand(String type, String token) {
}
