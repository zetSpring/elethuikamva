package elethu.ikamva.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "IKAMVA_USERS")
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

    @ManyToOne
    @JoinColumn(name = "ROLE_ID_FK", nullable = false)
    private Role role;
    @OneToOne
    @JoinColumn(name = "MEMBER_ID_PK", nullable = false)
    private Member userMember;

}
