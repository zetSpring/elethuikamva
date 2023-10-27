package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import elethu.ikamva.domain.enums.ContactType;
import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "CONTACT_DETAILS", schema = "elethu")
public class ContactDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CONTACT_ID", nullable = false, updatable = false, unique = true, length = 10)
    private Long id;

    @Column(name = "CONTACT", nullable = false, unique = true)
    private String contact;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONTACT_TYPE", nullable = false)
    private ContactType contactType;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String memberInvestId;

    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "END_DATE")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "MEMBER_ID_FK", nullable = false)
    private Member members;

    public ContactDetails(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ContactDetails(
            Long id, String contact, ContactType contactType, String memberInvestId, LocalDate createdDate) {
        this.id = id;
        this.contact = contact;
        this.contactType = contactType;
        this.memberInvestId = memberInvestId;
        this.createdDate = createdDate;
    }

    public ContactDetails(
            String contact, ContactType contactType, String memberInvestId, LocalDate createdDate, Member members) {
        this.contact = contact;
        this.contactType = contactType;
        this.memberInvestId = memberInvestId;
        this.createdDate = createdDate;
        this.members = members;
    }

    public ContactDetails(
            Long id,
            String contact,
            ContactType contactType,
            String memberInvestId,
            LocalDate createdDate,
            Member members) {
        this.id = id;
        this.contact = contact;
        this.contactType = contactType;
        this.memberInvestId = memberInvestId;
        this.createdDate = createdDate;
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ContactDetails that = (ContactDetails) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
