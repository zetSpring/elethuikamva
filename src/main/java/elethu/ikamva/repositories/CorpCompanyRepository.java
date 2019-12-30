package elethu.ikamva.repositories;

import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.domain.CorpCompany;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface CorpCompanyRepository extends CrudRepository<CorpCompany, Long> {
    @Query("SELECT corp FROM CorpCompany corp WHERE corp.endDate = NULL")
    Set<CorpCompany> findAllCorporates();
}
