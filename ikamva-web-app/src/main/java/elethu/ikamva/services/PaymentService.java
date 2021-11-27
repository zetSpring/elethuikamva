package elethu.ikamva.services;

import elethu.ikamva.domain.Payment;

import java.time.LocalDate;
import java.util.List;

public interface PaymentService {
    Payment SavePayment(Payment payment);
    Payment DeletePayment(Long id);
    Payment UpdatePayment(Long paymentId, Payment payment);
    Payment FindPaymentById(Long id);
    void BulkSavePayments(List<Payment> payments);
    List<Payment> FindPaymentByInvestId(String investmentId);
    List<Payment> FindPaymentsBetweenDates(LocalDate fromDate, LocalDate toDate);
    List<Payment> FindMemberPaymentsBetweenDates(String memberInvestId, LocalDate fromDate, LocalDate toDate);
    boolean IsPaymentActive(double paymentAmount, String investmentID, LocalDate paymentDate);
}
