package com.edgarrt.poc.payments.application.service;

import com.edgarrt.poc.payments.application.port.in.GetPaymentQuery;
import com.edgarrt.poc.payments.application.port.out.PaymentRepository;
import com.edgarrt.poc.payments.application.usecase.PaymentView;
import com.edgarrt.poc.payments.domain.exception.PaymentNotFoundException;
import com.edgarrt.poc.payments.domain.model.PaymentId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetPaymentService implements GetPaymentQuery {

    private final PaymentRepository paymentRepository;

    public GetPaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentView getById(String paymentId) {
        return paymentRepository.findById(new PaymentId(paymentId))
                .map(PaymentView::from)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }
}
