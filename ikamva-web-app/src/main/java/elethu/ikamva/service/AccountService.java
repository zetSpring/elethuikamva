package elethu.ikamva.service;

import elethu.ikamva.domain.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAllAccounts();

    Account findAccountById(Long id);

    Account deleteAccountById(Long id);

    Account findAccountByCompany(Long id);

    boolean isAccountActive(Long account);

    Account saveNewAccount(Account account);

    Account findAccountByAccountNo(Long account);

    Account deleteAccountByAccountNo(Long accountNo);
}
