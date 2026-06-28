package com.edgarrt.poc.payments.infrastructure.adapter.in.rest;

import com.edgarrt.poc.payments.application.port.in.GetMerchantRiskProfileQuery;
import com.edgarrt.poc.payments.application.port.in.MerchantVariablesUseCase;
import com.edgarrt.poc.payments.domain.model.MerchantRiskProfile;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/merchants")
public class MerchantController {
    private final GetMerchantRiskProfileQuery merchantRiskProfileQuery;
    private final MerchantVariablesUseCase merchantVariablesUseCase;

    public MerchantController(GetMerchantRiskProfileQuery merchantRiskProfileQuery, MerchantVariablesUseCase merchantVariablesUseCase) {
        this.merchantRiskProfileQuery = merchantRiskProfileQuery;
        this.merchantVariablesUseCase = merchantVariablesUseCase;
    }

    @GetMapping("/{merchantId}/risk-profile")
    public MerchantRiskProfile getRiskProfile(@PathVariable String merchantId) {
        return merchantRiskProfileQuery.getRiskProfile(merchantId);
    }

    @GetMapping("/{merchantId}/variables")
    public Map<String, String> getVariables(@PathVariable String merchantId) {
        return merchantVariablesUseCase.getVariables(merchantId);
    }

    @PutMapping("/{merchantId}/variables")
    public Map<String, String> saveVariables(@PathVariable String merchantId, @RequestBody Map<String, String> variables) {
        return merchantVariablesUseCase.saveVariables(merchantId, variables);
    }
}
