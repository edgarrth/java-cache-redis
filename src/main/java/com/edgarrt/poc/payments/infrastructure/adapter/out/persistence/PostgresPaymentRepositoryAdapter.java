package com.edgarrt.poc.payments.infrastructure.adapter.out.persistence;

import com.edgarrt.poc.payments.application.port.out.PaymentRepository;
import com.edgarrt.poc.payments.domain.model.MerchantId;
import com.edgarrt.poc.payments.domain.model.Payment;
import com.edgarrt.poc.payments.domain.model.PaymentId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PostgresPaymentRepositoryAdapter implements PaymentRepository {
    private final SpringDataPaymentJpaRepository repository;

    public PostgresPaymentRepositoryAdapter(SpringDataPaymentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Payment save(Payment payment) {
        return PaymentPersistenceMapper.toDomain(repository.save(PaymentPersistenceMapper.toEntity(payment)));
    }

    @Override
    public Optional<Payment> findById(PaymentId paymentId) {
        return repository.findById(paymentId.value()).map(PaymentPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Payment> findByMerchantAndIdempotencyKey(MerchantId merchantId, String idempotencyKey) {
        return repository.findByMerchantIdAndIdempotencyKey(merchantId.value(), idempotencyKey)
                .map(PaymentPersistenceMapper::toDomain);
    }
}
