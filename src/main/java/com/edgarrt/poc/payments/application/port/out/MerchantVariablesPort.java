package com.edgarrt.poc.payments.application.port.out;

import com.edgarrt.poc.payments.domain.model.MerchantId;

import java.util.Map;

public interface MerchantVariablesPort {
    Map<String, String> getVariables(MerchantId merchantId);
    void saveVariables(MerchantId merchantId, Map<String, String> variables);
}
