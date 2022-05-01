package elethu.ikamva.controllers;

import elethu.ikamva.domain.CorpCompany;
import elethu.ikamva.service.CorpCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CorporateCompanyRestController {
    private final CorpCompanyService corpCompanyService;

    @PostMapping("/")
    ResponseEntity<CorpCompany> addNewCorpCompany(@RequestBody CorpCompany newCorpCompany){
        CorpCompany corpCompany = corpCompanyService.saveCorpCompany(newCorpCompany);
        return new ResponseEntity<>(corpCompany, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<CorpCompany> deleteCorpCompany(@PathVariable Long id){
        CorpCompany corpCompany = corpCompanyService.deleteCorpCompany(id);
        return new ResponseEntity<>(corpCompany, HttpStatus.OK);
    }

    @GetMapping("/")
    ResponseEntity<CorpCompany> findCorporate(){
        CorpCompany corp =  corpCompanyService.findCorpCompany();
        return new ResponseEntity<>(corp, HttpStatus.OK);
    }
}
