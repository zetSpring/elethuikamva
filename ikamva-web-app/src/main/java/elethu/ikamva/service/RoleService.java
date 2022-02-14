package elethu.ikamva.service;

import elethu.ikamva.domain.Role;

import java.util.List;

public interface RoleService {
    Role saveRole(Role role);
    Role updateRole(Role role);
    Role findUserByRoleDescription(String roleDescription);
    void deleteRole(Long id);
    List<Role> findAllRoles();
}
