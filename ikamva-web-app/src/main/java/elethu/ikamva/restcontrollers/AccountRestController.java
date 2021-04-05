package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.Account;
import elethu.ikamva.exception.ResourceNotFoundException;
import elethu.ikamva.services.AccountService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RequestMapping("/api/v1/accounts")
@RestController
@Api(value = "Elethu Ikamva Investment", description = "Operations pertaining to the bank accounts of the elethu ikamva investment")
public class AccountRestController {
    private final AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 500, message = "Tell me something I don't know")
    })
    @ApiOperation(value = "Find an account by identity")
    @GetMapping("/{id}")
    ResponseEntity<Account> FindAccountById(@ApiParam(value = "An id for the account number to be retrieved.", required = true, example = "123")
                                            @PathVariable Long id) throws ResourceNotFoundException {
        Account findAccount = accountService.findAccountById(id);
        if(accountService.isAccountActive(findAccount))
            return ResponseEntity.ok().body(findAccount);
        else
            throw new ResourceNotFoundException("Account id: '" + id + "' could not be found");
    }


    @ApiOperation(value = "Private company id for which to retrieve account account associate with it.")
    @GetMapping("/private/{id}")
    Set<Account> FindAccountByPrivateCompany(@PathVariable Long id) throws ResourceNotFoundException{
        Set<Account> accounts = accountService.findAccountByCompany(id);
        if(!accounts.isEmpty())
            return accountService.findAccountByCompany(id);
        else
            throw new ResourceNotFoundException("There are no account to retrieve for private Company '" + id + "'");
    }

//    @ApiOperation(value = "Find an account by an Account Number ")
//    @GetMapping("/{accountNo}")
//    ResponseEntity<Account> FindAccountByAccountNo(@Valid @PathVariable Long accountNo) throws ResourceNotFoundException{
//        Account account = accountService.findAccountByAccountNo(accountNo);
//        if(accountService.isAccountActive(account))
//            return ResponseEntity.ok().body(account);
//        else
//            throw new ResourceNotFoundException("Account number: '" + accountNo + "' could not be found.");
//    }

    @ApiOperation(value = "Find all active account numbers for Elethu Ikamva.")
    @GetMapping("/")
    Set<Account> FindAllAccounts() throws ResourceNotFoundException{
        Set<Account> accountSet = accountService.findAllAccounts();
        if(!accountSet.isEmpty())
            return accountSet;
        else
            throw new ResourceNotFoundException("There are no accounts found to display.");
    }



//    @PostMapping("/account/save")
//    Map<String, Boolean> saveAccount(@RequestBody Account newAccount) {
//
//    }
//
//    @ApiOperation(value = "An id for the account number to be updated.")
//    @PutMapping("/account/update{id}")
//    ResponseEntity<Account> updateAccount(@ApiParam(value = "Account id for an account to be updated:", required = true)
//                                          @Valid @RequestBody Account updateAccount, Long id) throws ResourceNotFoundException{
//
//    }

    @ApiOperation(value = "An id for the account number to be deleted.")
    @DeleteMapping("/delete/{id}")
    Map<String, Boolean> deleteAccount(@ApiParam(value = "Account id for an account to be deleted:", required = true, example = "123")
                                       @PathVariable Long id) throws ResourceNotFoundException {
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
