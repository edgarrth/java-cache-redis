package com.edgarrt.poc.payments.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public record MerchantRiskProfile(
        String merchantId,
        String merchantName,
        RiskLevel riskLevel,
        BigDecimal maxTicketAmount,
        Set<String> allowedCurrencies,
        Instant updatedAt
) {
    public boolean currencyAllowed(String currency) {
        return allowedCurrencies != null && allowedCurrencies.contains(currency);
    }
}
