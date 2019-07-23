package elethu.ikamva.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "IKAMVA_MEMBERS")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID", unique = true, updatable = false, length = 10)
    private Long id;
    @Column(name = "IDENTITY_NAME", nullable = false, unique = true, length = 13)
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
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "CORPORATE_ID_FK", nullable = false)
    private CorpCompany corpCompany;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberPayment")
    private Set<Payment> payments = new HashSet<>();
    @OneToMany(mappedBy = "members", cascade = CascadeType.REMOVE)
    private Set<ContactDetails> memberContacts = new HashSet<>();
    @OneToOne(mappedBy = "userMember")
    private User user;

    public Member() {
    }

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
        this.corpCompany = corpCompany;
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
        this.corpCompany = corpCompany;
        this.payments = memberPayment;
        this.memberContacts = memberContacts;
    }


    public Set<Payment> getMemberPayment() {
        return payments;
    }

    public void setMemberPayment(Set<Payment> memberPayment) {
        this.payments = memberPayment;
    }

    public Long getId() {
        return this.id;
    }

    public Long getMemberIdentityNo() {
        return this.memberIdentityNo;
    }

    public String getInvestmentId() {
        return this.investmentId;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public Date getDob() {
        return this.dob;
    }

    public String getGender() {
        return this.gender;
    }

    public Byte[] getProfilePic() {
        return this.profilePic;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public CorpCompany getCorpCompany() {
        return this.corpCompany;
    }

    public Set<Payment> getPayments() {
        return this.payments;
    }

    public Set<ContactDetails> getMemberContacts() {
        return this.memberContacts;
    }

    public User getUser() {
        return this.user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMemberIdentityNo(Long memberIdentityNo) {
        this.memberIdentityNo = memberIdentityNo;
    }

    public void setInvestmentId(String investmentId) {
        this.investmentId = investmentId;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setProfilePic(Byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setCorpCompany(CorpCompany corpCompany) {
        this.corpCompany = corpCompany;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public void setMemberContacts(Set<ContactDetails> memberContacts) {
        this.memberContacts = memberContacts;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Member)) return false;
        final Member other = (Member) o;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$memberIdentityNo = this.getMemberIdentityNo();
        final Object other$memberIdentityNo = other.getMemberIdentityNo();
        if (this$memberIdentityNo == null ? other$memberIdentityNo != null : !this$memberIdentityNo.equals(other$memberIdentityNo))
            return false;
        final Object this$investmentId = this.getInvestmentId();
        final Object other$investmentId = other.getInvestmentId();
        if (this$investmentId == null ? other$investmentId != null : !this$investmentId.equals(other$investmentId))
            return false;
        final Object this$firstname = this.getFirstname();
        final Object other$firstname = other.getFirstname();
        if (this$firstname == null ? other$firstname != null : !this$firstname.equals(other$firstname)) return false;
        final Object this$lastname = this.getLastname();
        final Object other$lastname = other.getLastname();
        if (this$lastname == null ? other$lastname != null : !this$lastname.equals(other$lastname)) return false;
        final Object this$dob = this.getDob();
        final Object other$dob = other.getDob();
        if (this$dob == null ? other$dob != null : !this$dob.equals(other$dob)) return false;
        final Object this$gender = this.getGender();
        final Object other$gender = other.getGender();
        if (this$gender == null ? other$gender != null : !this$gender.equals(other$gender)) return false;
        if (!Arrays.deepEquals(this.getProfilePic(), other.getProfilePic())) return false;
        final Object this$createdDate = this.getCreatedDate();
        final Object other$createdDate = other.getCreatedDate();
        if (this$createdDate == null ? other$createdDate != null : !this$createdDate.equals(other$createdDate))
            return false;
        final Object this$endDate = this.getEndDate();
        final Object other$endDate = other.getEndDate();
        if (this$endDate == null ? other$endDate != null : !this$endDate.equals(other$endDate)) return false;
        final Object this$corpCompany = this.getCorpCompany();
        final Object other$corpCompany = other.getCorpCompany();
        if (this$corpCompany == null ? other$corpCompany != null : !this$corpCompany.equals(other$corpCompany))
            return false;
        final Object this$payments = this.getPayments();
        final Object other$payments = other.getPayments();
        if (this$payments == null ? other$payments != null : !this$payments.equals(other$payments)) return false;
        final Object this$memberContacts = this.getMemberContacts();
        final Object other$memberContacts = other.getMemberContacts();
        if (this$memberContacts == null ? other$memberContacts != null : !this$memberContacts.equals(other$memberContacts))
            return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        return this$user == null ? other$user == null : this$user.equals(other$user);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Member;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $memberIdentityNo = this.getMemberIdentityNo();
        result = result * PRIME + ($memberIdentityNo == null ? 43 : $memberIdentityNo.hashCode());
        final Object $investmentId = this.getInvestmentId();
        result = result * PRIME + ($investmentId == null ? 43 : $investmentId.hashCode());
        final Object $firstname = this.getFirstname();
        result = result * PRIME + ($firstname == null ? 43 : $firstname.hashCode());
        final Object $lastname = this.getLastname();
        result = result * PRIME + ($lastname == null ? 43 : $lastname.hashCode());
        final Object $dob = this.getDob();
        result = result * PRIME + ($dob == null ? 43 : $dob.hashCode());
        final Object $gender = this.getGender();
        result = result * PRIME + ($gender == null ? 43 : $gender.hashCode());
        result = result * PRIME + Arrays.deepHashCode(this.getProfilePic());
        final Object $createdDate = this.getCreatedDate();
        result = result * PRIME + ($createdDate == null ? 43 : $createdDate.hashCode());
        final Object $endDate = this.getEndDate();
        result = result * PRIME + ($endDate == null ? 43 : $endDate.hashCode());
        final Object $corpCompany = this.getCorpCompany();
        result = result * PRIME + ($corpCompany == null ? 43 : $corpCompany.hashCode());
        final Object $payments = this.getPayments();
        result = result * PRIME + ($payments == null ? 43 : $payments.hashCode());
        final Object $memberContacts = this.getMemberContacts();
        result = result * PRIME + ($memberContacts == null ? 43 : $memberContacts.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        return result;
    }

    public String toString() {
        return "Member(id=" + this.getId() + ", memberIdentityNo=" + this.getMemberIdentityNo() + ", investmentId=" + this.getInvestmentId() + ", firstname=" + this.getFirstname() + ", lastname=" + this.getLastname() + ", dob=" + this.getDob() + ", gender=" + this.getGender() + ", profilePic=" + Arrays.deepToString(this.getProfilePic()) + ", createdDate=" + this.getCreatedDate() + ", endDate=" + this.getEndDate() + ", corpCompany=" + this.getCorpCompany() + ", payments=" + this.getPayments() + ", memberContacts=" + this.getMemberContacts() + ", user=" + this.getUser() + ")";
    }
}
