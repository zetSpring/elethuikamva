package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date endDate;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "MEMBER_ID_FK", nullable = false)
    private Member members;

    public ContactDetails(String contact, String contactType, Date createdDate, Member member) {
        this.contact = contact;
        this.contactType = contactType;
        this.createdDate = createdDate;
        this.members = member;
    }

    public ContactDetails(Date endDate) {
        this.endDate = endDate;
    }
}



