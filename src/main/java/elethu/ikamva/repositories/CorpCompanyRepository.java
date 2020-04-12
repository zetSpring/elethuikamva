package elethu.ikamva.repositories;

import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.domain.CorpCompany;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface CorpCompanyRepository extends CrudRepository<CorpCompany, Long> {
    @Query("SELECT corp FROM CorpCompany corp WHERE corp.endDate = NULL")
    Set<CorpCompany> findAllCorporates();
}
