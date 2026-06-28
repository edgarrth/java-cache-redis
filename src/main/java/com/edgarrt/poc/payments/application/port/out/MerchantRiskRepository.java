package com.edgarrt.poc.payments.application.port.out;

import com.edgarrt.poc.payments.domain.model.MerchantId;
import com.edgarrt.poc.payments.domain.model.MerchantRiskProfile;

public interface MerchantRiskRepository {
    MerchantRiskProfile load(MerchantId merchantId);
}
