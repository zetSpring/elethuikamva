package elethu.ikamva.repositories;

import elethu.ikamva.domain.PrivateCompany;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PrivateCompanyRepository extends CrudRepository<PrivateCompany, Long> {
    Optional<PrivateCompany> findPrivateCompaniesByCompanyName(String companyName);
}
