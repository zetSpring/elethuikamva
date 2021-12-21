package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "IKAMVA_MEMBERS", schema = "elethu")
@JsonPropertyOrder({"id", "firstName", "lastName", "investmentId", "dob", "identityNo", "gender", "createdDate", "memberContacts", "payments", "noOfPayments","totalPayments","user"})
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
    private String firstName;
    @Column(name = "MEMBER_SURNAME", nullable = false)
    private String lastName;
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dob;  /*member date of birth*/
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
    @ToString.Exclude
    private CorpCompany corpMember;
    @OneToMany(mappedBy = "memberPayments", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Payment> payments = new LinkedList<>();
    @OneToMany(mappedBy = "members", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @ToString.Exclude
    private List<ContactDetails> memberContacts = new LinkedList<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "userMember")
    private User user;

    /*update profile picture*/
    public Member(Byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public Member(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public Member(Long identityNo, String investmentId, String firstName, String lastName, LocalDate dob, String gender) {
        this.identityNo = identityNo;
        this.investmentId = investmentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
    }

    public Member(Long memberIdentityNo, String investmentId, String firstName, String lastName, LocalDate dob, String gender, OffsetDateTime createdDate, CorpCompany corpCompany, List<Payment> memberPayment, List<ContactDetails> memberContacts) {
        this.identityNo = memberIdentityNo;
        this.investmentId = investmentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.createdDate = createdDate;
        this.corpMember = corpCompany;
        this.payments = memberPayment;
        this.memberContacts = memberContacts;
    }

    public Member(Long memberIdentityNo, String investmentId, String firstName, String lastName, LocalDate dob, String gender, Byte[] profilePic, OffsetDateTime createdDate, OffsetDateTime endDate, CorpCompany corpCompany, List<Payment> memberPayment, List<ContactDetails> memberContacts) {
        this.identityNo = memberIdentityNo;
        this.investmentId = investmentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.profilePic = profilePic;
        this.createdDate = createdDate;
        this.endDate = endDate;
        this.corpMember = corpCompany;
        this.payments = memberPayment;
        this.memberContacts = memberContacts;
    }

    public int getNoOfPayments(){
        int noOfPayments = 0;
        if(!payments.isEmpty()){
            noOfPayments = payments.size();
        }
        return noOfPayments;
    }

    public Double getTotalPayments(){
        Double total = 0.0;
        if(!payments.isEmpty()){
            total = payments.stream()
                    .filter(payment -> payment.getTransactionType().equals(TransactionType.MONTHLY_CONTRIBUTION))
                    .map(Payment::getAmount)
                    .reduce(0.0, Double::sum);
        }
        return total;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Member member = (Member) o;
        return id != null && Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
