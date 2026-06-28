package com.edgarrt.poc.payments.infrastructure.adapter.out.persistence;

import com.edgarrt.poc.payments.domain.model.MerchantRiskProfile;
import com.edgarrt.poc.payments.domain.model.RiskLevel;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

class MerchantRiskPersistenceMapper {
    private MerchantRiskPersistenceMapper() {
    }

    static MerchantRiskProfile toDomain(MerchantRiskJpaEntity entity) {
        Set<String> currencies = Arrays.stream(entity.getAllowedCurrencies().split(","))
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .collect(Collectors.toSet());
        return new MerchantRiskProfile(
                entity.getMerchantId(),
                entity.getMerchantName(),
                RiskLevel.valueOf(entity.getRiskLevel()),
                entity.getMaxTicketAmount(),
                currencies,
                entity.getUpdatedAt()
        );
    }
}
