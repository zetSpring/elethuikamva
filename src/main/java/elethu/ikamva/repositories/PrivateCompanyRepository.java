package elethu.ikamva.repositories;

import elethu.ikamva.domain.PrivateCompany;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface PrivateCompanyRepository extends CrudRepository<PrivateCompany, Long> {
    Optional<PrivateCompany> findPrivateCompaniesByCompanyName(String companyName);
    @Query("SELECT priv FROM PrivateCompany priv WHERE priv.endDate = null")
    Set<PrivateCompany> findAllActivePrivateCompanies();
}
