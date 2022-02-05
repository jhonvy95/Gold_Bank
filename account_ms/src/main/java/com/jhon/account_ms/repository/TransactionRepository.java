package com.jhon.account_ms.repository;

import com.jhon.account_ms.models.Account;
import com.jhon.account_ms.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction,String> {
    List<Transaction> findByUsernameOrigin(String usernameOrigin);
    List<Transaction> findByUsernameDestiny(String usernameDestiny);
}
