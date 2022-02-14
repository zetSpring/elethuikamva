package elethu.ikamva.service.Impl;
import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.Account;
import elethu.ikamva.exception.AccountException;
import elethu.ikamva.repositories.AccountRepository;
import elethu.ikamva.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Account saveNewAccount(Account account) {
        if(!isAccountActive(account.getAccountNo())){
            account.setCreatedDate(DateFormatter.returnLocalDate());
            return accountRepository.save(account);
        }else
            throw new AccountException("The account: " + account.getAccountNo() + " already exist");
    }

    @Override
    public Account deleteAccountByAccountNo(Long accountNo) {
       var deleteAccount = accountRepository.findAccountsByAccountNo(accountNo)
                .orElseThrow(() -> new AccountException("Account number: " + accountNo + " is already inactive or does not exist"));

        deleteAccount.setEndDate(DateFormatter.returnLocalDate());
        return  accountRepository.save(deleteAccount);
    }

    @Override
    public Account deleteAccountById(Long id) {
        var deleteAccount = accountRepository.findById(id)
                .orElseThrow(() -> new AccountException("Account with id: " + id + " is already inactive or does not exist"));
        deleteAccount.setEndDate(DateFormatter.returnLocalDate());
        return  accountRepository.save(deleteAccount);
    }

    @Override
    public Account findAccountByAccountNo(Long accountNo) {
        return accountRepository.findAccountsByAccountNo(accountNo)
                .orElseThrow(() -> new AccountException("Account number: " + accountNo + " could not be found"));
    }

    @Override
    public Account findAccountById(Long id) {
        return accountRepository.findAccountsById(id)
                .orElseThrow(() -> new AccountException("Account id: " + id + " could not be found"));
    }

    @Override
    public Account findAccountByCompany(Long companyId) {
        return accountRepository.findAccountsByCompany(companyId)
                .orElseThrow(() -> new AccountException("There is no private company to add account for"));
    }

    @Override
    public List<Account> findAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().iterator().forEachRemaining(accounts::add);

        if (accounts.isEmpty()) {
            throw new AccountException("There is no private company to add account for");

        } else {
            return accounts.stream()
                    .filter( account -> account.getEndDate() == null)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public boolean isAccountActive(Long account) {
        return accountRepository.findAccountsByAccountNo(account).isPresent();
    }
}
