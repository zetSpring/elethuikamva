package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.domain.Payment;
import elethu.ikamva.exception.PaymentException;
import elethu.ikamva.repositories.PaymentRepository;
import elethu.ikamva.services.PaymentService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    public void deletePayment(Payment payment) {
        if (isPaymentActive(payment))
            saveOrUpdatePayment(payment);
        else
            throw new PaymentException("Could not find payment for amount: " + payment.getAmount());
    }

    @Override
    public Payment findPaymentById(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);

        if (!paymentOptional.isPresent()) {
            throw new PaymentException("Could not find payment for payment id: " + id);
        }
        return paymentOptional.get();
    }

    @Override
    public Payment findPaymentByInvestId(String investmentId) {
        Optional<Payment> paymentOptional = paymentRepository.findPaymentByInvestmentId(investmentId);

        if (!paymentOptional.isPresent()) {
            throw new PaymentException("Payment could not be found for investment id: " + investmentId);
        }
        return paymentOptional.get();
    }

    @Override
    public Set<Payment> findAllPayments() {
        Set<Payment> payments = new HashSet<>();

        paymentRepository.findAll().iterator().forEachRemaining(payments::add);

        return payments;
    }

    @Override
    public Boolean isPaymentActive(Payment payment) {
        return payment.getId() != null && payment.getEndDate() == null;
    }
}
