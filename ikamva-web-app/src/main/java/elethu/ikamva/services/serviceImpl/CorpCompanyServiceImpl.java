package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.CorpCompany;
import elethu.ikamva.exception.CorpCompanyException;
import elethu.ikamva.repositories.CorpCompanyRepository;
import elethu.ikamva.services.CorpCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CorpCompanyServiceImpl implements CorpCompanyService {

    private final CorpCompanyRepository corpCompanyRepository;
    private final DateFormatter dateFormatter;

    @Override
    public CorpCompany createCorpCompany(CorpCompany newCorpCompany) {
        if(!isCorporateActive(newCorpCompany.getRegistrationNo())){
           // CorpCompany corpCompany = newCorpCompany;
            newCorpCompany.setCreatedDate(dateFormatter.returnLocalDateTime());
            return corpCompanyRepository.save(newCorpCompany);

            //return corpCompany;
        }else {
            throw new CorpCompanyException("The corporate company with the registration no: " + newCorpCompany.getRegistrationNo() + " already exists");
        }
    }

    @Override
    public CorpCompany updateCorpCompany(CorpCompany updateCorpCompany) {
        return null;
    }


    @Override
    public CorpCompany deleteCorpCompany(Long id) {
        Optional<CorpCompany> corpCompany = corpCompanyRepository.findById(id);
        CorpCompany deleteCorpCompany = corpCompany.orElseThrow(() -> new CorpCompanyException("Could not find corporate company id: " + id + " to delete."));
        deleteCorpCompany.setEndDate(dateFormatter.returnLocalDateTime());
        return corpCompanyRepository.save(deleteCorpCompany);
    }

    @Override
    public List<CorpCompany> findAllCorpCompany() {
        List<CorpCompany> corpCompaniesSet = new LinkedList<>();
        corpCompanyRepository.findAll().iterator().forEachRemaining(corpCompaniesSet::add);

        return corpCompaniesSet;
    }

    @Override
    public CorpCompany findCorpCompany() {
        Optional<CorpCompany> corpCompanyOptional = corpCompanyRepository.findCorpCompany();
        return corpCompanyOptional.orElseThrow(() -> new CorpCompanyException("No Corporate Company could be found."));
    }

    public Boolean isCorporateActive(String companyRegistrationNo){
        return corpCompanyRepository.findCorpCompanyByRegistrationNo(companyRegistrationNo).isPresent();
    }
}
