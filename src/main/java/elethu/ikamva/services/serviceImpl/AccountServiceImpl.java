package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.domain.Account;
import elethu.ikamva.exception.AccountException;
import elethu.ikamva.repositories.AccountRepository;
import elethu.ikamva.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private final AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void saveOrUpdateAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Account account) {
        if (isAccountActive(account))
            saveOrUpdateAccount(account);
        else
            throw new AccountException("Account number: " + account.getAccountNo() + " is already inactive or does not exist");
    }


    @Override
    public Account findAccountByAccountNo(Long accountNo) {
        Optional<Account> accountOptional = accountRepository.findAccountsByAccountNo(accountNo);

        if (!accountOptional.isPresent())
            throw new AccountException("Account number: " + accountNo + " could not be found");

        return accountOptional.get();
    }

    @Override
    public Account findAccountById(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);

        if (!accountOptional.isPresent())
            throw new AccountException("Account id: " + id + " could not be found");

        return accountOptional.get();
    }

    @Override
    public Set<Account> findAllAccounts() {
        Set<Account> accounts = new HashSet<>();

        accountRepository.findAll().iterator().forEachRemaining(accounts::add);

        return accounts;
    }

    @Override
    public Boolean isAccountActive(Account account) {
        return account.getEndDate() == null && account.getId() != null;
    }
}
