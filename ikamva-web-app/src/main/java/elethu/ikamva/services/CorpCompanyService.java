package elethu.ikamva.services;

import elethu.ikamva.domain.CorpCompany;

import java.util.List;

public interface CorpCompanyService {
    CorpCompany createCorpCompany(CorpCompany newCorpCompany);
    CorpCompany updateCorpCompany(CorpCompany updateCorpCompany);
    CorpCompany deleteCorpCompany(Long id);
    List<CorpCompany> findAllCorpCompany();
    CorpCompany findCorpCompany();
}
