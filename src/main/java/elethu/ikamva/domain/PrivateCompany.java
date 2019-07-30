package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PRIVATE_COMPANIES")
public class PrivateCompany implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRIVATE_COM_ID", nullable = false, updatable = false, length = 10, unique = true)
    private Long id;
    @Column(name = "COMPANY_NAME", nullable = false, unique = true)
    private String companyName;
    @Column(name = "REGISTRATION_NO", unique = true)
    private String registrationNo;
    @Column(name = "REGISTRATION_DATE")
    private Date registeredDate;
    @Column(name = "CREATED_DATE", nullable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdDate;
    @Column(name = "END_DATE")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "CORPORATE_ID_FK", nullable = false)
    private CorpCompany corpCompany;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyProjects")
    private Set<Project> projectCompany;
    @OneToOne(mappedBy = "companyAccount", cascade = CascadeType.ALL)
    private Account account;


    public PrivateCompany(Date endDate) {
        this.endDate = endDate;
    }

    public PrivateCompany(String companyName, String registrationNo, Date registeredDate, Date createdDate, CorpCompany corpCompany, Set<Project> projectCompany, Account account) {
        this.companyName = companyName;
        this.registrationNo = registrationNo;
        this.registeredDate = registeredDate;
        this.createdDate = createdDate;
        this.corpCompany = corpCompany;
        this.projectCompany = projectCompany;
        this.account = account;
    }
}
