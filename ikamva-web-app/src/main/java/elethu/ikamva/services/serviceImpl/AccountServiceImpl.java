package elethu.ikamva.services.serviceImpl;

//import elethu.ikamva.bulk.domain.Account;
import elethu.ikamva.domain.Account;
import elethu.ikamva.exception.AccountException;
import elethu.ikamva.repositories.AccountRepository;
import elethu.ikamva.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public void deleteAccount(Long accountNo) {
        Optional<Account> account = accountRepository.findAccountsByAccountNo(accountNo);
        Date accountEndDate = new Date(System.currentTimeMillis());
        if (isAccountActive(account.get())){
            account.get().setEndDate(accountEndDate);
            saveOrUpdateAccount(account.get());
        }
        else
            throw new AccountException("Account number: " + accountNo + " is already inactive or does not exist");
    }

    @Override
    public Account findAccountByAccountNo(Long accountNo) {
        Optional<Account> accountOptional = accountRepository.findAccountsByAccountNo(accountNo);

        if (accountOptional.isPresent())
            return accountOptional.get();
        else
            throw new AccountException("Account number: " + accountNo + " could not be found");
    }

    @Override
    public Account findAccountById(Long id) {
        Optional<Account> accountOptional = accountRepository.findAccountsById(id);

        if (accountOptional.isPresent())
            return accountOptional.get();
        else
            throw new AccountException("Account id: " + id + " could not be found");
    }

    @Override
    public Set<Account> findAccountByCompany(Long id) {
        return null;
    }

    @Override
    public Set<Account> findAllAccounts() {
        Set<Account> accounts = new HashSet<>();
        accountRepository.findAllActiveAccounts().iterator().forEachRemaining(accounts::add);

        return accounts;
    }

    @Override
    public Boolean isAccountActive(Account account) {
        if(accountRepository.findAccountsById(account.getId()) != null)
            return true;
        else
            return false;

    }
}
