package elethu.ikamva.services;

import elethu.ikamva.domain.CorpCompany;

import java.util.List;
import java.util.Set;

public interface CorpCompanyService {
    void saveOrUpdateCorpCompany(CorpCompany corpCompany);
    List<CorpCompany> findAllCorpCompany();

    //CorpCompany
}
