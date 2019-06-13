package elethu.ikamva.repositories;

import elethu.ikamva.domain.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findAccountsByAccountNo(Long accountNo);
}
