package elethu.ikamva.controllers;

import elethu.ikamva.domain.PrivateCompany;
import elethu.ikamva.service.PrivateCompanyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private-company")
@SecurityRequirement(name = "bearerAuth")
public class PrivateCompanyRestController {
    private final PrivateCompanyService privateCompanyService;

    @GetMapping("/{id}")
    ResponseEntity<PrivateCompany> findPrivateCompany(@PathVariable Long id) {
        var privateAccount = privateCompanyService.findPrivateCompanyById(id);
        return new ResponseEntity<>(privateAccount, HttpStatus.OK);
    }

    @GetMapping("/")
    List<PrivateCompany> findAllPrivateCompanies() {
        return privateCompanyService.findAllPrivateCompany();
    }
}
