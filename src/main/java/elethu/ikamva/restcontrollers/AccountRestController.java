package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.Account;
import elethu.ikamva.exception.ResourceNotFoundException;
import elethu.ikamva.services.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class AccountRestController {

    private final AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "Find an account by identity")
    @GetMapping("/account/{id}")
    ResponseEntity<Account> findAccountById(@ApiParam(value = "An id for the account number to be retrieved.", required = true)
                                            @PathVariable Long id) throws ResourceNotFoundException {
        Account findAccount = accountService.findAccountById(id);
        if(accountService.isAccountActive(findAccount))
            return ResponseEntity.ok().body(findAccount);
        else
            throw new ResourceNotFoundException("Account id: '" + id + "' could not be found");
    }
    @ApiOperation(value = "Private company id for which to retrieve account account associate with it.")
    @GetMapping("/account/company/{id}")
    Set<Account> findAccountByPrivateCompany(@PathVariable Long id) throws ResourceNotFoundException{
        Set<Account> accounts = accountService.findAccountByCompany(id);
        if(!accounts.isEmpty())
            return accountService.findAccountByCompany(id);
        else
            throw new ResourceNotFoundException("There are no account to retrieve for private Company '" + id + "'");
    }

    @ApiOperation(value = "Find an account by an Account Number ")
    @GetMapping("/account/{accountNo}")
    ResponseEntity<Account> findAccountByAccountNo(@Valid @PathVariable Long accountNo) throws ResourceNotFoundException{
        Account account = accountService.findAccountByAccountNo(accountNo);
        if(accountService.isAccountActive(account))
            return ResponseEntity.ok().body(account);
        else
            throw new ResourceNotFoundException("Account number: '" + accountNo + "' could not be found.");
    }

    @GetMapping("/accounts")
    Set<Account> findAllAccounts() throws ResourceNotFoundException{
        Set<Account> accountSet = accountService.findAllAccounts();
        if(!accountSet.isEmpty())
            return accountSet;
        else
            throw new ResourceNotFoundException("There are no accounts to found to display.");
    }



    @PostMapping("/account/save")
    Map<String, Boolean> saveAccount(@RequestBody Account newAccount) {

    }

    @PutMapping("/account/update{id}")
    ResponseEntity<Account> updateAccount(@Valid @RequestBody Account updateAccount, Long id){

    }

    @DeleteMapping("/account/delete/{id}")
    Map<String, Boolean> deleteAccount(@PathVariable Long id) throws ResourceNotFoundException {
        Account account = accountService.findAccountById(id);
        if(accountService.isAccountActive(account)){
            accountService.deleteAccount(account.getAccountNo());
            Map<String, Boolean> deleteResponse = new HashMap<>();
            deleteResponse.put("Successfully deleted: ", Boolean.TRUE);
            return deleteResponse;
        }
        else
            throw new ResourceNotFoundException("Could not an account id '" + id + "' to delete.");

    }


}
