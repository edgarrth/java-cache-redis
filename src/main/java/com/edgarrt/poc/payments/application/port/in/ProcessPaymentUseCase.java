package com.edgarrt.poc.payments.application.port.in;

import com.edgarrt.poc.payments.application.usecase.ProcessPaymentCommand;
import com.edgarrt.poc.payments.application.usecase.PaymentResult;

public interface ProcessPaymentUseCase {
    PaymentResult process(ProcessPaymentCommand command);
}
