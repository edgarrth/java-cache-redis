package com.edgarrt.poc.payments.infrastructure.adapter.out.psp;

import com.edgarrt.poc.payments.application.port.out.PaymentGatewayPort;
import com.edgarrt.poc.payments.application.usecase.GatewayAccessToken;
import com.edgarrt.poc.payments.application.usecase.GatewayAuthorization;
import com.edgarrt.poc.payments.domain.model.Payment;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class FakeCardGatewayAdapter implements PaymentGatewayPort {
    @Override
    public GatewayAuthorization authorize(Payment payment, GatewayAccessToken token, Map<String, String> merchantVariables) {
        if (token == null || token.token() == null || token.token().isBlank()) {
            return GatewayAuthorization.declined("PSP token is missing");
        }
        if (payment.paymentMethod().token().toLowerCase().contains("declined")) {
            return GatewayAuthorization.declined("Simulated gateway decline by payment method token");
        }
        String routingKey = merchantVariables.getOrDefault("pspRoutingKey", "default-route");
        String shortCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return GatewayAuthorization.approved("AUTH-" + routingKey.toUpperCase().replace('-', '_') + "-" + shortCode);
    }
}
