package elethu.ikamva;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.*;
import elethu.ikamva.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ElethuikamvaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElethuikamvaApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService, MemberService memberService, CorpCompanyService companyService
            , PaymentService paymentService) {
        return args -> {

            companyService.saveCorpCompany(new CorpCompany(1L, "12345", "Elethu Ikamva", "2022-01-01", DateFormatter.returnLocalDateTime()));

            List<Member> members = new ArrayList<>();
            members.add(new Member(null, Long.parseLong("8507235427081"), "ZY012015", "Zuko", "Yawa", DateFormatter.returnLocalDate(), "Male"));
            members.add(new Member(null, Long.parseLong("8606235917081"), "SM012015", "Siphamandla", "Mnyani", DateFormatter.returnLocalDate(), "Male"));
            memberService.saveAllMembers(members);

            paymentService.savePayment(new Payment(1500.0d, "ZY012015", DateFormatter.returnLocalDate().minusDays(10), "ZY012015"));

            userService.registerUser(new User(null, "ZY012015", "password"));
            userService.registerUser(new User(null, "SM012015", "password"));

            roleService.saveRole(new Role(null, "USER"));
            roleService.saveRole(new Role(null, "SYS_ADMIN"));


            userService.addRoleToUser("ZY012015", "USER");
            userService.addRoleToUser("ZY012015", "SYS_ADMIN");

            userService.addRoleToUser("SM012015", "USER");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
