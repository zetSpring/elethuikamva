package elethu.ikamva.service.Impl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.CorpCompany;
import elethu.ikamva.exception.CorpCompanyException;
import elethu.ikamva.repositories.CorpCompanyRepository;
import elethu.ikamva.service.CorpCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CorpCompanyServiceImpl implements CorpCompanyService {
    private final CorpCompanyRepository corpCompanyRepository;

    @Override
    public CorpCompany saveCorpCompany(CorpCompany newCorpCompany) {
        if (isCorporateActive(newCorpCompany.getRegistrationNo())) {
            throw new CorpCompanyException("Corporate Company with registration number: " + newCorpCompany.getRegistrationNo() + " already exists.");
        }

        newCorpCompany.setCreatedDate(DateFormatter.returnLocalDateTime());
        return corpCompanyRepository.save(newCorpCompany);
    }

    @Override
    public CorpCompany updateCorpCompany(CorpCompany updateCorpCompany) {
        CorpCompany updateCompany = corpCompanyRepository.findById(updateCorpCompany.getId())
                .orElseThrow(() -> new CorpCompanyException(String.format("There is not Corporate Company found with id: %s", updateCorpCompany.getId())));

        return corpCompanyRepository.save(updateCorpCompany);
    }


    @Override
    public CorpCompany deleteCorpCompany(Long id) {
        var corpCompany = corpCompanyRepository.findById(id)
                .orElseThrow(() -> new CorpCompanyException("Could not find corporate company id: " + id + " to delete."));
        corpCompany.setEndDate(DateFormatter.returnLocalDateTime());

        return corpCompanyRepository.save(corpCompany);
    }

    @Override
    public List<CorpCompany> findAllCorpCompany() {
        List<CorpCompany> corpCompanyList = new ArrayList<>();
        corpCompanyRepository.findAll().iterator().forEachRemaining(corpCompanyList::add);

        if (corpCompanyList.isEmpty()) {
            throw new CorpCompanyException("There are no corporate companies found.");
        }

        return corpCompanyList.stream()
                .filter(corp -> Objects.isNull(corp.getEndDate()))
                .collect(Collectors.toList());
    }

    @Override
    public CorpCompany findCorpCompany() {
        return corpCompanyRepository.findCorpCompany()
                .orElseThrow(() -> new CorpCompanyException("No Corporate Company could be found."));
    }

    public Boolean isCorporateActive(String companyRegistrationNo) {
        return corpCompanyRepository.findCorpCompanyByRegistrationNo(companyRegistrationNo).isPresent();
    }
}
