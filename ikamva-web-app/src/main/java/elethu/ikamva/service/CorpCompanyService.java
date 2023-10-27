package elethu.ikamva.service;

import elethu.ikamva.domain.CorpCompany;

import java.util.List;

public interface CorpCompanyService {
    CorpCompany findCorpCompany();

    CorpCompany deleteCorpCompany(Long id);

    List<CorpCompany> findAllCorpCompany();

    CorpCompany saveCorpCompany(CorpCompany newCorpCompany);

    CorpCompany updateCorpCompany(CorpCompany updateCorpCompany);
}
