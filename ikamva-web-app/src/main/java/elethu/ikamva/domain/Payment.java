package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "MEMBER_PAYMENTS", schema = "elethu")
@JsonPropertyOrder({"id", "amount", "investmentId"})
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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

    public Payment(Double amount, String investmentId, LocalDate paymentDate, String paymentReference) {
        this.amount = amount;
        this.investmentId = investmentId;
        this.paymentDate = paymentDate;
        this.paymentReference = paymentReference;
    }

    public Payment(Double amount, String investmentId, LocalDate paymentDate, String paymentReference, Member memberPayments) {
        this.amount = amount;
        this.investmentId = investmentId;
        this.paymentDate = paymentDate;
        this.paymentReference = paymentReference;
        this.memberPayments = memberPayments;
    }

    public Payment(Long id, Double amount, String investmentId, LocalDate paymentDate, String paymentReference, TransactionType transactionType, Member memberPayments) {
        this.id = id;
        this.amount = amount;
        this.investmentId = investmentId;
        this.paymentDate = paymentDate;
        this.deleteReason = deleteReason;
        this.paymentReference = paymentReference;
        this.transactionType = transactionType;
        this.memberPayments = memberPayments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Payment payment = (Payment) o;
        return id != null && Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
