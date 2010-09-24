package com.morgajel.spoe.service;

import com.morgajel.spoe.model.Account;

import java.util.List;
import com.morgajel.spoe.dao.AccountDao;
/** 
 * Account Service for managing AccountDao 
 */
public interface AccountService {
	/** 
	 * Adds an account object to the datastore. 
	 */
    public void addAccount(Account account);
    /** 
	 * Returns an account object matching the given username.
	 */
    public Account loadByUsername(String username);
    /** 
	 * Returns an account object if the Username and checksum match. 
	 */
    public Account loadByUsernameAndChecksum(String username,String checksum);
    /** 
	 * Saves details of an account to the datastore.  
	 */
    public void saveAccount(Account account);
    /** 
	 * List all accounts currently in the system. 
	 * TODO: Has the potential to be large, may need to paginate results. 
	 */
    public List<Account> listAccounts();
    /** 
	 * Returns whether or not the users credentials check out. 
	 */    
    boolean login(String userName, String password);
    /** 
	 * Sets the AccountDao for this object so we can access the datastore. 
	 */
    public void setAccountDao(AccountDao accountDao);
}