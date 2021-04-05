package elethu.ikamva.services;

import elethu.ikamva.domain.Payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface PaymentService {
    Payment saveOrUpdatePayment(Payment payment);

    void DeletePayment(Long id);

    Payment FindPaymentById(Long id);

    List<Payment> FindPaymentByInvestId(String investmentId);

    List<Payment> FindAllPaymentsBetween(LocalDate fromDate, LocalDate toDate, String memberInvestId);

    Boolean IsPaymentActive(double payment, String investmentID, LocalDateTime paymentDate);
}
