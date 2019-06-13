package elethu.ikamva.repositories;

import elethu.ikamva.domain.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
    Optional<Payment> findPaymentByInvestmentId(String investmentId);
}
