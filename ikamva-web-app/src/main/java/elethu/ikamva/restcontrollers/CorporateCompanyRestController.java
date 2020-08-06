package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.CorpCompany;
import elethu.ikamva.exception.ResourceNotFoundException;
import elethu.ikamva.services.CorpCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/corporate")
public class CorporateCompanyRestController {
    @Autowired
    private CorpCompanyService corpCompanyService;

    @GetMapping("/corporates")
    Set<CorpCompany> findAllCorps() throws ResourceNotFoundException {
        Set<CorpCompany> corpCompanySet = corpCompanyService.findAllCorpCompany();
        if(!corpCompanySet.isEmpty()){
            return corpCompanySet;
        }else
            throw new ResourceNotFoundException("There are no corporate companies found to display.");
    }

}
