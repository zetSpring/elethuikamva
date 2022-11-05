package elethu.ikamva.service.Impl;

import elethu.ikamva.aspects.ExecutionTime;
import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.PrivateCompany;
import elethu.ikamva.exception.PrivateCompanyException;
import elethu.ikamva.repositories.PrivateCompanyRepository;
import elethu.ikamva.service.PrivateCompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateCompanyServiceImpl implements PrivateCompanyService {
    private final PrivateCompanyRepository privateCompanyRepository;

    @Override
    @ExecutionTime
    public PrivateCompany savePrivateCompany(PrivateCompany privateCompany) {
        if (isPrivateCompanyActive(privateCompany.getRegistrationNo())) {
            log.info("Private company with registration number: {} already exists.", privateCompany.getRegistrationNo());
            return privateCompanyRepository.findActivePrivateCompany(privateCompany.getRegistrationNo()).get();
        }

        return privateCompanyRepository.save(privateCompany);
    }

    @Override
    @ExecutionTime
    public PrivateCompany deletePrivateCompany(Long id) {
        PrivateCompany deletePtyCompany = privateCompanyRepository.findPrivateCompaniesById(id)
                .orElseThrow(() -> new PrivateCompanyException(String.format("Could not find pty company with id %d to delete", id)));
        deletePtyCompany.setEndDate(DateFormatter.returnLocalDateTime());

        return privateCompanyRepository.save(deletePtyCompany);
    }

    @Override
    @ExecutionTime
    public PrivateCompany findPrivateCompanyById(Long id) {
        return privateCompanyRepository.findPrivateCompaniesById(id)
                .orElseThrow(() -> new PrivateCompanyException("Could not find a private company: " + id));
    }

    @Override
    @ExecutionTime
    public PrivateCompany findPrivateCompanyByRegistration(String registrationNo) {
        return privateCompanyRepository.findPrivateCompanyByRegistrationNo(registrationNo)
                .orElseThrow(() -> new PrivateCompanyException(String.format("Could not find pty company with registration no %s.", registrationNo)));
    }

    @Override
    @ExecutionTime
    public List<PrivateCompany> findAllPrivateCompany() {
        List<PrivateCompany> privateCompanies = new ArrayList<>();
        privateCompanyRepository.findAll().iterator().forEachRemaining(privateCompanies::add);

        if (CollectionUtils.isEmpty(privateCompanies)) {
            log.info("No private companies found.");
            return new ArrayList<>();
        }

        return privateCompanies.stream()
                .filter(pty -> pty.getEndDate() == null)
                .collect(Collectors.toList());
    }

    @Override
    @ExecutionTime
    public boolean isPrivateCompanyActive(String registrationNo) {
        return privateCompanyRepository.findActivePrivateCompany(registrationNo).isPresent();
    }
}
