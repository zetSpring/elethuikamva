package elethu.ikamva.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
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
    private Date endDate;
    @Column(name = "DELETE_REASON")
    private String deleteReason;
    @Column(name = "PAYMENT_REFERENCE", nullable = false)
    private String paymentReference;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID_FK", nullable = false)
    private Member memberPayment;

    public Payment() {
    }

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(String investmentId) {
        this.investmentId = investmentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public Member getMemberPayment() {
        return memberPayment;
    }

    public void setMemberPayment(Member memberPayment) {
        this.memberPayment = memberPayment;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDeleteReason() {
        return deleteReason;
    }

    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment otherPayments = (Payment) object;
        {
            return (this.id != null || otherPayments.id == null) && (this.id == null || this.id.equals(otherPayments.id));
        }
    }

    @Override
    public String toString() {
        return "elethu.ikamva.domain.Payment[id=" + id + "]";
    }
}
