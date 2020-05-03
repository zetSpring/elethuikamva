package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "MEMBER_PAYMENTS")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PAYMENT_ID", nullable = false, updatable = false, length = 10, unique = true)
    private Long id;
    @Column(name = "AMOUNT", nullable = false)
    private Double amount;
    @Column(name = "INVESTMENT_ID", nullable = false)
    private String investmentId;
    @Column(name = "PAYMENT_DATE", nullable = false)
    private Date paymentDate;
    @Column(name = "PAYMENT_END_DATE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date endDate;
    @Column(name = "DELETE_REASON")
    private String deleteReason;
    @Column(name = "PAYMENT_REFERENCE", nullable = false)
    private String paymentReference;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID_FK", nullable = false)
    private Member memberPayment;

    public Payment(Date endDate, String deleteReason) {
        this.endDate = endDate;
        this.deleteReason = deleteReason;
    }

    public Payment(Double amount, String investmentId, Date paymentDate, String paymentReference, Member memberPayment) {
        this.amount = amount;
        this.investmentId = investmentId;
        this.paymentDate = paymentDate;
        this.paymentReference = paymentReference;
        this.memberPayment = memberPayment;
    }
}
