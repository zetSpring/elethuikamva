package elethu.ikamva.controllers;


import elethu.ikamva.domain.User;
import elethu.ikamva.service.IkamvaTokensService;
import elethu.ikamva.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserRestController {
    private final UserService userService;
    private final IkamvaTokensService tokensService;

    @GetMapping("/find/{username}")
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

    @PutMapping("/update")
    ResponseEntity<User> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.ACCEPTED);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        tokensService.getAccessToken(request, response);
    }
}
