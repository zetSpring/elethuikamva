package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.Account;
import elethu.ikamva.service.AccountService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/accounts")
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
                                            @PathVariable Long id) {
        var findAccount = accountService.findAccountById(id);
        return new ResponseEntity<>(findAccount, HttpStatus.OK);
    }

    @ApiOperation(value = "Find an Account for A Private Company")
    @GetMapping("/company/{id}")
    ResponseEntity<Account> FindAccountByPrivateCompany(@ApiParam(value = "A private company id for retrieving account details.", required = true, example = "000000000")
                                                        @PathVariable Long id) {
        var companyAccount = accountService.findAccountByCompany(id);
        return new ResponseEntity<>(companyAccount, HttpStatus.OK);
    }

    @ApiOperation(value = "Find an account by an Account Number ")
    @GetMapping("/account/{accountNo}")
    ResponseEntity<Account> FindAccountByAccountNo(@ApiParam(value = "An account no for retrieving account details", required = true, example = "0000000000")
                                                   @PathVariable Long accountNo) {
        var account = accountService.findAccountByAccountNo(accountNo);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @ApiOperation(value = "Find All Active Account Numbers for Elethu Ikamva.")
    @GetMapping("/")
    List<Account> FindAllAccounts() {
        return accountService.findAllAccounts();
    }

    @ApiOperation(value = "Adding a New Bank Account To a Private Account.")
    @PostMapping("/add")
    ResponseEntity<Account> saveAccount(@RequestBody Account account) {
        var newAccount = accountService.saveNewAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

//    @ApiOperation(value = "An id for the account number to be updated.")
//    @PutMapping("/account/update{id}")
//    ResponseEntity<Account> updateAccount(@ApiParam(value = "Account id for an account to be updated:", required = true)
//                                          @Valid @RequestBody Account updateAccount, Long id) throws ResourceNotFoundException{
//
//    }

    @ApiOperation(value = "An id for the account number to be deleted.")
    @DeleteMapping("/delete/{accountNo}")
    ResponseEntity<Account> deleteAccountByAccountNo(@ApiParam(value = "Account id for an account to be deleted:", required = true, example = "123")
                                          @PathVariable Long accountNo )  {
        var account = accountService.deleteAccountByAccountNo(accountNo);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @ApiOperation(value = "An id for the account number to be deleted.")
    @DeleteMapping("/{id}")
    ResponseEntity<Account> deleteAccountById(@ApiParam(value = "Account id for an account to be deleted:", required = true, example = "123")
                                                     @PathVariable Long id )  {
        var account = accountService.deleteAccountById(id);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
