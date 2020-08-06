package elethu.ikamva.repositories;

import elethu.ikamva.domain.Payment;
import elethu.ikamva.domain.TransactionType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PaymentRepository extends CrudRepository<Payment, Long> {
    @Query("SELECT pay from Payment pay where pay.investmentId = ?1")
    List<Payment> FindPaymentByInvestmentId(String investmentId);
    @Query("SELECT pay FROM Payment pay WHERE pay.transactionType = ?1 AND pay.endDate = NULL")
    List<Payment> FindPaymentByPaymentType(TransactionType transType);
    @Query("SELECT pay FROM Payment pay WHERE pay.investmentId = ?1 AND pay.endDate = NULL")
    List<Payment> FindAllMemberPayments(String InvestmentID);
    @Query("SELECT pay FROM Payment pay WHERE pay.amount = ?1 and pay.investmentId = ?2 AND pay.endDate = ?3")
    Boolean CheckPayment(double payment, String investmentID, OffsetDateTime paymentDate);


    @Query("SELECT pay FROM Payment pay WHERE pay.endDate BETWEEN ?1 AND ?2")
    List<Payment> FindPaymentsBetween(OffsetDateTime fromDate, OffsetDateTime toDate);
    /*@Query("SELECT pay FROM Payment pay WHERE pay.paymentDate BETWEEN ?1 AND ?2 AND pay.investmentId = ?3")
    List<Payment> FindPaymentsByDateRangeForMember(OffsetDateTime fromDate, OffsetDateTime toDate, String investmentID);*/

}
