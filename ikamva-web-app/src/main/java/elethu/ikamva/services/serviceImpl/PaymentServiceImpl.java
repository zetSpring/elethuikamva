package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.domain.Payment;
import elethu.ikamva.exception.PaymentException;
import elethu.ikamva.repositories.PaymentRepository;
import elethu.ikamva.services.PaymentService;
import org.springframework.boot.actuate.endpoint.web.Link;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void saveOrUpdatePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public void DeletePayment(Payment payment) {
        if (IsPaymentActive(payment.getAmount(), payment.getInvestmentId(), payment.getPaymentDate()))
            saveOrUpdatePayment(payment);
        else
            throw new PaymentException("Could not find payment for amount: " + payment.getAmount());
    }

    @Override
    public Payment FindPaymentById(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);

        if (!paymentOptional.isPresent()) {
            throw new PaymentException("Could not find payment for payment id: " + id);
        }
        return paymentOptional.get();
    }

    @Override
    public List<Payment> FindPaymentByInvestId(String investmentId) {
        List<Payment> paymentList = paymentRepository.FindPaymentByInvestmentId(investmentId);

        if (paymentList.isEmpty()) {
            throw new PaymentException("This member : " + investmentId + " has no payments made. ");
        }
       return null;
    }

    @Override
    public List<Payment> FindAllPaymentsBetween(OffsetDateTime fromDate, OffsetDateTime toDate, String memberInvestId) {
        List<Payment> payments = new LinkedList<>();

        //paymentRepository.

        return payments;
    }

    @Override
    public Boolean IsPaymentActive(double payment, String investmentID, OffsetDateTime paymentDate) {
        return paymentRepository.CheckPayment(payment, investmentID, paymentDate);
    }
}
