package elethu.ikamva.services;

import elethu.ikamva.domain.CorpCompany;

import java.util.Set;

public interface CorpCompanyService {
    void saveOrUpdateCorpCompany(CorpCompany corpCompany);
    void deleteCorpCompany(CorpCompany corpCompany);
    CorpCompany findOne(Long id);
    Set<CorpCompany> findAllCorpCompany();

    //CorpCompany
}
