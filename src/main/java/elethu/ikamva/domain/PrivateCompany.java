package elethu.ikamva.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
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

    public PrivateCompany() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public CorpCompany getCorpCompany() {
        return corpCompany;
    }

    public void setCorpCompany(CorpCompany corpCompany) {
        this.corpCompany = corpCompany;
    }

    public Set<Project> getProjectCompany() {
        return projectCompany;
    }

    public void setProjectCompany(Set<Project> projectCompany) {
        this.projectCompany = projectCompany;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PrivateCompany)) {
            return false;
        }
        PrivateCompany otherPrivateCompany = (PrivateCompany) object;
        {
            return (this.id != null || otherPrivateCompany.id == null) && (this.id == null || this.id.equals(otherPrivateCompany.id));
        }
    }

    @Override
    public String toString() {
        return "elethu.ikamva.domain.PrivateCompany[id=" + id + "]";
    }
}
