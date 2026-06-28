package com.edgarrt.poc.payments.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataPaymentJpaRepository extends JpaRepository<PaymentJpaEntity, String> {
    Optional<PaymentJpaEntity> findByMerchantIdAndIdempotencyKey(String merchantId, String idempotencyKey);
}
