package elethu.ikamva.service.Impl;

import elethu.ikamva.aspects.ExecutionTime;
import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.Account;
import elethu.ikamva.exception.AccountException;
import elethu.ikamva.repositories.AccountRepository;
import elethu.ikamva.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    @ExecutionTime
    public Account saveNewAccount(Account account) {
        if (isAccountActive(account.getAccountNo())) {
            log.info("Account with account number: {} already exists.", account.getAccountNo());
            throw new AccountException("Account already exists");
        }

        account.setCreatedDate(DateFormatter.returnLocalDate());
        return accountRepository.save(account);
    }

    @Override
    @ExecutionTime
    public Account deleteAccountByAccountNo(Long accountNo) {
        var deleteAccount = accountRepository.findAccountsByAccountNo(accountNo)
                .orElseThrow(() -> new AccountException("Account number: " + accountNo + " is already inactive or does not exist"));

        deleteAccount.setEndDate(DateFormatter.returnLocalDate());
        return accountRepository.save(deleteAccount);
    }

    @Override
    @ExecutionTime
    public Account deleteAccountById(Long id) {
        var deleteAccount = accountRepository.findById(id)
                .orElseThrow(() -> new AccountException("Account with id: " + id + " is already inactive or does not exist"));
        deleteAccount.setEndDate(DateFormatter.returnLocalDate());
        return accountRepository.save(deleteAccount);
    }

    @Override
    @ExecutionTime
    public Account findAccountByAccountNo(Long accountNo) {
        return accountRepository.findAccountsByAccountNo(accountNo)
                .orElseThrow(() -> new AccountException("Account number: " + accountNo + " could not be found"));
    }

    @Override
    @ExecutionTime
    public Account findAccountById(Long id) {
        return accountRepository.findAccountsById(id)
                .orElseThrow(() -> new AccountException("Account id: " + id + " could not be found"));
    }

    @Override
    @ExecutionTime
    public Account findAccountByCompany(Long companyId) {
        return accountRepository.findAccountsByCompany(companyId)
                .orElseThrow(() -> new AccountException("There is no private company to add account for"));
    }

    @Override
    @ExecutionTime
    public List<Account> findAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().iterator().forEachRemaining(accounts::add);

        if (accounts.isEmpty()) {
            log.info("There are no accounts to display");
            throw new AccountException("There is no private company to add account for");

        }

        return accounts.stream()
                .filter(account -> Objects.isNull(account.getEndDate()))
                .collect(Collectors.toList());
    }

    @Override
    @ExecutionTime
    public boolean isAccountActive(Long account) {
        return accountRepository.findAccountsByAccountNo(account).isPresent();
    }
}
