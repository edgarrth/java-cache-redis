package com.edgarrt.poc.payments.application.port.out;

import com.edgarrt.poc.payments.domain.model.MerchantId;

import java.util.Optional;

public interface IdempotencyPort {
    boolean tryStart(MerchantId merchantId, String idempotencyKey, String paymentId);
    Optional<String> findPaymentId(MerchantId merchantId, String idempotencyKey);
}
