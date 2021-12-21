package elethu.ikamva.repositories;

import elethu.ikamva.domain.PrivateCompany;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PrivateCompanyRepository extends CrudRepository<PrivateCompany, Long> {

    @Query("SELECT pty FROM PrivateCompany pty WHERE pty.endDate = NULL AND pty.id = ?1")
    Optional<PrivateCompany> findPrivateCompaniesById(Long companyId);
    Optional<PrivateCompany> findPrivateCompanyByRegistrationNo(String registrationNo);
    @Query("SELECT pty FROM PrivateCompany pty WHERE pty.endDate = NULL AND pty.registrationNo = ?1")
    Optional<PrivateCompany> findActivePrivateCompany(String registrationNo);
}
