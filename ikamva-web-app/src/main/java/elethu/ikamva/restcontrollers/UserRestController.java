package elethu.ikamva.restcontrollers;


import elethu.ikamva.domain.User;
import elethu.ikamva.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserRestController {
    private final UserService userService;

    @GetMapping("find/{username}")
    ResponseEntity<User> findUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.findUserByUsername(username), HttpStatus.FOUND);
    }

    @GetMapping("/")
    ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.FOUND);
    }

    @PostMapping("/save")
    ResponseEntity<User> saveNewUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/add-user-role/{username}/{roleDescription}")
    ResponseEntity<User> addRoleToUser(@PathVariable String username, @PathVariable String roleDescription) {
        return new ResponseEntity<>(userService.addRoleToUser(username, roleDescription), HttpStatus.CREATED);
    }

    @PutMapping
    ResponseEntity<User> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.ACCEPTED);
    }
}
