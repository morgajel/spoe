package com.morgajel.spoe.service;

import com.morgajel.spoe.model.Account;

import java.util.List;
import com.morgajel.spoe.dao.AccountDao;

public interface AccountService {
    public void addAccount(Account account);
    public Account loadByUsername(String username);
    public Account loadByUsernameAndChecksum(String username,String checksum);
    public void saveAccount(Account account);
    
    public List<Account> listAccounts();
    
    boolean login(String userName, String password);
    public void setAccountDao(AccountDao accountDao);
}
