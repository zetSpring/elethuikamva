package elethu.ikamva.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "MEMBER_ROLES")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_ID", unique = true, nullable = false, length = 10, updatable = false)
    private Long id;
    @Column(name = "ROLE_DESCRIPTION", unique = true, nullable = false)
    private String roleDescription;
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;
    @Column(name = "END_DATE")
    private Date endDate;

    @OneToMany(mappedBy = "role")
    private Set<User> userRoles = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
        Role otherRole = (Role) object;
        {
            return (this.id != null || otherRole.id == null) && (this.id == null || this.id.equals(otherRole.id));
        }
    }

    @Override
    public String toString() {
        return "elethu.ikamva.domain.Role[id=" + id + "]";
    }
}
