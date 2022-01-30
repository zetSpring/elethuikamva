package elethu.ikamva.services;

import elethu.ikamva.domain.Payment;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

public interface PaymentService {
    Payment savePayment(Payment payment);
    Payment deletePayment(Long id);
    Payment updatePayment(Long paymentId, Payment payment);
    Payment findPaymentById(Long id);
    void bulkSavePayments(List<Payment> payments);
    List<Payment> findPaymentByInvestId(String investmentId);
    List<Payment> findPaymentsBetweenDates(LocalDate fromDate, LocalDate toDate);
    List<Payment> findMemberPaymentsBetweenDates(String memberInvestId, LocalDate fromDate, LocalDate toDate);
    boolean isPaymentActive(double paymentAmount, String investmentID, LocalDate paymentDate);
    void processCSVFile(MultipartFile csvFile) throws FileNotFoundException;
}
