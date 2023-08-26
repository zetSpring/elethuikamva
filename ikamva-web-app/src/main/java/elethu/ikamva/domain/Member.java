package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.*;
import elethu.ikamva.domain.enums.Gender;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
//@Builder
@AllArgsConstructor
@Entity
@Table(name = "IKAMVA_MEMBERS", schema = "elethu")
@JsonPropertyOrder({"id", "firstName", "lastName", "investmentId", "dob", "identityNo", "gender", "createdDate", "memberContacts", "user"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Member implements Serializable {
    @Serial
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
    private Gender gender;
    @Lob
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Byte[] profilePic;
    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime createdDate;
    @Column(name = "END_DATE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "CORPORATE_ID_FK", nullable = false)
    @ToString.Exclude
    private CorpCompany corpMember;
    @JsonIgnore
    @OneToMany(mappedBy = "memberPayments", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Payment> payments = new ArrayList<>();
    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "members", cascade = CascadeType.ALL)
    //@LazyCollection(LazyCollectionOption.FALSE)
    @ToString.Exclude
    private List<ContactDetails> memberContacts = new ArrayList<>();

    //@ToString.Exclude
   // @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonManagedReference
    @OneToOne(mappedBy = "userMember" , cascade = CascadeType.ALL)
    private User user;

    /*update profile picture*/
    public Member(Byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public Member(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Member(Long id, Long identityNo, String investmentId, String firstName, String lastName, LocalDate dob, Gender gender) {
        this.id = id;
        this.identityNo = identityNo;
        this.investmentId = investmentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
    }

    public Member(Long memberIdentityNo, String investmentId, String firstName, String lastName, LocalDate dob, Gender gender, LocalDateTime createdDate, CorpCompany corpCompany, List<Payment> memberPayment, List<ContactDetails> memberContacts) {
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
