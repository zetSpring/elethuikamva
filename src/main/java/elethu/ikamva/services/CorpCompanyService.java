package elethu.ikamva.services;

import elethu.ikamva.domain.CorpCompany;

import java.util.Set;

public interface CorpCompanyService {
    void saveOrUpdateCorpCompany(CorpCompany corpCompany);

    void deleteCorpCompany(CorpCompany corpCompany);

    CorpCompany findCorpCompany(Long id);

    Set<CorpCompany> findAllCorpCompany();

    Boolean isCorpCompanyActive(CorpCompany corpCompany);
    //CorpCompany
}
