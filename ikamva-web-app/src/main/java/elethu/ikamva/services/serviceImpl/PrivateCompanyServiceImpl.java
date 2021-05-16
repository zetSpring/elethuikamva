package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.domain.PrivateCompany;
import elethu.ikamva.exception.PrivateCompanyException;
import elethu.ikamva.repositories.PrivateCompanyRepository;
import elethu.ikamva.services.PrivateCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PrivateCompanyServiceImpl implements PrivateCompanyService {

    private final PrivateCompanyRepository privateCompanyRepository;

    @Override
    public PrivateCompany savePrivateCompany(PrivateCompany privateCompany) {
        return null;
    }

    @Override
    public PrivateCompany deletePrivateCompany(PrivateCompany privateCompany) {
        return null;
    }

    @Override
    public PrivateCompany findPrivateCompanyById(Long id) {
        return privateCompanyRepository.findPrivateCompaniesById(id)
                .orElseThrow(() -> new PrivateCompanyException("Could not find a private company: " + id));
    }

    @Override
    public PrivateCompany findPrivateCompanyByRegistration(String registrationNo) {
        return null;
    }

    @Override
    public List<PrivateCompany> findAllPrivateCompany() {
        List<PrivateCompany>  privateCompanies = privateCompanyRepository.findAllPrivateCompanies();
        if(!privateCompanies.isEmpty())
            return privateCompanies;
        else
            throw new PrivateCompanyException("There are no private companies ro display.");
    }

    @Override
    public Boolean isPrivateCompanyActive(PrivateCompany privateCompany) {
        return null;
    }
}
