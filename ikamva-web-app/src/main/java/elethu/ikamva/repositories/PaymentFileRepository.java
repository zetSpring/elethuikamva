package elethu.ikamva.repositories;

import elethu.ikamva.domain.PaymentFile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface PaymentFileRepository extends JpaRepository<PaymentFile, Long> {
    Optional<PaymentFile> findByFileNameAndFileTotalAmountAndNumberOfTransactions(String fileName, double fileTotalAmount, int numberOfTransactions);
}
