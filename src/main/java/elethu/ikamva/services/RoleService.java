package elethu.ikamva.services;

import elethu.ikamva.domain.Role;

import java.util.Set;

public interface RoleService {
    void saveOrUpdateRole(Role role);

    void deleteRole(Role role);

    Role findRoleById(Long id);

    Role findRoleByDescription(String description);

    Set<Role> findAllRoles();

    Boolean isRoleActive(Role role);
}
