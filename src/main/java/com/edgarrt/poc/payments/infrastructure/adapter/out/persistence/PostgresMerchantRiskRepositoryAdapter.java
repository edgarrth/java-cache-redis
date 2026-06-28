package com.edgarrt.poc.payments.infrastructure.adapter.out.persistence;

import com.edgarrt.poc.payments.application.port.out.MerchantRiskRepository;
import com.edgarrt.poc.payments.domain.exception.MerchantRiskProfileNotFoundException;
import com.edgarrt.poc.payments.domain.model.MerchantId;
import com.edgarrt.poc.payments.domain.model.MerchantRiskProfile;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresMerchantRiskRepositoryAdapter implements MerchantRiskRepository {
    private final SpringDataMerchantRiskJpaRepository repository;

    public PostgresMerchantRiskRepositoryAdapter(SpringDataMerchantRiskJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public MerchantRiskProfile load(MerchantId merchantId) {
        return repository.findById(merchantId.value())
                .map(MerchantRiskPersistenceMapper::toDomain)
                .orElseThrow(() -> new MerchantRiskProfileNotFoundException(merchantId.value()));
    }
}
