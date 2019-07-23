package elethu.ikamva.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "IKAMVA_CORPORATIVE")
public class CorpCompany implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CORPORATE_ID", nullable = false, updatable = false, length = 10, unique = true)
    private Long id;
    @Column(name = "REGISTRATION_NO", nullable = false, unique = true)
    private Long registrationNo;
    @Column(name = "COMPANY_NAME", nullable = false, unique = true)
    private String corpName;
    @Column(name = "REGISTRATION_DATE", nullable = false)
    private String registeredDate;
    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    private Date createdCreated;
    @Column(name = "END_DATE")
    private Date endDate;

    @OneToMany(mappedBy = "corpCompany", cascade = CascadeType.ALL)
    private Set<PrivateCompany> corpPrivateCompany = new HashSet<>();
    @OneToMany(mappedBy = "corpCompany", cascade = CascadeType.ALL)
    private Set<Member> memberSet = new HashSet<>();

    public CorpCompany() {
    }

    public CorpCompany(Date endDate) {
        this.endDate = endDate;
    }

    public CorpCompany(Long registrationNo, String corpName, String registeredDate, Date createdCreated) {
        this.registrationNo = registrationNo;
        this.corpName = corpName;
        this.registeredDate = registeredDate;
        this.createdCreated = createdCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(Long registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Date getCreatedCreated() {
        return createdCreated;
    }

    public void setCreatedCreated(Date createdCreated) {
        this.createdCreated = createdCreated;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set<PrivateCompany> getCorpPrivateCompany() {
        return corpPrivateCompany;
    }

    public void setCorpPrivateCompany(Set<PrivateCompany> corpPrivateCompany) {
        this.corpPrivateCompany = corpPrivateCompany;
    }

    public Set<Member> getMemberSet() {
        return memberSet;
    }

    public void setMemberSet(Set<Member> memberSet) {
        this.memberSet = memberSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CorpCompany)) {
            return false;
        }
        CorpCompany otherCorp = (CorpCompany) object;
        {
            return (this.id != null || otherCorp.id == null) && (this.id == null || this.id.equals(otherCorp.id));
        }
    }

    @Override
    public String toString() {
        return "elethu.ikamva.domain.CorpCompany[id=" + id + "]";
    }
}
