package elethu.ikamva;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.CorpCompany;
import elethu.ikamva.domain.Member;
import elethu.ikamva.domain.Role;
import elethu.ikamva.domain.User;
import elethu.ikamva.domain.enums.Gender;
import elethu.ikamva.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
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
    @Profile("dev")
    CommandLineRunner run(UserService userService, RoleService roleService, MemberService memberService, CorpCompanyService companyService
            , PaymentService paymentService) {
        return args -> {

            companyService.saveCorpCompany(new CorpCompany(1L, "12345", "Elethu Ikamva", "2022-01-01", DateFormatter.returnLocalDateTime()));

            List<Member> members = new ArrayList<>();
            members.add(new Member(null, Long.parseLong("8507235427081"), "ZY012015", "Zuko", "Yawa", DateFormatter.returnLocalDate(), Gender.MALE));
            members.add(new Member(null, Long.parseLong("8606235917081"), "SM012015", "Siphamandla", "Mnyani", DateFormatter.returnLocalDate(), Gender.MALE));
            members.add(new Member(null, Long.parseLong("8601155950089"), "CM012015", "Chuma", "Mketo", DateFormatter.returnLocalDate(), Gender.MALE));
            members.add(new Member(null, Long.parseLong("9003285877085"), "LG012015", "Lusizo", "Ngqiba", DateFormatter.returnLocalDate(), Gender.MALE));
            members.add(new Member(null, Long.parseLong("8707050199084"), "NT012015", "Nomaxabiso", "Tata", DateFormatter.returnLocalDate(), Gender.FEMALE));
            members.add(new Member(null, Long.parseLong("9006216168086"), "MM012015", "Mmeli", "Mnyani", DateFormatter.returnLocalDate(), Gender.MALE));
            members.add(new Member(null, Long.parseLong("8211095735085"), "BN012015", "Bonginkosi", "Ngcaweni", DateFormatter.returnLocalDate(), Gender.MALE));
            members.add(new Member(null, Long.parseLong("8208237501084"), "LWG012015", "Lwandile", "Gagela ", DateFormatter.returnLocalDate(), Gender.MALE));
            members.add(new Member(null, Long.parseLong("7911245300083"), "SG012015", "Simphiwe", "Gxashe", DateFormatter.returnLocalDate(), Gender.MALE));
            members.add(new Member(null, Long.parseLong("8903165773083"), "XP012015", "Xolisani", "Pato", DateFormatter.returnLocalDate(), Gender.MALE));
            memberService.saveAllMembers(members);

            //paymentService.savePayment(new Payment(1500.0d, "ZY012015", DateFormatter.returnLocalDate().minusDays(10), "ZY012015"));

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
