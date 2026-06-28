package com.edgarrt.poc.payments.application.port.out;

import com.edgarrt.poc.payments.application.usecase.GatewayAccessToken;
import com.edgarrt.poc.payments.domain.model.MerchantId;

public interface TokenProviderPort {
    GatewayAccessToken getToken(MerchantId merchantId);
}
