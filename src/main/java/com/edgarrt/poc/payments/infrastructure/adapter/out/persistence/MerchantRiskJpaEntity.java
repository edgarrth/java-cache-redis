package com.edgarrt.poc.payments.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "merchant_risk_profiles")
public class MerchantRiskJpaEntity {
    @Id
    @Column(name = "merchant_id", nullable = false, length = 80)
    private String merchantId;

    @Column(name = "merchant_name", nullable = false, length = 160)
    private String merchantName;

    @Column(name = "risk_level", nullable = false, length = 20)
    private String riskLevel;

    @Column(name = "max_ticket_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal maxTicketAmount;

    @Column(name = "allowed_currencies", nullable = false, length = 80)
    private String allowedCurrencies;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
    public String getMerchantName() { return merchantName; }
    public void setMerchantName(String merchantName) { this.merchantName = merchantName; }
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public BigDecimal getMaxTicketAmount() { return maxTicketAmount; }
    public void setMaxTicketAmount(BigDecimal maxTicketAmount) { this.maxTicketAmount = maxTicketAmount; }
    public String getAllowedCurrencies() { return allowedCurrencies; }
    public void setAllowedCurrencies(String allowedCurrencies) { this.allowedCurrencies = allowedCurrencies; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
