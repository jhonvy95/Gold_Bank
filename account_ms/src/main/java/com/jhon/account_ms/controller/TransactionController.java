package com.jhon.account_ms.controller;

import com.jhon.account_ms.exceptions.AccountNotFoundException;
import com.jhon.account_ms.exceptions.InsufficientBalanceException;
import com.jhon.account_ms.models.Account;
import com.jhon.account_ms.models.Transaction;
import com.jhon.account_ms.repository.AccountRepository;
import com.jhon.account_ms.repository.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionController(TransactionRepository transaction,AccountRepository account){
        this.transactionRepository = transaction;
        this.accountRepository = account;
    }

    @PostMapping("/transactions")
    Transaction newTransaction(@RequestBody Transaction transaction){
        Account accountOrigin = accountRepository.findById(transaction.getUsernameOrigin()).orElse(null);
        Account accountDestiny = accountRepository.findById(transaction.getUsernameDestiny()).orElse(null);

        if (accountOrigin == null){
            throw new AccountNotFoundException("No se encontro una cuenta asociada al usuario " + transaction.getUsernameOrigin() + ". Transaccion declinada.");
        }

        if (accountDestiny == null){
            throw new AccountNotFoundException("No se encontro una cuentas asociada al usuario " + transaction.getUsernameDestiny());
        }
        
        if (accountOrigin.getBalance() < transaction.getValue()){
            throw new InsufficientBalanceException("El saldo de la cuenta es insuficiente. Transaccion declinada");
        }

        accountOrigin.setBalance(accountOrigin.getBalance() - transaction.getValue());
        accountOrigin.setLastChance(new Date());
        accountRepository.save(accountOrigin);

        accountDestiny.setBalance(accountDestiny.getBalance() + transaction.getValue());
        accountDestiny.setLastChance(new Date());
        accountRepository.save(accountDestiny);

        transaction.setDate(new Date());

        return transactionRepository.save(transaction);
    }

    @GetMapping("/transactions/{username}")
    List<Transaction> userTransaction(@PathVariable String username){
        Account userAccount = accountRepository.findById(username).orElse(null);
        if(userAccount == null){
            throw new AccountNotFoundException("No se encontro una cuenta asociada al usuario " + username + ". Intente de nuevo");
        }

        List<Transaction> transactionsOrigin = transactionRepository.findByUsernameOrigin(username);
        List<Transaction> transactionsDestiny = transactionRepository.findByUsernameDestiny(username);

        List<Transaction> transactions = Stream.concat(transactionsOrigin.stream(),transactionsDestiny.stream()).collect(Collectors.toList());
        return transactions;
    }


}

