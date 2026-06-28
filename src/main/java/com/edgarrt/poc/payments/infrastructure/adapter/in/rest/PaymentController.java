package com.edgarrt.poc.payments.infrastructure.adapter.in.rest;

import com.edgarrt.poc.payments.application.port.in.GetPaymentQuery;
import com.edgarrt.poc.payments.application.port.in.ProcessPaymentUseCase;
import com.edgarrt.poc.payments.application.usecase.PaymentMethodCommand;
import com.edgarrt.poc.payments.application.usecase.PaymentResult;
import com.edgarrt.poc.payments.application.usecase.PaymentView;
import com.edgarrt.poc.payments.application.usecase.ProcessPaymentCommand;
import com.edgarrt.poc.payments.infrastructure.adapter.in.rest.dto.ProcessPaymentHttpRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final ProcessPaymentUseCase processPaymentUseCase;
    private final GetPaymentQuery getPaymentQuery;

    public PaymentController(ProcessPaymentUseCase processPaymentUseCase, GetPaymentQuery getPaymentQuery) {
        this.processPaymentUseCase = processPaymentUseCase;
        this.getPaymentQuery = getPaymentQuery;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResult process(@Valid @RequestBody ProcessPaymentHttpRequest request) {
        return processPaymentUseCase.process(new ProcessPaymentCommand(
                request.idempotencyKey(),
                request.merchantId(),
                request.customerId(),
                request.amount(),
                request.currency(),
                new PaymentMethodCommand(request.paymentMethod().type(), request.paymentMethod().token())
        ));
    }

    @GetMapping("/{paymentId}")
    public PaymentView getById(@PathVariable String paymentId) {
        return getPaymentQuery.getById(paymentId);
    }
}
