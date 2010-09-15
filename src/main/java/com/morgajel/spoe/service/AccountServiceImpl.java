package com.morgajel.spoe.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.dao.AccountDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("accountService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AccountServiceImpl implements AccountService {


	@Autowired
	private AccountDao accountDao;

	//TODO unit test
	public AccountServiceImpl() {
	}
	//TODO unit test
	@Override
	public void setAccountDao(AccountDao accountdao){
		this.accountDao=accountdao;  
	}
	//TODO unit test
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addAccount(Account account) {
		accountDao.saveAccount(account);
	}
	public List<Account> listAccounts() {
	    return accountDao.listAccounts();
	}
	@Override
	public Account loadByUsername(String username) {
		return accountDao.loadByUsername(username);
		
	}

    @Override	
    public boolean login(String username, String password){
		boolean valid = false;
		Account results = accountDao.loadByUsernameAndPassword(username, password);
	    if(results != null) {
	    	valid = true;
	    }
		return valid;
    }

	  
	  
}
