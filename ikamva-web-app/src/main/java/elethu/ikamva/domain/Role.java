package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "MEMBER_ROLES", schema = "elethu")
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date endDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private Set<User> userRoles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
