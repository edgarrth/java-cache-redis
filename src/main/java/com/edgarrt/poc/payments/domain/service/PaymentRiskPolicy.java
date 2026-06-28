package com.edgarrt.poc.payments.domain.service;

import com.edgarrt.poc.payments.domain.model.MerchantRiskProfile;
import com.edgarrt.poc.payments.domain.model.Payment;
import com.edgarrt.poc.payments.domain.model.RiskLevel;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class PaymentRiskPolicy {
    public Optional<String> evaluate(Payment payment, MerchantRiskProfile riskProfile, Map<String, String> merchantVariables) {
        String currency = payment.money().currency().getCurrencyCode();
        if (!riskProfile.currencyAllowed(currency)) {
            return Optional.of("Currency " + currency + " is not allowed for merchant " + payment.merchantId().value());
        }
        if (payment.money().greaterThan(riskProfile.maxTicketAmount())) {
            return Optional.of("Amount exceeds merchant max ticket amount " + riskProfile.maxTicketAmount());
        }
        BigDecimal variableLimit = new BigDecimal(merchantVariables.getOrDefault("maxAmountWithoutReview", "999999.00"));
        if (payment.money().greaterThan(variableLimit)) {
            return Optional.of("Amount exceeds Redis variable maxAmountWithoutReview " + variableLimit);
        }
        if (riskProfile.riskLevel() == RiskLevel.HIGH && payment.money().amount().compareTo(BigDecimal.valueOf(100)) > 0) {
            return Optional.of("High risk merchant requires manual review above 100.00");
        }
        return Optional.empty();
    }
}
