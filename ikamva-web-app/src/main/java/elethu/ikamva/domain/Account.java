package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "IKAMVA_ACCOUNTS", schema = "elethu")
@ApiModel(description = "Ikamva business accounts")
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

    @ApiModelProperty(notes = "A type of bank account (current, business, etc.)")
    @Column(name = "BANK_NAME")
    private String bank;

    @Column(name = "CREATED_DATE", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    @ApiModelProperty(notes = "Datetime at which the record is created the first time.")
    private LocalDate createdDate;


    @Column(name = "END_DATE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    @ApiModelProperty(notes = "Datetime at which the record is deleted from the database.")
    private LocalDate endDate;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ApiModelProperty(notes = "Private company identification at which the account belongs to.")
    @JoinColumn(name = "PRIVATE_COMPANY_FK", nullable = false)
    @JsonIgnore
    private PrivateCompany companyAccount;

    public Account(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Account(Long accountNo, String accountType, LocalDate createdDate, PrivateCompany companyAccount) {
        this.accountNo = accountNo;
        this.accountType = accountType;
        this.createdDate = createdDate;
        this.companyAccount = companyAccount;
    }

    public Account(Long id, Long accountNo, String accountType, LocalDate createdDate, PrivateCompany companyAccount) {
        this.accountNo = accountNo;
        this.accountType = accountType;
        this.createdDate = createdDate;
        this.companyAccount = companyAccount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
