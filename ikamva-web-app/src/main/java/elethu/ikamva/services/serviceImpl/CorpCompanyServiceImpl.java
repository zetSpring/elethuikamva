package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.CorpCompany;
import elethu.ikamva.exception.CorpCompanyException;
import elethu.ikamva.repositories.CorpCompanyRepository;
import elethu.ikamva.services.CorpCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CorpCompanyServiceImpl implements CorpCompanyService {
    private final CorpCompanyRepository corpCompanyRepository;

    @Override
    public CorpCompany createCorpCompany(CorpCompany newCorpCompany) {
        if(!isCorporateActive(newCorpCompany.getRegistrationNo())){
            newCorpCompany.setCreatedDate(DateFormatter.returnLocalDateTime());
            return corpCompanyRepository.save(newCorpCompany);
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
        deleteCorpCompany.setEndDate(DateFormatter.returnLocalDateTime());
        return corpCompanyRepository.save(deleteCorpCompany);
    }

    @Override
    public List<CorpCompany> findAllCorpCompany() {
        List<CorpCompany> corpCompanyList = new LinkedList<>();
        corpCompanyRepository.findAll().iterator().forEachRemaining(corpCompanyList::add);

        if (corpCompanyList.isEmpty()) {
            throw new CorpCompanyException("There are no Corporate  companies found.");
        } else {
            return corpCompanyList.stream()
                    .filter(corp -> corp.getEndDate() == null)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public CorpCompany findCorpCompany() {
        return corpCompanyRepository.findCorpCompany()
                .orElseThrow(() -> new CorpCompanyException("No Corporate Company could be found."));
    }

    public Boolean isCorporateActive(String companyRegistrationNo){
        return corpCompanyRepository.findCorpCompanyByRegistrationNo(companyRegistrationNo).isPresent();
    }
}
