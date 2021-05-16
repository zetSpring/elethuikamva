package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "IKAMVA_CORPORATIVE")
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
    private List<PrivateCompany> corpPrivateCompany = new LinkedList<>();
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "corpMember", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
}
