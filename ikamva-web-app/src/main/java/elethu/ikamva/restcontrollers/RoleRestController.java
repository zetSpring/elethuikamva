package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.Role;
import elethu.ikamva.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/role")
public class RoleRestController {
    private final RoleService roleService;

    @PostMapping("/save")
    ResponseEntity<Role> saveRole(@RequestBody Role role) {
        var saveRole = roleService.saveRole(role);

        return new ResponseEntity<>(saveRole, HttpStatus.CREATED);
    }

    @GetMapping("/")
    ResponseEntity<List<Role>> findAllRoles(){
        return new ResponseEntity<>(roleService.findAllRoles(), HttpStatus.FOUND);
    }

    @GetMapping("/find/{username}")
    ResponseEntity<Role> findRoleByRoelDescription(@PathVariable String username) {
        return new ResponseEntity<>(roleService.findUserByRoleDescription(username), HttpStatus.FOUND);
    }

    @PutMapping("/update")
    ResponseEntity<Role> updateRole(@RequestBody Role role) {
        return new ResponseEntity<>(roleService.updateRole(role), HttpStatus.ACCEPTED);
    }
}
