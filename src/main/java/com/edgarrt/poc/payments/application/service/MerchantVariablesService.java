package com.edgarrt.poc.payments.application.service;

import com.edgarrt.poc.payments.application.port.in.MerchantVariablesUseCase;
import com.edgarrt.poc.payments.application.port.out.MerchantVariablesPort;
import com.edgarrt.poc.payments.domain.model.MerchantId;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MerchantVariablesService implements MerchantVariablesUseCase {
    private final MerchantVariablesPort merchantVariablesPort;

    public MerchantVariablesService(MerchantVariablesPort merchantVariablesPort) {
        this.merchantVariablesPort = merchantVariablesPort;
    }

    @Override
    public Map<String, String> getVariables(String merchantId) {
        return merchantVariablesPort.getVariables(new MerchantId(merchantId));
    }

    @Override
    public Map<String, String> saveVariables(String merchantId, Map<String, String> variables) {
        merchantVariablesPort.saveVariables(new MerchantId(merchantId), variables);
        return merchantVariablesPort.getVariables(new MerchantId(merchantId));
    }
}
