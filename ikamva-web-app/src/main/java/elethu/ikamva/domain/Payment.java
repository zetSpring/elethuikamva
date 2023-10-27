package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.*;
import elethu.ikamva.domain.enums.TransactionType;
import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "MEMBER_PAYMENTS", schema = "elethu")
@JsonPropertyOrder({"id", "amount", "investmentId"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Payment.class)
public class Payment implements Serializable {

    @Serial
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate paymentDate;

    /*Date of debit or credit or transaction*/
    @Column(name = "CREATED_DATE", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy:HH:mm:ss")
    private LocalDateTime createdDate;

    @Column(name = "PAYMENT_END_DATE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy:HH:mm:ss")
    private LocalDateTime endDate;

    @Column(name = "DELETE_REASON")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String deleteReason;

    @Column(name = "PAYMENT_REFERENCE", nullable = false)
    private String paymentReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSACTION_TYPE")
    private TransactionType transactionType;

    @JsonIgnore
    @JoinColumn(name = "MEMBER_ID_FK")
    @ManyToOne(fetch = FetchType.EAGER)
    private Member memberPayments;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PaymentFile paymentFile;

    public Payment(LocalDateTime endDate, String deleteReason) {
        this.endDate = endDate;
        this.deleteReason = deleteReason;
    }

    public Payment(Double amount, String investmentId, LocalDate paymentDate, String paymentReference) {
        this.amount = amount;
        this.investmentId = investmentId;
        this.paymentDate = paymentDate;
        this.paymentReference = paymentReference;
    }

    public Payment(
            Double amount, String investmentId, LocalDate paymentDate, String paymentReference, Member memberPayments) {
        this.amount = amount;
        this.investmentId = investmentId;
        this.paymentDate = paymentDate;
        this.paymentReference = paymentReference;
        this.memberPayments = memberPayments;
    }

    public Payment(
            Long id,
            Double amount,
            String investmentId,
            LocalDate paymentDate,
            LocalDateTime createdDate,
            String paymentReference,
            TransactionType transactionType,
            Member memberPayments) {
        this.id = id;
        this.amount = amount;
        this.investmentId = investmentId;
        this.paymentDate = paymentDate;
        this.createdDate = createdDate;
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
