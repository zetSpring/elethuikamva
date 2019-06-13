package elethu.ikamva.services;

import elethu.ikamva.domain.Payment;

import java.util.Set;

public interface PaymentService {
    void saveOrUpdatePayment(Payment payment);

    void deletePayment(Payment payment);

    Payment findPaymentById(Long id);

    Payment findPaymentByInvestId(String investmentId);

    Set<Payment> findAllPayments();

    Boolean isPaymentActive(Payment payment);
}
