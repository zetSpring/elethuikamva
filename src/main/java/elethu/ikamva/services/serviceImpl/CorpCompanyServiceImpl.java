package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.domain.CorpCompany;
import elethu.ikamva.repositories.CorpCompanyRepository;
import elethu.ikamva.services.CorpCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CorpCompanyServiceImpl implements CorpCompanyService {

    @Autowired
    private CorpCompanyRepository corpCompanyRepository;

    @Override
    public void saveOrUpdateCorpCompany(CorpCompany corpCompany) {

    }

    @Override
    public void deleteCorpCompany(CorpCompany corpCompany) {

    }

    @Override
    public CorpCompany findOne(Long id) {
        return corpCompanyRepository.findById(id).get();
    }

    @Override
    public Set<CorpCompany> findAllCorpCompany() {
        Set<CorpCompany> corpCompaniesSet = new HashSet<>();
        corpCompanyRepository.findAll().iterator().forEachRemaining(corpCompaniesSet::add);

        return corpCompaniesSet;
    }
}
