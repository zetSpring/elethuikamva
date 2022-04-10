package elethu.ikamva.service.Impl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.User;
import elethu.ikamva.exception.UserException;
import elethu.ikamva.repositories.UserRepository;
import elethu.ikamva.service.MemberService;
import elethu.ikamva.service.RoleService;
import elethu.ikamva.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MemberService memberService;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = Optional.ofNullable(userRepository.findUserByUsername(username))
                .orElseThrow(() -> new UserException("Could not find user to authenticate"));

        log.info("User found on the database: {}", username);
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role ->
                    authorities.add(new SimpleGrantedAuthority(role.getRoleDescription())));
        } else {
            log.info("The user has no roles, cannot authenticate.");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User registerUser(User newUser) {
        var member = memberService.findMemberByInvestmentId(newUser.getUsername());
        newUser.setUserMember(member);
        newUser.setCreatedDate(DateFormatter.returnLocalDateTime());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

//        if (!CollectionUtils.isEmpty(newUser.getRoles())) {
//            newUser.getRoles().forEach(role -> {
//                this.addRoleToUser(newUser.getUsername(), role.getRoleDescription());
//            });
//        }

        log.info("Created the user with username: {}", newUser.getUsername());
        return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(() ->
                new UserException(String.format("User with id %s was not found, please correct.", id)));
        log.info("User with an id: {} was found and will be deleted", id);

        user.setEndDate(DateFormatter.returnLocalDateTime());
        userRepository.save(user);
    }

    @Override
    public User addRoleToUser(String username, String roleDescription) {
        var user = Optional.ofNullable(userRepository.findUserByUsername(username))
                .orElseThrow(() -> new UserException(String.format("Could not find a user with username: %s", username)));
        var role = roleService.findUserByRoleDescription(roleDescription);
        user.getRoles().add(role);
        log.info("Adding role: {} to user: {} current roles: {}", roleDescription, username, user.getRoles());

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findUserByUsername(username))
                .orElseThrow(() -> new UserException(String.format("Could not find user with username: %s", username)));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll().stream().filter(user -> Objects.isNull(user.getEndDate())).collect(Collectors.toList());
    }
}
