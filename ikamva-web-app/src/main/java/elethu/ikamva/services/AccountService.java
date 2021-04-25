package elethu.ikamva.services;

import elethu.ikamva.domain.Account;

import java.util.List;
import java.util.Set;

public interface AccountService {
    Account saveNewAccount(Account account);
    Account deleteAccount(Long accountNo);
    Account findAccountById(Long id);
    Account findAccountByAccountNo(Long account);
    Account findAccountByCompany(Long id);
    List<Account> findAllAccounts();
    boolean isAccountActive(Long account);
}
