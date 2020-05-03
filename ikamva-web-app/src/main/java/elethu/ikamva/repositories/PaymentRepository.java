package elethu.ikamva.repositories;

import elethu.ikamva.domain.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface PaymentRepository extends CrudRepository<Payment, Long> {
    Optional<Payment> findPaymentByInvestmentId(String investmentId);
}
