package com.edgarrt.poc.payments.domain.model;

import java.time.Instant;

public class Payment {
    private final PaymentId id;
    private final MerchantId merchantId;
    private final CustomerId customerId;
    private final String idempotencyKey;
    private final Money money;
    private final PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String authorizationCode;
    private String declineReason;
    private final Instant createdAt;
    private Instant updatedAt;

    private Payment(
            PaymentId id,
            MerchantId merchantId,
            CustomerId customerId,
            String idempotencyKey,
            Money money,
            PaymentMethod paymentMethod,
            PaymentStatus status,
            String authorizationCode,
            String declineReason,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.merchantId = merchantId;
        this.customerId = customerId;
        this.idempotencyKey = idempotencyKey;
        this.money = money;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.authorizationCode = authorizationCode;
        this.declineReason = declineReason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Payment receive(
            PaymentId id,
            MerchantId merchantId,
            CustomerId customerId,
            String idempotencyKey,
            Money money,
            PaymentMethod paymentMethod
    ) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IllegalArgumentException("idempotencyKey is required");
        }
        Instant now = Instant.now();
        return new Payment(
                id,
                merchantId,
                customerId,
                idempotencyKey,
                money,
                paymentMethod,
                PaymentStatus.RECEIVED,
                null,
                null,
                now,
                now
        );
    }

    public static Payment rehydrate(
            PaymentId id,
            MerchantId merchantId,
            CustomerId customerId,
            String idempotencyKey,
            Money money,
            PaymentMethod paymentMethod,
            PaymentStatus status,
            String authorizationCode,
            String declineReason,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Payment(id, merchantId, customerId, idempotencyKey, money, paymentMethod, status, authorizationCode, declineReason, createdAt, updatedAt);
    }

    public void markAuthorizing() {
        this.status = PaymentStatus.AUTHORIZING;
        this.updatedAt = Instant.now();
    }

    public void approve(String authorizationCode) {
        if (authorizationCode == null || authorizationCode.isBlank()) {
            throw new IllegalArgumentException("authorizationCode is required when a payment is approved");
        }
        this.status = PaymentStatus.APPROVED;
        this.authorizationCode = authorizationCode;
        this.declineReason = null;
        this.updatedAt = Instant.now();
    }

    public void decline(String reason) {
        this.status = PaymentStatus.DECLINED;
        this.authorizationCode = null;
        this.declineReason = reason == null || reason.isBlank() ? "DECLINED" : reason;
        this.updatedAt = Instant.now();
    }

    public PaymentId id() { return id; }
    public MerchantId merchantId() { return merchantId; }
    public CustomerId customerId() { return customerId; }
    public String idempotencyKey() { return idempotencyKey; }
    public Money money() { return money; }
    public PaymentMethod paymentMethod() { return paymentMethod; }
    public PaymentStatus status() { return status; }
    public String authorizationCode() { return authorizationCode; }
    public String declineReason() { return declineReason; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
