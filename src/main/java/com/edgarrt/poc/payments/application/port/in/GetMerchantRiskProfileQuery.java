package com.edgarrt.poc.payments.application.port.in;

import com.edgarrt.poc.payments.domain.model.MerchantRiskProfile;

public interface GetMerchantRiskProfileQuery {
    MerchantRiskProfile getRiskProfile(String merchantId);
}
