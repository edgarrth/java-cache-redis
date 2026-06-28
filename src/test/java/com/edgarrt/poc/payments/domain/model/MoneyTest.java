package com.edgarrt.poc.payments.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void shouldRejectNegativeAmounts() {
        assertThrows(IllegalArgumentException.class, () -> new Money(BigDecimal.valueOf(-1), Currency.getInstance("PEN")));
    }

    @Test
    void shouldNormalizeScale() {
        Money money = new Money(BigDecimal.valueOf(10), Currency.getInstance("PEN"));
        assertEquals("10.00", money.amount().toPlainString());
    }
}
