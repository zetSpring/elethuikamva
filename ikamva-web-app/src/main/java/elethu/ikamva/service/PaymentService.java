package elethu.ikamva.service;

import elethu.ikamva.domain.Payment;
import elethu.ikamva.view.PaymentView;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

public interface PaymentService {
    Payment savePayment(Payment payment);
    Payment deletePayment(Long id);
    Payment updatePayment(Payment payment);
    Payment findPaymentById(Long id);
    void bulkSavePayments(List<Payment> payments);
    PaymentView findPaymentByInvestId(String investmentId, int pageNo, int pageSize, String sortBy);
    PaymentView findPaymentsBetweenDates(LocalDate fromDate, LocalDate toDate, int pageNo, int pageSize, String sortBy);
    PaymentView findMemberPaymentsBetweenDates(String memberInvestId, LocalDate fromDate, LocalDate toDate, int pageNo, int pageSize, String sortBy);
    boolean isPaymentActive(double paymentAmount, String investmentID, LocalDate paymentDate);
    void processCSVFile(MultipartFile csvFile) throws FileNotFoundException;
}
