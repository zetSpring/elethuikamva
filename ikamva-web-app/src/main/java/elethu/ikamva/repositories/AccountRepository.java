package elethu.ikamva.repositories;

import elethu.ikamva.domain.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface AccountRepository extends CrudRepository<Account, Long> {
    @Query("SELECT acc FROM Account acc WHERE acc.endDate = NULL AND acc.accountNo = ?1")
    Optional<Account> findAccountsByAccountNo(Long accountNo);
    @Query("SELECT acc FROM Account acc WHERE acc.endDate = NULL AND acc.id = ?1")
    Optional<Account> findAccountsById(Long id);
    @Query("SELECT acc FROM Account acc WHERE acc.endDate = NULL")
    List<Account> findAllActiveAccounts();
    @Query("SELECT acc FROM Account acc WHERE acc.endDate = NULL AND acc.companyAccount.id = ?1")
    Optional<Account> findAccountsByCompany(Long companyId);
}
