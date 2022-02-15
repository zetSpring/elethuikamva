package elethu.ikamva.service;

import elethu.ikamva.domain.PrivateCompany;

import java.util.List;


public interface PrivateCompanyService {
    PrivateCompany savePrivateCompany(PrivateCompany privateCompany);
    PrivateCompany deletePrivateCompany(Long id);
    PrivateCompany findPrivateCompanyById(Long id);
    PrivateCompany findPrivateCompanyByRegistration(String registrationNo);
    List<PrivateCompany> findAllPrivateCompany();
    boolean isPrivateCompanyActive(String registrationNo);
}
