package elethu.ikamva.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "CONTACT_DETAILS")
public class ContactDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CONTACT_ID", nullable = false, updatable = false, unique = true, length = 10)
    private Long id;

    @Column(name = "CONTACT", nullable = false, unique = true)
    private String contact;

    @Column(name = "CONTACT_TYPE", nullable = false)
    private String contactType;

    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    private Date createdDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID_FK", nullable = false)
    private Member members;

    public ContactDetails() {
    }

    public ContactDetails(String contact, String contactType, Date createdDate, Member member) {
        this.contact = contact;
        this.contactType = contactType;
        this.createdDate = createdDate;
        this.members = member;
    }

    public ContactDetails(Date endDate) {
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
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

    public Member getMember() {
        return members;
    }

    public void setMember(Member member) {
        this.members = member;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ContactDetails)) {
            return false;
        }
        ContactDetails otherContacts = (ContactDetails) object;
        {
            return (this.id != null || otherContacts.id == null) && (this.id == null || this.id.equals(otherContacts.id));
        }
    }

    @Override
    public String toString() {
        return "elethu.ikamva.domain.ContactDetails[id=" + id + "]";
    }
}



