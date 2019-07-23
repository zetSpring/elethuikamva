package elethu.ikamva.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "IKAMVA_PROJECTS")
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
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "PRIVATE_COMPANY_ID_FK", nullable = false)
    private PrivateCompany companyProjects;

    public Project() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public PrivateCompany getCompanyProjects() {
        return companyProjects;
    }

    public void setCompanyProjects(PrivateCompany companyProjects) {
        this.companyProjects = companyProjects;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Role)) {
            return false;
        }
        Project otherProject = (Project) object;
        {
            return (this.id != null || otherProject.id == null) && (this.id == null || this.id.equals(otherProject.id));
        }
    }

    @Override
    public String toString() {
        return "elethu.ikamva.domain.Project[id=" + id + "]";
    }
}
