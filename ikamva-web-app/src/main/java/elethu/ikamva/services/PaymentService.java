package elethu.ikamva.services;

import elethu.ikamva.domain.Payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface PaymentService {
    Payment savePayment(Payment payment);
    Payment DeletePayment(Long id);
    Payment findPaymentById(Long id);
    List<Payment> findPaymentByInvestId(String investmentId);
    List<Payment> findPaymentsBetweenDates(LocalDate fromDate, LocalDate toDate);
    List<Payment> findMemberPaymentsBetweenDates(String memberInvestId, LocalDate fromDate, LocalDate toDate);
    boolean IsPaymentActive(double payment, String investmentID, LocalDate paymentDate);
}
