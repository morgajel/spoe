package com.morgajel.spoe.service;

import com.morgajel.spoe.model.Account;

import java.util.List;

public interface AccountService {
    public void addAccount(Account account);
    public Account findAccountByUsername(String username);
    
    public List<Account> listAccounts();
    
    
}