package elethu.ikamva.service;

import elethu.ikamva.domain.CorpCompany;

import java.util.List;

public interface CorpCompanyService {
    CorpCompany saveCorpCompany(CorpCompany newCorpCompany);
    CorpCompany updateCorpCompany(CorpCompany updateCorpCompany);
    CorpCompany deleteCorpCompany(Long id);
    List<CorpCompany> findAllCorpCompany();
    CorpCompany findCorpCompany();
}
