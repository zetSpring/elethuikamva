package elethu.ikamva.service.Impl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.PrivateCompany;
import elethu.ikamva.exception.PrivateCompanyException;
import elethu.ikamva.repositories.PrivateCompanyRepository;
import elethu.ikamva.service.PrivateCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateCompanyServiceImpl implements PrivateCompanyService {
    private final PrivateCompanyRepository privateCompanyRepository;

    @Override
    public PrivateCompany savePrivateCompany(PrivateCompany privateCompany) {
        if (!isPrivateCompanyActive(privateCompany.getRegistrationNo())) {
            return privateCompanyRepository.save(privateCompany);
        } else {
            throw new PrivateCompanyException(String.format("private company with registration %s already exists.", privateCompany.getRegistrationNo()));
        }
    }

    @Override
    public PrivateCompany deletePrivateCompany(Long id) {
        PrivateCompany deletePtyCompany = privateCompanyRepository.findPrivateCompaniesById(id)
                .orElseThrow(() -> new PrivateCompanyException(String.format("Could not find pty company with id %d to delete", id)));
        deletePtyCompany.setEndDate(DateFormatter.returnLocalDateTime());
        return privateCompanyRepository.save(deletePtyCompany);
    }

    @Override
    public PrivateCompany findPrivateCompanyById(Long id) {
        return privateCompanyRepository.findPrivateCompaniesById(id)
                .orElseThrow(() -> new PrivateCompanyException("Could not find a private company: " + id));
    }

    @Override
    public PrivateCompany findPrivateCompanyByRegistration(String registrationNo) {
        return privateCompanyRepository.findPrivateCompanyByRegistrationNo(registrationNo)
                .orElseThrow(() -> new PrivateCompanyException(String.format("Could not find pty company with registration no %s.", registrationNo)));
    }

    @Override
    public List<PrivateCompany> findAllPrivateCompany() {
        List<PrivateCompany> privateCompanies = new ArrayList<>();
        privateCompanyRepository.findAll().iterator().forEachRemaining(privateCompanies::add);
        if (!privateCompanies.isEmpty()) {
            return privateCompanies.stream()
                    .filter(pty -> pty.getEndDate() == null)
                    .collect(Collectors.toList());
        } else {
            throw new PrivateCompanyException("There were no private companies found please, please add some.");
        }
    }

    @Override
    public boolean isPrivateCompanyActive(String registrationNo) {
        return privateCompanyRepository.findActivePrivateCompany(registrationNo).isPresent();
    }
}
