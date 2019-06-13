package elethu.ikamva.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;


@Entity
@Table(name = "IKAMVA_ACCOUNTS")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACCOUNT_ID", nullable = false, updatable = false, unique = true, length = 10)
    private Long id;

    @Column(name = "ACCOUNT_NO", unique = true, nullable = false)
    private Long accountNo;

    @Column(name = "ACCOUNT_TYPE", nullable = false)
    private String accountType;

    @Column(name = "CREATED_DATE", nullable = false)
    private ZonedDateTime insertDate;

    @Column(name = "END_DATE")
    private ZonedDateTime endDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRIVATE_COMPANY_FK", nullable = false)
    private PrivateCompany companyAccount;

    public Account() {
    }

    public Account(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Account(Long accountNo, String accountType, ZonedDateTime insertDate, PrivateCompany companyAccount) {
        this.accountNo = accountNo;
        this.accountType = accountType;
        this.insertDate = insertDate;
        this.companyAccount = companyAccount;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Long accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public ZonedDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(ZonedDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public PrivateCompany getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(PrivateCompany companyAccount) {
        this.companyAccount = companyAccount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Account)) {
            return false;
        }
        Account otherAccount = (Account) object;
        {
            return (this.id != null || otherAccount.id == null) && (this.id == null || this.id.equals(otherAccount.id));
        }
    }

    @Override
    public String toString() {
        return "elethu.ikamva.domain.Account[id=" + id + "]";
    }

}
