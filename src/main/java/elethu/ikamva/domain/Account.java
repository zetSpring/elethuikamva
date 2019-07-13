package elethu.ikamva.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "IKAMVA_ACCOUNTS")
@ApiModel(description = "Account entity")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "System generated student identification")
    @Column(name = "ACCOUNT_ID", nullable = false, updatable = false, unique = true, length = 10)
    private Long id;

    @ApiModelProperty(notes = "A bank issued account number.")
    @Column(name = "ACCOUNT_NO", unique = true, nullable = false)
    private Long accountNo;

    @ApiModelProperty(notes = "A type of bank account (current, business, etc.)")
    @Column(name = "ACCOUNT_TYPE", nullable = false)
    private String accountType;

    @ApiModelProperty(notes = "Datetime at which the record is created the first time.")
    @Column(name = "CREATED_DATE", nullable = false)
    private Date insertDate;

    @ApiModelProperty(notes = "Datetime at which the record is deleted from the database.")
    @Column(name = "END_DATE")
    private Date endDate;

    @OneToOne(cascade = CascadeType.ALL)
    @ApiModelProperty(notes = "Private company identification at which the account belongs to.")
    @JoinColumn(name = "PRIVATE_COMPANY_FK", nullable = false)
    private PrivateCompany companyAccount;

    public Account() {
    }

    public Account(Date endDate) {
        this.endDate = endDate;
    }

    public Account(Long accountNo, String accountType, Date insertDate, PrivateCompany companyAccount) {
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

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
