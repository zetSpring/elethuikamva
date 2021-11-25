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
@Table(name = "IKAMVA_USERS", schema = "elethu")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID", unique = true, nullable = false, length = 10, updatable = false)
    private Long id;
    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;
    @Column(name = "PASSWORD", nullable = false, unique = true)
    private String password;
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;
    @Column(name = "END_DATE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID_FK", nullable = false)
    @ToString.Exclude
    private Role role;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEMBER_ID_PK", nullable = false)
    private Member userMember;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
