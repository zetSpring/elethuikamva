package elethu.ikamva.repositories;

import elethu.ikamva.domain.CorpCompany;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface CorpCompanyRepository extends CrudRepository<CorpCompany, Long> {
   @Query("SELECT corp FROM CorpCompany corp WHERE corp.endDate = NULL")
   Optional<CorpCompany> findCorpCompany();

   @Query("SELECT corp FROM CorpCompany corp WHERE corp.registrationNo = ?1 AND corp.endDate = NULL")
   Optional<CorpCompany> findCorpCompanyByRegistrationNo(String registrationNo);
}
