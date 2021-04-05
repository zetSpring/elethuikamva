package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.*;

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
    private Long identityNo;
    @Column(name = "INVESTMENT_ID", nullable = false, unique = true)
    private String investmentId;
    @Column(name = "MEMBER_NAME", nullable = false)
    private String firstname;
    @Column(name = "MEMBER_SURNAME", nullable = false)
    private String lastname;
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private OffsetDateTime dob;  /*member date of birth*/
    @Column(name = "GENDER", nullable = false)
    private String gender;
    @Lob
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Byte[] profilePic;
    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private OffsetDateTime createdDate;
    @Column(name = "END_DATE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private OffsetDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "CORPORATE_ID_FK", nullable = false)
    private CorpCompany corpMember;
    @OneToMany(mappedBy = "memberPayments", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Payment> payments = new LinkedList<>();
    @OneToMany(mappedBy = "members", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ContactDetails> memberContacts = new LinkedList<>();
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "userMember")
    private User user;

    /*update profile picture*/
    public Member(Byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public Member(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public Member(Long memberIdentityNo, String investmentId, String firstname, String lastname, OffsetDateTime dob, String gender, OffsetDateTime createdDate, CorpCompany corpCompany, List<Payment> memberPayment, List<ContactDetails> memberContacts) {
        this.identityNo = memberIdentityNo;
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

    public Member(Long memberIdentityNo, String investmentId, String firstname, String lastname, OffsetDateTime dob, String gender, Byte[] profilePic, OffsetDateTime createdDate, OffsetDateTime endDate, CorpCompany corpCompany, List<Payment> memberPayment, List<ContactDetails> memberContacts) {
        this.identityNo = memberIdentityNo;
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
