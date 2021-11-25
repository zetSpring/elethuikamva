package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "IKAMVA_PROJECTS", schema = "elethu")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PROJECT_ID", nullable = false, updatable = false, length = 10, unique = true)
    private Long id;
    @Column(name = "PROJECT_NAME", nullable = false)
    private String name;
    @Column(name = "PROJECT_DESCRIPTION", nullable = false)
    private String projectDescription;
    @Column(name = "PROJECT_CREATED_DATE", nullable = false)
    private Date startDate;
    @Column(name = "PROJECT_END_DATE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRIVATE_COMPANY_ID_FK", nullable = false)
    @ToString.Exclude
    private PrivateCompany companyProjects;

    public Project(Date endDate) {
        this.endDate = endDate;
    }

    public Project(String name, String projectDescription, Date startDate, Date endDate, PrivateCompany companyProjects) {
        this.name = name;
        this.projectDescription = projectDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.companyProjects = companyProjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
