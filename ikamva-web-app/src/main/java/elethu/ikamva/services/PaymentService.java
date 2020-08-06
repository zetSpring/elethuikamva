package elethu.ikamva.services;

import elethu.ikamva.domain.Payment;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface PaymentService {
    void saveOrUpdatePayment(Payment payment);

    void DeletePayment(Payment payment);

    Payment FindPaymentById(Long id);

    List<Payment> FindPaymentByInvestId(String investmentId);

    List<Payment> FindAllPaymentsBetween(OffsetDateTime fromDate, OffsetDateTime toDate, String memberInvestId);

    Boolean IsPaymentActive(double payment, String investmentID, OffsetDateTime paymentDate);
}
