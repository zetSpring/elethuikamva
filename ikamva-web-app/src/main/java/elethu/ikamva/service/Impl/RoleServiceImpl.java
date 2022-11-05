package elethu.ikamva.service.Impl;

import elethu.ikamva.aspects.ExecutionTime;
import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.Role;
import elethu.ikamva.exception.RoleException;
import elethu.ikamva.repositories.RoleRepository;
import elethu.ikamva.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service("roleService")
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    @ExecutionTime
    public Role saveRole(Role role) {
        role.setCreatedDate(DateFormatter.returnLocalDateTime());
        log.info("Creating the role with role description: {}", role.getRoleDescription());
        return roleRepository.save(role);
    }

    @Override
    @ExecutionTime
    public Role updateRole(Role role) {
        var foundRole = roleRepository.findById(role.getId())
                .orElseThrow(() -> new RoleException("Could not find a role to update"));
        foundRole.setRoleDescription(role.getRoleDescription());

        return roleRepository.save(role);
    }

    @Override
    @ExecutionTime
    public Role findUserByRoleDescription(String roleDescription) {
        return Optional.ofNullable(roleRepository.findRoleByRoleDescription(roleDescription))
                .orElseThrow(() -> new RoleException(String.format("Could not find a role with id: %s", roleRepository)));
    }

    @Override
    @ExecutionTime
    public void deleteRole(Long id) {
        var foundRole = roleRepository.findById(id)
                .orElseThrow(() -> new RoleException("Could not find a role to delete"));
        log.info("Role with id: {} was found.", id);
        foundRole.setEndDate(DateFormatter.returnLocalDateTime());
        roleRepository.save(foundRole);
    }

    @Override
    @ExecutionTime
    public List<Role> findAllRoles() {
        return roleRepository.findAll()
                .stream().filter(role -> Objects.isNull(role.getEndDate()))
                .collect(Collectors.toList());
    }
}
