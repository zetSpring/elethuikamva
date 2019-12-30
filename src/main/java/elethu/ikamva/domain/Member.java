package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "IKAMVA_MEMBERS")
@EqualsAndHashCode(of = {""})
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID", unique = true, updatable = false, length = 10)
    private Long id;
    @Column(name = "IDENTITY_NO", nullable = false, unique = true, length = 13)
    private Long memberIdentityNo;
    @Column(name = "INVESTMENT_ID")
    private String investmentId;
    @Column(name = "MEMBER_NAME", nullable = false)
    private String firstname;
    @Column(name = "MEMBER_SURNAME", nullable = false)
    private String lastname;
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private Date dob;  /*member date of birth*/
    @Column(name = "GENDER", nullable = false)
    private String gender;
    @Lob
    private Byte[] profilePic;
    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    private Date createdDate;
    @Column(name = "END_DATE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "CORPORATE_ID_FK", nullable = false)
    private CorpCompany corpMember;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberPayment", fetch = FetchType.EAGER)
    private Set<Payment> payments = new HashSet<>();
    @OneToMany(mappedBy = "members", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ContactDetails> memberContacts = new HashSet<>();
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "userMember")
    private User user;

    /*update profile picture*/
    public Member(Byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public Member(Date endDate) {
        this.endDate = endDate;
    }

    public Member(Long memberIdentityNo, String investmentId, String firstname, String lastname, Date dob, String gender, Date createdDate, CorpCompany corpCompany, Set<Payment> memberPayment, Set<ContactDetails> memberContacts) {
        this.memberIdentityNo = memberIdentityNo;
        this.investmentId = investmentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.gender = gender;
        this.createdDate = createdDate;
        this.corpMember = corpCompany;
        this.payments = memberPayment;
        this.memberContacts = memberContacts;
    }

    public Member(Long memberIdentityNo, String investmentId, String firstname, String lastname, Date dob, String gender, Byte[] profilePic, Date createdDate, Date endDate, CorpCompany corpCompany, Set<Payment> memberPayment, Set<ContactDetails> memberContacts) {
        this.memberIdentityNo = memberIdentityNo;
        this.investmentId = investmentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.gender = gender;
        this.profilePic = profilePic;
        this.createdDate = createdDate;
        this.endDate = endDate;
        this.corpMember = corpCompany;
        this.payments = memberPayment;
        this.memberContacts = memberContacts;
    }
}
