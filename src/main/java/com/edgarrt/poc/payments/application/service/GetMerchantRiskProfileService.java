package com.edgarrt.poc.payments.application.service;

import com.edgarrt.poc.payments.application.port.in.GetMerchantRiskProfileQuery;
import com.edgarrt.poc.payments.application.port.out.MerchantRiskRepository;
import com.edgarrt.poc.payments.domain.model.MerchantId;
import com.edgarrt.poc.payments.domain.model.MerchantRiskProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetMerchantRiskProfileService implements GetMerchantRiskProfileQuery {
    private static final Logger log = LoggerFactory.getLogger(GetMerchantRiskProfileService.class);
    private final MerchantRiskRepository merchantRiskRepository;

    public GetMerchantRiskProfileService(MerchantRiskRepository merchantRiskRepository) {
        this.merchantRiskRepository = merchantRiskRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "merchant-risk-profiles", key = "#merchantId")
    public MerchantRiskProfile getRiskProfile(String merchantId) {
        log.info("Cache miss: loading merchant risk profile from PostgreSQL for merchantId={}", merchantId);
        return merchantRiskRepository.load(new MerchantId(merchantId));
    }
}
