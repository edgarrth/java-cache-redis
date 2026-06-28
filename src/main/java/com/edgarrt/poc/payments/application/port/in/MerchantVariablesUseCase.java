package com.edgarrt.poc.payments.application.port.in;

import java.util.Map;

public interface MerchantVariablesUseCase {
    Map<String, String> getVariables(String merchantId);
    Map<String, String> saveVariables(String merchantId, Map<String, String> variables);
}
