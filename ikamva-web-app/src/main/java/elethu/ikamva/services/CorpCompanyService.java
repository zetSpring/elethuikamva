package elethu.ikamva.services;

import elethu.ikamva.domain.CorpCompany;

import java.util.Set;

public interface CorpCompanyService {
    void saveOrUpdateCorpCompany(CorpCompany corpCompany);
    Set<CorpCompany> findAllCorpCompany();

    //CorpCompany
}
