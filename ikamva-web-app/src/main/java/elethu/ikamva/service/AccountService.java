package elethu.ikamva.service;

import elethu.ikamva.domain.Account;

import java.util.List;

public interface AccountService {
    Account saveNewAccount(Account account);
    Account deleteAccountByAccountNo(Long accountNo);
    Account deleteAccountById(Long id);
    Account findAccountById(Long id);
    Account findAccountByAccountNo(Long account);
    Account findAccountByCompany(Long id);
    List<Account> findAllAccounts();
    boolean isAccountActive(Long account);
}
