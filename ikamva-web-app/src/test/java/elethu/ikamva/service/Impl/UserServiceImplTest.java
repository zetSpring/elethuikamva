package elethu.ikamva.service.Impl;

import elethu.ikamva.domain.Member;
import elethu.ikamva.domain.Role;
import elethu.ikamva.domain.User;
import elethu.ikamva.repositories.UserRepository;
import elethu.ikamva.service.MemberService;
import elethu.ikamva.service.RoleService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserServiceImpl userService;

    private Role role;
    private User user;
    private  Member member;

//    @BeforeEach
//    void setUp() {
//        role = new Role(1L, "USER");
//        user = new User(1L, "User", "password");
//        member = new Member(1L, Long.parseLong("1006145427081"), "EY012015", "Emihle", "Yawa", DateFormatter.returnLocalDate("2008-04-26"), Gender.FEMALE);
//    }
//
//    @Test
//    void loadUserByUsernameWithNoRoles() {
//        //given
//        given(userRepository.findByUsername(anyString())).willReturn(user);
//
//        //when
//        UserDetails user = userService.loadUserByUsername("User");
//
//        //then
//        verify(userRepository).findByUsername(anyString());
//        assertThat(user).isNotNull();
//    }
//
//    @Test
//    void loadUserByUsernameWithRoles() {
//        //given
//        List<Role> roles = new ArrayList<>();
//        roles.add(role);
//        user.setRoles(roles);
//        given(userRepository.findByUsername(anyString())).willReturn(user);
//
//        //when
//        UserDetails user = userService.loadUserByUsername("User");
//
//        //then
//        verify(userRepository).findByUsername(anyString());
//        assertThat(user).isNotNull();
//        assertThat(user.getAuthorities()).hasSize(1);
//    }
//
//    @Test
//    void registerUserWithNoRoles() {
//        //given
//        given(memberService.findMemberByInvestmentId(anyString())).willReturn(member);
//        given(userRepository.save(any(User.class))).willReturn(user);
//
//        //when
//        User registerUser = userService.registerUser(this.user);
//
//
//        //then
//        verify(memberService).findMemberByInvestmentId(anyString());
//        verify(userRepository).save(any(User.class));
//        assertThat(registerUser).isNotNull();
//    }
//
////    @Test
////    void registerUserWithRoles() {
////        //given
////        List<Role> roles = new ArrayList<>();
////        roles.add(role);
////        user.setRoles(roles);
////        given(memberService.findMemberByInvestmentId(anyString())).willReturn(member);
////        given(userRepository.save(any(User.class))).willReturn(user);
////        given(userRepository.findUserByUsername(anyString())).willReturn(user);
////        given(roleService.findUserByRoleDescription(anyString())).willReturn(role);
////
////        //when
////        User registerUser = userService.registerUser(this.user);
////
////        //then
////        verify(memberService).findMemberByInvestmentId(anyString());
////        verify(userRepository).save(any(User.class));
////        verify(userRepository.findUserByUsername(anyString()));
////        verify(roleService).findUserByRoleDescription(anyString());
////        assertThat(registerUser).isNotNull();
////    }
//
//
//    @Test
//    void deleteUser() {
//        //given
//        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        //when
//        userService.deleteUser(1L);
//
//        //then
//        then(userRepository).should(atLeastOnce()).findById(anyLong());
//        then(userRepository).should(atLeastOnce()).save(any(User.class));
//        assertThat(user.getEndDate()).isNotNull();
//    }
//
//    @Test
//    void addRoleToUser() {
//        //given
//        when(userRepository.findByUsername(anyString())).thenReturn(user);
//        when(userRepository.save(any(User.class))).thenReturn(user);
//        when(roleService.findUserByRoleDescription(anyString())).thenReturn(role);
//
//        //when
//        User user = userService.addRoleToUser("User", "USER");
//
//        //then
//        then(userRepository).should(atLeastOnce()).findByUsername(anyString());
//        then(userRepository).should(atLeastOnce()).save(any(User.class));
//        then(roleService).should(atLeastOnce()).findUserByRoleDescription(anyString());
//        assertThat(user).isNotNull();
//        assertThat(user.getRoles()).hasSize(1);
//    }
//
//    @Test
//    void updateUser() {
//        //given
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        //when
//        User user = userService.updateUser(this.user);
//
//        //then
//        verify(userRepository).save(any(User.class));
//        assertThat(user).isNotNull();
//    }
//
//    @Test
//    void findUserByUsername() {
//        //given
//        given(userRepository.findByUsername(anyString())).willReturn(user);
//
//        //when
//        User user = userService.findUserByUsername("User");
//
//        //then
//        verify(userRepository, atLeastOnce()).findByUsername(anyString());
//        assertThat(user).isNotNull();
//    }
//
//    @Test
//    void findAllUsers() {
//        //given
//        List<User> users = new ArrayList<>();
//        users.add(user);
//        users.add(new User(2L, "Tame", "password"));
//        given(userRepository.findAll()).willReturn(users);
//
//        //when
//        List<User> allUsers = userService.findAllUsers();
//
//        //then
//        verify(userRepository).findAll();
//        assertThat(allUsers).hasSize(2);
//    }
}