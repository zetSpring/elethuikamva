package elethu.ikamva.repositories;

import elethu.ikamva.domain.Payment;
import elethu.ikamva.domain.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PaymentRepository extends PagingAndSortingRepository<Payment, Long> {
    @Query("SELECT pay from Payment pay where pay.id = ?1 AND pay.endDate = NULL")
    Optional<Payment> findPaymentById(Long id);
    @Query("SELECT pay from Payment pay where pay.investmentId = UPPER(?1) AND pay.endDate = NULL")
    Page<Payment> findPaymentByInvestmentId(String investmentId, Pageable pageable);
    @Query("SELECT pay FROM Payment pay WHERE pay.transactionType = ?1 AND pay.endDate = NULL")
    List<Payment> findPaymentByPaymentType(TransactionType transType);
    @Query("SELECT pay FROM Payment pay WHERE pay.investmentId = ?1 AND pay.endDate = NULL")
    List<Payment> findAllMemberPayments(String InvestmentID);
    @Query("SELECT pay FROM Payment pay WHERE pay.amount = ?1 and pay.investmentId = ?2 AND pay.paymentDate = ?3")
    Optional<Payment> checkPayment(double payment, String investmentID, LocalDate paymentDate);
    @Query("SELECT pay FROM Payment pay WHERE pay.endDate = NULL AND pay.paymentDate BETWEEN ?1 AND ?2")
    Page<Payment> findPaymentsBetween(LocalDate fromDate, LocalDate toDate, Pageable pageable);
    @Query("SELECT pay FROM Payment pay WHERE pay.investmentId = ?1 AND pay.endDate = NULL AND pay.paymentDate BETWEEN ?2 AND ?3")
    Page<Payment> findPaymentsByDateRangeForMember(String memberInvestId, LocalDate fromDate, LocalDate toDate, Pageable pageable);

}
