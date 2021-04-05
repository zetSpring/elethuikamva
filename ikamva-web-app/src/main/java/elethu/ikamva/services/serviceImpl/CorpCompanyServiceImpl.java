package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.domain.CorpCompany;
import elethu.ikamva.repositories.CorpCompanyRepository;
import elethu.ikamva.services.CorpCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class CorpCompanyServiceImpl implements CorpCompanyService {

    @Autowired
    private CorpCompanyRepository corpCompanyRepository;

    @Override
    public void saveOrUpdateCorpCompany(CorpCompany corpCompany) {

    }

    @Override
    public List<CorpCompany> findAllCorpCompany() {
        List<CorpCompany> corpCompaniesSet = new LinkedList<>();
        corpCompanyRepository.findAll().iterator().forEachRemaining(corpCompaniesSet::add);

        return corpCompaniesSet;
    }
}
