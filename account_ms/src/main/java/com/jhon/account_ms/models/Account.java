package com.jhon.account_ms.models;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Account {

    @Id
    private String username;
    private Integer balance;
    private Date lastChance;

    public Account(String username, Integer balance, Date lastChance) {
        this.username = username;
        this.balance = balance;
        this.lastChance = lastChance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Date getLastChance() {
        return lastChance;
    }

    public void setLastChance(Date lastChance) {
        this.lastChance = lastChance;
    }
}
