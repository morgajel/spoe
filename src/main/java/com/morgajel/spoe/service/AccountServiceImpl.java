package com.morgajel.spoe.service;

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

	  public AccountServiceImpl() {
	  }
	
	  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	  public void addAccount(Account account) {
	    accountDao.saveAccount(account);
	  }

	  public List<Account> listAccounts() {
	    return accountDao.listAccounts();
	  }

	@Override
	public Account findAccountByUsername(String username) {
		return accountDao.findAccountByUsername(username);
		
	}
	

	  
	  
}
