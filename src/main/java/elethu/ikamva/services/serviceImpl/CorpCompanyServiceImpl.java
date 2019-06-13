package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.domain.CorpCompany;
import elethu.ikamva.exception.CorpCompanyException;
import elethu.ikamva.repositories.CorpCompanyRepository;
import elethu.ikamva.services.CorpCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CorpCompanyServiceImpl implements CorpCompanyService {

    @Autowired
    private final CorpCompanyRepository corpCompanyRepository;

    public CorpCompanyServiceImpl(CorpCompanyRepository corpCompanyRepository) {
        this.corpCompanyRepository = corpCompanyRepository;
    }

    @Override
    public void saveOrUpdateCorpCompany(CorpCompany corpCompany) {
        corpCompanyRepository.save(corpCompany);
    }

    @Override
    public void deleteCorpCompany(CorpCompany corpCompany) {
        if (isCorpCompanyActive(corpCompany))
            saveOrUpdateCorpCompany(corpCompany);
        else
            throw new CorpCompanyException("Corporate company: " + corpCompany.getCorpName() + " is already inactive or could not be found");
    }

    @Override
    public CorpCompany findCorpCompany(Long id) {
        Optional<CorpCompany> corpCompanyOptional = corpCompanyRepository.findById(id);

        if (!corpCompanyOptional.isPresent()) {
            throw new CorpCompanyException("Corporate company: " + id + " could not be found");
        }
        return corpCompanyOptional.get();
    }

    @Override
    public Set<CorpCompany> findAllCorpCompany() {
        Set<CorpCompany> corpCompanies = new HashSet();

        corpCompanyRepository.findAll().iterator().forEachRemaining(corpCompanies::add);
        return corpCompanies;
    }

    @Override
    public Boolean isCorpCompanyActive(CorpCompany corpCompany) {
        if (corpCompany.getId() != null && corpCompany.getEndDate() == null)
            return true;
        else
            return null;
    }
}
