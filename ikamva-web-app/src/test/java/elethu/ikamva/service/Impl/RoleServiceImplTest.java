package elethu.ikamva.service.Impl;

import elethu.ikamva.domain.Role;
import elethu.ikamva.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role(1L, "ADMIN");
    }

    @Test
    @DisplayName("Save a User Role - Test")
    void saveRole() {
        //given
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        //when
        Role saveRole = roleService.saveRole(this.role);

        //then
        verify(roleRepository).save(any(Role.class));
        assertThat(saveRole).isNotNull();
    }

    @Test
    @DisplayName("Update role ")
    void updateRole() {
        //given
        Role roleUpdate = new Role(1L, "SYS_ADMIN");
        given(roleRepository.findById(anyLong())).willReturn(Optional.of(role));
        given(roleRepository.save(any(Role.class))).willReturn(role);

        //when
        Role updateRole = roleService.updateRole(roleUpdate);

        //then
        then(roleRepository).should(atLeastOnce()).findById(anyLong());
        assertThat(updateRole.getId()).isEqualTo(1L);
        assertThat(updateRole.getRoleDescription()).isEqualTo(roleUpdate.getRoleDescription());
    }

    @Test
    @DisplayName("Find Role By Description - Test")
    void findUserByRoleDescription() {
        //given
        given(roleRepository.findRoleByRoleDescription(anyString())).willReturn(role);

        //when
        Role roleByDescription = roleService.findUserByRoleDescription("ADMIN");

        //then
        verify(roleRepository).findRoleByRoleDescription(anyString());
        assertThat(roleByDescription).isNotNull();
    }

    @Test
    @DisplayName("Delete Role By Id - Test")
    void deleteRole() {
        //given
        given(roleRepository.findById(anyLong())).willReturn(Optional.of(role));

        //when
        roleService.deleteRole(1L);

        //then
        verify(roleRepository, atLeastOnce()).findById(anyLong());
        verify(roleRepository, atLeastOnce()).save(any(Role.class));
    }

    @Test
    @DisplayName("Find All Roles")
    void findAllRoles() {
        //given
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        roles.add(new Role(2L, "SYS_ADMIN"));
        when(roleRepository.findAll()).thenReturn(roles);

        //when
        List<Role> allRoles = roleService.findAllRoles();

        //then
        then(roleRepository).should(atLeastOnce()).findAll();
        assertThat(allRoles).hasSize(2);
    }
}