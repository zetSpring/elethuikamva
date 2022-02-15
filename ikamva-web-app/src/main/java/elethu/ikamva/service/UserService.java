package elethu.ikamva.service;

import elethu.ikamva.domain.User;

import java.util.List;

public interface UserService {
    User registerUser(User newUser);
    User updateUser(User user);
    User findUserByUsername(String username);
    List<User> findAllUsers();
    void deleteUser(Long id);
    User addRoleToUser(String username, String Role);
}
