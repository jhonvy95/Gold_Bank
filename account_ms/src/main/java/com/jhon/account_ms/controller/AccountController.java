package com.jhon.account_ms.controller;

import com.jhon.account_ms.exceptions.AccountNotFoundException;
import com.jhon.account_ms.models.Account;
import com.jhon.account_ms.repository.AccountRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @GetMapping("/account/{username}")
    Account getAccount(@PathVariable String username){
        return accountRepository.findById(username).orElseThrow(() -> new AccountNotFoundException("No se encontro una cuenta con ese ususario " + username + " enviado. "));
    }

    @PostMapping("/accounts")
    Account newAccount(@RequestBody Account account){
        return accountRepository.save(account);
    }
 
}
