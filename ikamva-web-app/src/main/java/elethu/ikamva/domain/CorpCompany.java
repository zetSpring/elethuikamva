package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@Table(name = "IKAMVA_CORPORATIVE", schema = "elethu")
public class CorpCompany implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CORPORATE_ID", nullable = false, updatable = false, length = 10, unique = true)
    private Long id;
    @Column(name = "REGISTRATION_NO", nullable = false, unique = true)
    private String registrationNo;
    @Column(name = "COMPANY_NAME", nullable = false, unique = true)
    private String corpName;
    @Column(name = "REGISTRATION_DATE", nullable = false)
    private String registeredDate;
    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    private LocalDateTime createdDate;
    @Column(name = "END_DATE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "corpCompany", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    @ToString.Exclude
    private List<PrivateCompany> corpPrivateCompany = new LinkedList<>();
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "corpMember", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Member> memberList = new LinkedList<>();

    public CorpCompany(LocalDateTime endDate) {
        this.endDate = endDate;
    }


    public CorpCompany(String registrationNo, String corpName, String registeredDate, LocalDateTime createdDate) {
        this.registrationNo = registrationNo;
        this.corpName = corpName;
        this.registeredDate = registeredDate;
        this.createdDate = createdDate;
    }

    public CorpCompany(Long id, String registrationNo, String corpName, String registeredDate, LocalDateTime createdDate) {
        this.id = id;
        this.registrationNo = registrationNo;
        this.corpName = corpName;
        this.registeredDate = registeredDate;
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CorpCompany that = (CorpCompany) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
