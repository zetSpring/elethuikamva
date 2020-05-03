package elethu.ikamva.repositories;

import elethu.ikamva.domain.PrivateCompany;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PrivateCompanyRepository extends CrudRepository<PrivateCompany, Long> {
}
