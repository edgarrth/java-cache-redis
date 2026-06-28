package com.edgarrt.poc.payments.infrastructure.adapter.out.persistence;

import com.edgarrt.poc.payments.domain.model.*;

import java.util.Currency;

class PaymentPersistenceMapper {
    private PaymentPersistenceMapper() {
    }

    static PaymentJpaEntity toEntity(Payment payment) {
        PaymentJpaEntity entity = new PaymentJpaEntity();
        entity.setPaymentId(payment.id().value());
        entity.setMerchantId(payment.merchantId().value());
        entity.setCustomerId(payment.customerId().value());
        entity.setIdempotencyKey(payment.idempotencyKey());
        entity.setAmount(payment.money().amount());
        entity.setCurrency(payment.money().currency().getCurrencyCode());
        entity.setPaymentMethodType(payment.paymentMethod().type().name());
        entity.setPaymentMethodToken(payment.paymentMethod().token());
        entity.setStatus(payment.status().name());
        entity.setAuthorizationCode(payment.authorizationCode());
        entity.setDeclineReason(payment.declineReason());
        entity.setCreatedAt(payment.createdAt());
        entity.setUpdatedAt(payment.updatedAt());
        return entity;
    }

    static Payment toDomain(PaymentJpaEntity entity) {
        return Payment.rehydrate(
                new PaymentId(entity.getPaymentId()),
                new MerchantId(entity.getMerchantId()),
                new CustomerId(entity.getCustomerId()),
                entity.getIdempotencyKey(),
                new Money(entity.getAmount(), Currency.getInstance(entity.getCurrency())),
                new PaymentMethod(PaymentMethodType.valueOf(entity.getPaymentMethodType()), entity.getPaymentMethodToken()),
                PaymentStatus.valueOf(entity.getStatus()),
                entity.getAuthorizationCode(),
                entity.getDeclineReason(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
