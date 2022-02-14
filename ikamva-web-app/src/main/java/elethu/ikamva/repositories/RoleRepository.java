package elethu.ikamva.repositories;

import elethu.ikamva.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRoleDescription(String roleDescription);
}
