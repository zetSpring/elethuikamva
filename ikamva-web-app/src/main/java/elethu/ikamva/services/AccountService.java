package elethu.ikamva.services;

import elethu.ikamva.domain.Account;

import java.util.Set;

public interface AccountService {
    void saveOrUpdateAccount(Account account);
    void deleteAccount(Long accountNo);
    Account findAccountById(Long id);
    Account findAccountByAccountNo(Long account);
    Set<Account> findAccountByCompany(Long id);
    Set<Account> findAllAccounts();
    Boolean isAccountActive(Account account);
}
