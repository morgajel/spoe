package com.morgajel.spoe.service;

import java.util.List;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.dao.AccountDao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("accountService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AccountServiceImpl implements AccountService {


	@Autowired
	private AccountDao accountDao;
	private transient static Logger logger = Logger.getLogger("com.morgajel.spoe.service.AccountService");

	//TODO unit test
	public AccountServiceImpl() {
	}
	//TODO unit test
	@Override
	public void setAccountDao(AccountDao accountdao){
		this.accountDao=accountdao;  
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addAccount(Account account) {
		//TODO move role addition to here.
		logger.debug("adding account "+account.getUsername());
		accountDao.saveAccount(account);
		logger.debug("added account "+account.getUsername());
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveAccount(Account account) {
		logger.debug("saving account "+account.getUsername());
		accountDao.saveAccount(account);
		logger.debug("saved account "+account.getUsername());
	}
	@Override
	public List<Account> listAccounts() {
	    return accountDao.listAccounts();
	}
	@Override
	public Account loadByUsername(String username) {
		return accountDao.loadByUsername(username);
	}
	@Override
	public Account loadByUsernameAndChecksum(String username,String checksum) {
		logger.debug("attempting to load user by "+username+" and "+checksum );
		return accountDao.loadByUsernameAndChecksum(username,checksum);
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
