package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
@NoArgsConstructor
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
    private Date createdCreated;
    @Column(name = "END_DATE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date endDate;

    @OneToMany(mappedBy = "corpCompany", cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    private Set<PrivateCompany> corpPrivateCompany = new HashSet<>();
    @OneToMany(mappedBy = "corpMember", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Member> memberSet = new HashSet<>();

    public CorpCompany(Date endDate) {
        this.endDate = endDate;
    }

    public CorpCompany(String registrationNo, String corpName, String registeredDate, Date createdCreated) {
        this.registrationNo = registrationNo;
        this.corpName = corpName;
        this.registeredDate = registeredDate;
        this.createdCreated = createdCreated;
    }
   }
