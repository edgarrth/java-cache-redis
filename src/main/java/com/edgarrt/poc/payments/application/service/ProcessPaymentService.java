package com.edgarrt.poc.payments.application.service;

import com.edgarrt.poc.payments.application.port.in.GetMerchantRiskProfileQuery;
import com.edgarrt.poc.payments.application.port.in.ProcessPaymentUseCase;
import com.edgarrt.poc.payments.application.port.out.*;
import com.edgarrt.poc.payments.application.usecase.*;
import com.edgarrt.poc.payments.domain.exception.PaymentNotFoundException;
import com.edgarrt.poc.payments.domain.model.*;
import com.edgarrt.poc.payments.domain.service.PaymentRiskPolicy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;
import java.util.Map;

@Service
public class ProcessPaymentService implements ProcessPaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final IdempotencyPort idempotencyPort;
    private final GetMerchantRiskProfileQuery merchantRiskProfileQuery;
    private final MerchantVariablesPort merchantVariablesPort;
    private final TokenProviderPort tokenProviderPort;
    private final PaymentGatewayPort paymentGatewayPort;
    private final PaymentRiskPolicy riskPolicy = new PaymentRiskPolicy();

    public ProcessPaymentService(
            PaymentRepository paymentRepository,
            IdempotencyPort idempotencyPort,
            GetMerchantRiskProfileQuery merchantRiskProfileQuery,
            MerchantVariablesPort merchantVariablesPort,
            TokenProviderPort tokenProviderPort,
            PaymentGatewayPort paymentGatewayPort
    ) {
        this.paymentRepository = paymentRepository;
        this.idempotencyPort = idempotencyPort;
        this.merchantRiskProfileQuery = merchantRiskProfileQuery;
        this.merchantVariablesPort = merchantVariablesPort;
        this.tokenProviderPort = tokenProviderPort;
        this.paymentGatewayPort = paymentGatewayPort;
    }

    @Override
    @Transactional
    public PaymentResult process(ProcessPaymentCommand command) {
        MerchantId merchantId = new MerchantId(command.merchantId());
        PaymentId paymentId = PaymentId.newId();

        boolean firstExecution = idempotencyPort.tryStart(merchantId, command.idempotencyKey(), paymentId.value());
        if (!firstExecution) {
            String existingPaymentId = idempotencyPort.findPaymentId(merchantId, command.idempotencyKey())
                    .orElseThrow(() -> new IllegalStateException("Idempotency key exists but payment id was not found in Redis"));
            Payment existing = paymentRepository.findById(new PaymentId(existingPaymentId))
                    .orElseThrow(() -> new PaymentNotFoundException(existingPaymentId + " still processing or rolled back"));
            return PaymentResult.from(existing, "REPLAYED_FROM_REDIS_IDEMPOTENCY", "Redis Cluster: idempotency replay + possible @Cacheable hit", "Caffeine: not used on replay");
        }

        Payment payment = Payment.receive(
                paymentId,
                merchantId,
                new CustomerId(command.customerId()),
                command.idempotencyKey(),
                new Money(command.amount(), Currency.getInstance(command.currency())),
                new PaymentMethod(PaymentMethodType.valueOf(command.paymentMethod().type()), command.paymentMethod().token())
        );

        MerchantRiskProfile riskProfile = merchantRiskProfileQuery.getRiskProfile(merchantId.value());
        Map<String, String> variables = merchantVariablesPort.getVariables(merchantId);

        String caffeineUsage = "Caffeine local: not used because risk policy declined before PSP authorization";
        var rejection = riskPolicy.evaluate(payment, riskProfile, variables);
        if (rejection.isPresent()) {
            payment.decline(rejection.get());
        } else {
            payment.markAuthorizing();
            GatewayAccessToken gatewayToken = tokenProviderPort.getToken(merchantId);
            caffeineUsage = gatewayToken.source();
            GatewayAuthorization authorization = paymentGatewayPort.authorize(payment, gatewayToken, variables);
            if (authorization.approved()) {
                payment.approve(authorization.authorizationCode());
            } else {
                payment.decline(authorization.reason());
            }
        }

        Payment saved = paymentRepository.save(payment);

        return PaymentResult.from(
                saved,
                "CREATED_AND_REGISTERED_IN_REDIS_IDEMPOTENCY",
                "Redis Cluster: @Cacheable merchant-risk-profiles + payment:variables hash",
                caffeineUsage
        );
    }
}
