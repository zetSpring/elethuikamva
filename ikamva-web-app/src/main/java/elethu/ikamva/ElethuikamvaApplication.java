package elethu.ikamva;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.dto.UserRegistrationRequestData;
import elethu.ikamva.domain.CorpCompany;
import elethu.ikamva.domain.Member;
import elethu.ikamva.domain.enums.Gender;
import elethu.ikamva.service.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

import static elethu.ikamva.domain.enums.Role.MEMBER;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
public class ElethuikamvaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElethuikamvaApplication.class, args);
    }

    @Bean
    @Profile("dev")
    CommandLineRunner run(
            MemberService memberService,
            CorpCompanyService companyService,
            AuthenticationService authenticationService) {
        return args -> {
            companyService.saveCorpCompany(
                    new CorpCompany(1L, "12345", "Elethu Ikamva", "2022-01-01", DateFormatter.returnLocalDateTime()));

            List<Member> members = new ArrayList<>();
            members.add(new Member(
                    null,
                    Long.parseLong("8507235427081"),
                    "ZY012015",
                    "Zuko",
                    "Yawa",
                    DateFormatter.returnLocalDate(),
                    Gender.MALE));
            members.add(new Member(
                    null,
                    Long.parseLong("8606235917081"),
                    "SM012015",
                    "Siphamandla",
                    "Mnyani",
                    DateFormatter.returnLocalDate(),
                    Gender.MALE));
            members.add(new Member(
                    null,
                    Long.parseLong("8601155950089"),
                    "CM012015",
                    "Chuma",
                    "Mketo",
                    DateFormatter.returnLocalDate(),
                    Gender.MALE));
            members.add(new Member(
                    null,
                    Long.parseLong("9003285877085"),
                    "LG012015",
                    "Lusizo",
                    "Ngqiba",
                    DateFormatter.returnLocalDate(),
                    Gender.MALE));
            memberService.saveAllMembers(members);

            int i1 = Runtime.getRuntime().availableProcessors();
            long i = Runtime.getRuntime().freeMemory();

            log.info("Number of free memory: {}", i);
            log.info("Number of processors: {}", i1);



            authenticationService.registerUser(
                    new UserRegistrationRequestData("ZY012015", "czyawa@gmail.com", "password", MEMBER));
        };
    }
}
