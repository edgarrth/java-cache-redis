package com.edgarrt.poc.payments.domain.model;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(BigDecimal amount, Currency currency) {
    public Money {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        if (currency == null) {
            throw new IllegalArgumentException("currency is required");
        }
        amount = amount.setScale(2, java.math.RoundingMode.HALF_UP);
    }

    public boolean greaterThan(BigDecimal limit) {
        return amount.compareTo(limit) > 0;
    }
}
