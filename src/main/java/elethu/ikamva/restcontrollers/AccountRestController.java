package elethu.ikamva.restcontrollers;

import elethu.ikamva.services.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController {

    private final AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public void saveAccount() {

    }
}
