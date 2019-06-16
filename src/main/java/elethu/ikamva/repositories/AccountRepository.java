package elethu.ikamva.repositories;

import elethu.ikamva.domain.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;


public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findAccountsByAccountNo(Long accountNo);
    @Query("SELECT acc FROM Account acc WHERE acc.endDate = null")
    Set<Account> findAllActiveAccounts();
    @Query("SELECT acc FROM Account acc WHERE acc.endDate = null")
    Account findAccountsByCompany();
}
