package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CONTACT_DETAILS")
@EqualsAndHashCode(of = {""})
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private OffsetDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OffsetDateTime endDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "MEMBER_ID_FK", nullable = false)
    private Member members;

    public ContactDetails(String contact, String contactType, OffsetDateTime createdDate, Member member) {
        this.contact = contact;
        this.contactType = contactType;
        this.createdDate = createdDate;
        this.members = member;
    }

    public ContactDetails(OffsetDateTime endDate) {
        this.endDate = endDate;
    }
}



