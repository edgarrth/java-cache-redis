package com.edgarrt.poc.payments.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMerchantRiskJpaRepository extends JpaRepository<MerchantRiskJpaEntity, String> {
}
