package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "MEMBER_PAYMENTS")
@EqualsAndHashCode(of = {""})
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PAYMENT_ID", nullable = false, updatable = false, length = 10, unique = true)
    private Long id;
    /*Transaction amount - member payments and */
    @Column(name = "AMOUNT", nullable = false)
    private Double amount;
    /*Member Investment Id*/
    @Column(name = "INVESTMENT_ID")
    private String investmentId;
    /*Date of debit or credit or transaction*/
    @Column(name = "PAYMENT_DATE", nullable = false)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy:HH:mm:ss")
    private LocalDate paymentDate;
    @Column(name = "PAYMENT_END_DATE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy:HH:mm:ss")
    private LocalDate endDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "DELETE_REASON")
    private String deleteReason;
    @Column(name = "PAYMENT_REFERENCE", nullable = false)
    private String paymentReference;
    @Column(name = "TRANSACTION_TYPE")
    private TransactionType transactionType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEMBER_ID_FK")
    private Member memberPayments;

    public Payment(LocalDate endDate, String deleteReason) {
        this.endDate = endDate;
        this.deleteReason = deleteReason;
    }

    public Payment(Double amount, String investmentId, LocalDate paymentDate, String paymentReference, Member memberPayments) {
        this.amount = amount;
        this.investmentId = investmentId;
        this.paymentDate = paymentDate;
        this.paymentReference = paymentReference;
        this.memberPayments = memberPayments;
    }

    public Payment(Long id, Double amount, String investmentId, LocalDate paymentDate, LocalDate endDate, String deleteReason, String paymentReference, TransactionType transactionType, Member memberPayments) {
        this.id = id;
        this.amount = amount;
        this.investmentId = investmentId;
        this.paymentDate = paymentDate;
        this.endDate = endDate;
        this.deleteReason = deleteReason;
        this.paymentReference = paymentReference;
        this.transactionType = transactionType;
        this.memberPayments = memberPayments;
    }
}
