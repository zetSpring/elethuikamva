package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRIVATE_COMPANIES", schema = "elethu")
@JsonPropertyOrder({"id", "companyname"})
public class PrivateCompany implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRIVATE_COM_ID", nullable = false, updatable = false, length = 10, unique = true)
    private Long id;
    @Column(name = "COMPANY_NAME", nullable = false, unique = true)
    private String companyName;
    @Column(name = "REGISTRATION_NO", unique = true)
    private String registrationNo;
    @Column(name = "REGISTRATION_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDate registeredDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "CREATED_DATE", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime createdDate;

    @Column(name = "END_DATE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JoinColumn(name = "CORPORATE_ID_FK", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "CORPORATE_ID_FK", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private CorpCompany corpCompany;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "companyProjects")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Project> projectCompany;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "companyAccount", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Account account;

    public PrivateCompany(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public PrivateCompany(String companyName, String registrationNo, LocalDate registeredDate, LocalDateTime createdDate, CorpCompany corpCompany, List<Project> projectCompany, Account account) {
        this.companyName = companyName;
        this.registrationNo = registrationNo;
        this.registeredDate = registeredDate;
        this.createdDate = createdDate;
        this.corpCompany = corpCompany;
        this.projectCompany = projectCompany;
        this.account = account;
    }

    public PrivateCompany(Long id, String companyName, String registrationNo, LocalDate registeredDate, LocalDateTime createdDate, CorpCompany corpCompany, Account account) {
        this.id = id;
        this.companyName = companyName;
        this.registrationNo = registrationNo;
        this.registeredDate = registeredDate;
        this.createdDate = createdDate;
        this.corpCompany = corpCompany;
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PrivateCompany that = (PrivateCompany) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
