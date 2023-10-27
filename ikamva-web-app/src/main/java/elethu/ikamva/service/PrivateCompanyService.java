package elethu.ikamva.service;

import elethu.ikamva.domain.PrivateCompany;

import java.util.List;

public interface PrivateCompanyService {
    PrivateCompany deletePrivateCompany(Long id);

    List<PrivateCompany> findAllPrivateCompany();

    PrivateCompany findPrivateCompanyById(Long id);

    boolean isPrivateCompanyActive(String registrationNo);

    PrivateCompany savePrivateCompany(PrivateCompany privateCompany);

    PrivateCompany findPrivateCompanyByRegistration(String registrationNo);
}
