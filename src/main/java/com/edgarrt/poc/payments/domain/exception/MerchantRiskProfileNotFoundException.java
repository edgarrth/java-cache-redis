package com.edgarrt.poc.payments.domain.exception;

public class MerchantRiskProfileNotFoundException extends RuntimeException {
    public MerchantRiskProfileNotFoundException(String merchantId) {
        super("Merchant risk profile not found: " + merchantId);
    }
}
