package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date endDate;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ApiModelProperty(notes = "Private company identification at which the account belongs to.")
    @JoinColumn(name = "PRIVATE_COMPANY_FK", nullable = false)
    private PrivateCompany companyAccount;

    public Account(Date endDate) {
        this.endDate = endDate;
    }

    public Account(Long accountNo, String accountType, Date insertDate, PrivateCompany companyAccount) {
        this.accountNo = accountNo;
        this.accountType = accountType;
        this.insertDate = insertDate;
        this.companyAccount = companyAccount;
    }
}
