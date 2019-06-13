package elethu.ikamva.services;

import elethu.ikamva.domain.PrivateCompany;

import java.util.Set;


public interface PrivateCompanyService {
    void saveOrUpdatePrivateCompany(PrivateCompany privateCompany);

    void deletePrivateCompany(PrivateCompany privateCompany);

    PrivateCompany findPrivateCompanyById(Long id);

    PrivateCompany findPrivateCompanyByName(String name);

    Set<PrivateCompany> getAllPrivateCompany();

    Boolean isPrivateCompanyActive(PrivateCompany privateCompany);
}
