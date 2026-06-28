package com.edgarrt.poc.payments.application.port.out;

import com.edgarrt.poc.payments.domain.model.MerchantId;
import com.edgarrt.poc.payments.domain.model.Payment;
import com.edgarrt.poc.payments.domain.model.PaymentId;

import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(PaymentId paymentId);
    Optional<Payment> findByMerchantAndIdempotencyKey(MerchantId merchantId, String idempotencyKey);
}
