package elethu.ikamva.repositories;

import elethu.ikamva.domain.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findRoleByRoleDescripyion(String name);
}
