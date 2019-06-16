package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.domain.PrivateCompany;
import elethu.ikamva.exception.PrivateCompanyException;
import elethu.ikamva.repositories.PrivateCompanyRepository;
import elethu.ikamva.services.PrivateCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PrivateCompanyServiceImpl implements PrivateCompanyService {

    @Autowired
    private final PrivateCompanyRepository privateCompanyRepository;

    public PrivateCompanyServiceImpl(PrivateCompanyRepository privateCompanyRepository) {
        this.privateCompanyRepository = privateCompanyRepository;
    }

    @Override
    public void saveOrUpdatePrivateCompany(PrivateCompany privateCompany) {
        privateCompanyRepository.save(privateCompany);
    }

    @Override
    public void deletePrivateCompany(PrivateCompany privateCompany) {
        if (isPrivateCompanyActive(privateCompany))
            saveOrUpdatePrivateCompany(privateCompany);
        else
            throw new PrivateCompanyException("Private company: " + privateCompany.getCompanyName() + "is already inactive or could not be found.");
    }

    /* Find Private Company by company id*/
    @Override
    public PrivateCompany findPrivateCompanyById(Long id) {
        Optional<PrivateCompany> privateCompanyOptional = privateCompanyRepository.findById(id);

        if (!privateCompanyOptional.isPresent()) {
            throw new PrivateCompanyException("Private company id: " + id + "could not be found.");
        }
        return privateCompanyOptional.get();
    }

    /* Find Private Company by company name*/
    @Override
    public PrivateCompany findPrivateCompanyByName(String name) {
        Optional<PrivateCompany> privateCompanyOptional = privateCompanyRepository.findPrivateCompaniesByCompanyName(name);
        if (!privateCompanyOptional.isPresent()) {
            throw new PrivateCompanyException("Private company id: " + name + "could not be found.");
        }

        return privateCompanyOptional.get();
    }

    /* Find all Elethu Ikamva private companies*/
    @Override
    public Set<PrivateCompany> getAllPrivateCompany() {
        Set<PrivateCompany> privateCompanies = new HashSet<>();

        privateCompanyRepository.findAllActivePrivateCompanies().iterator().forEachRemaining(privateCompanies::add);

        return privateCompanies;
    }

    @Override
    public Boolean isPrivateCompanyActive(PrivateCompany privateCompany) {
        return privateCompany.getId() != null && privateCompany.getEndDate() == null;
    }
}
