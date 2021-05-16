package elethu.ikamva.services;

import elethu.ikamva.domain.PrivateCompany;

import java.util.List;
import java.util.Set;


public interface PrivateCompanyService {
    PrivateCompany savePrivateCompany(PrivateCompany privateCompany);
    PrivateCompany deletePrivateCompany(PrivateCompany privateCompany);
    PrivateCompany findPrivateCompanyById(Long id);
    PrivateCompany findPrivateCompanyByRegistration(String registrationNo);
    List<PrivateCompany> findAllPrivateCompany();
    Boolean isPrivateCompanyActive(PrivateCompany privateCompany);
}
