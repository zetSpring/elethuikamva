package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.CorpCompany;
import elethu.ikamva.services.CorpCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/company")
public class CorporateCompanyRestController {
    @Autowired
    private CorpCompanyService corpCompanyService;

    @PostMapping("/add")
    ResponseEntity<CorpCompany> addNewCorpCompany(@RequestBody CorpCompany newCorpCompany){
        CorpCompany corpCompany = corpCompanyService.createCorpCompany(newCorpCompany);
        return new ResponseEntity<>(corpCompany, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<CorpCompany> deleteCorpCompany(Long id){
        CorpCompany corpCompany = corpCompanyService.deleteCorpCompany(id);
        return new ResponseEntity<>(corpCompany, HttpStatus.OK);
    }

    @GetMapping("/")
    ResponseEntity<CorpCompany> findCorporate(){
        CorpCompany corp =  corpCompanyService.findCorpCompany();
        return new ResponseEntity<>(corp, HttpStatus.OK);
    }
}
