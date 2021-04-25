package elethu.ikamva.services.serviceImpl;
import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.Account;
import elethu.ikamva.exception.AccountException;
import elethu.ikamva.repositories.AccountRepository;
import elethu.ikamva.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final DateFormatter dateFormatter;

    @Override
    public Account saveNewAccount(Account account) {
        if(!isAccountActive(account.getAccountNo())){
            account.setCreatedDate(dateFormatter.returnLocalDate());
            return accountRepository.save(account);
        }else
            throw new AccountException("The account: " + account.getAccountNo() + " already exist");
    }

    @Override
    public Account deleteAccount(Long accountNo) {
        Optional<Account> accountOptional = accountRepository.findAccountsByAccountNo(accountNo);
        var account = accountOptional.orElseThrow(() -> new AccountException("Account number: " + accountNo + " is already inactive or does not exist"));
        account.setEndDate(dateFormatter.returnLocalDate());
        return  accountRepository.save(account);
    }

    @Override
    public Account findAccountByAccountNo(Long accountNo) {
        return accountRepository.findAccountsByAccountNo(accountNo)
                .orElseThrow(() -> new AccountException("Account number: " + accountNo + " could not be found"));
    }

    @Override
    public Account findAccountById(Long id) {
        Optional<Account> accountOptional = accountRepository.findAccountsById(id);
        return accountOptional.orElseThrow(() -> new AccountException("Account id: " + id + " could not be found"));
    }

    @Override
    public Account findAccountByCompany(Long companyId) {
        return accountRepository.findAccountsByCompany(companyId)
                .orElseThrow(() -> new AccountException("There is no private company to add account for"));
    }

    @Override
    public List<Account> findAllAccounts() {
        List<Account> accounts = new LinkedList<>();
        accountRepository.findAllActiveAccounts().iterator().forEachRemaining(accounts::add);

        return accounts;
    }

    @Override
    public boolean isAccountActive(Long account) {
        return accountRepository.findAccountsByAccountNo(account).isPresent();
    }
}
