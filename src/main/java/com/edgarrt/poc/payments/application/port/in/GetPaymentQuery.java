package com.edgarrt.poc.payments.application.port.in;

import com.edgarrt.poc.payments.application.usecase.PaymentView;

public interface GetPaymentQuery {
    PaymentView getById(String paymentId);
}
