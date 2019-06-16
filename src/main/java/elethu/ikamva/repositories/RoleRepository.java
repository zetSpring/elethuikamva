package elethu.ikamva.repositories;

import elethu.ikamva.domain.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findRoleByRoleDescription(String name);
    @Query("SELECT r FROM Role r WHERE r.endDate = null")
    Set<Role> findAllActiveRoles();
}
