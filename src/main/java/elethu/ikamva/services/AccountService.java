package elethu.ikamva.services;

import elethu.ikamva.domain.Account;

import java.util.Set;

public interface AccountService {

    void saveOrUpdateAccount(Account account);

    void deleteAccount(Account account);

    Account findAccountById(Long id);

    Account findAccountByAccountNo(Long account);

    Set<Account> findAllAccounts();

    Boolean isAccountActive(Account account);
}
