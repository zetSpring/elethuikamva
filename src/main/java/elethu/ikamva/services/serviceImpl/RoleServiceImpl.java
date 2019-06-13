package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.domain.Role;
import elethu.ikamva.exception.RoleException;
import elethu.ikamva.repositories.RoleRepository;
import elethu.ikamva.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveOrUpdateRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(Role role) {
        if (isRoleActive(role))
            saveOrUpdateRole(role);
        else
            throw new RoleException("Role: " + role.getRoleDescripyion() + " could not be found for deletion");
    }

    @Override
    public Role findRoleById(Long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);

        if (!roleOptional.isPresent()) {
            throw new RoleException("Role: " + id + " could not be found");
        }
        return roleOptional.get();
    }

    @Override
    public Role findRoleByDescription(String description) {
        Optional<Role> roleOptional = roleRepository.findRoleByRoleDescripyion(description);
        if (!roleOptional.isPresent()) {
            throw new RoleException("Role: " + description + " could not be found");
        }
        return roleOptional.get();
    }

    @Override
    public Set<Role> findAllRoles() {
        Set<Role> role = new HashSet<>();
        roleRepository.findAll().iterator().forEachRemaining(role::add);
        return role;
    }

    @Override
    public Boolean isRoleActive(Role role) {
        return role.getId() != null && role.getEndDate() == null;
    }
}
