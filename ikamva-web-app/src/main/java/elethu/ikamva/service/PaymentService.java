package elethu.ikamva.service;

import elethu.ikamva.domain.Payment;
import elethu.ikamva.view.PaymentView;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PaymentService {
    Payment deletePayment(Long id);

    Payment findPaymentById(Long id);

    Payment savePayment(Payment payment);

    Payment updatePayment(Payment payment);

    void processCSVFile(MultipartFile csvFile) throws FileNotFoundException;

    Map<Double, Map<Integer, List<Payment>>> bulkSavePayments(List<Payment> payments);

    boolean isPaymentActive(double paymentAmount, String investmentID, LocalDate paymentDate);

    PaymentView findPaymentByInvestId(String investmentId, int pageNo, int pageSize, String sortBy);

    PaymentView findPaymentsBetweenDates(LocalDate fromDate, LocalDate toDate, int pageNo, int pageSize, String sortBy);

    PaymentView findMemberPaymentsBetweenDates(
            String memberInvestId, LocalDate fromDate, LocalDate toDate, int pageNo, int pageSize, String sortBy);
}
