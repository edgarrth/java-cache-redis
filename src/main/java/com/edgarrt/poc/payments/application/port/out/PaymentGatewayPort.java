package com.edgarrt.poc.payments.application.port.out;

import com.edgarrt.poc.payments.application.usecase.GatewayAccessToken;
import com.edgarrt.poc.payments.application.usecase.GatewayAuthorization;
import com.edgarrt.poc.payments.domain.model.Payment;

import java.util.Map;

public interface PaymentGatewayPort {
    GatewayAuthorization authorize(Payment payment, GatewayAccessToken token, Map<String, String> merchantVariables);
}
