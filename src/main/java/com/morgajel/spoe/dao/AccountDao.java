package com.morgajel.spoe.dao;

import com.morgajel.spoe.model.Account;
import java.util.List;
/**
 * Hibernate object interface used for storing and loading Accounts.
 */
public interface AccountDao {

	/**
	 * Save a provided account to the datasource.
	 */
	public void saveAccount ( Account account );
	  
	/**
	 * List all accounts in the datasource
	 */
	public List<Account> listAccounts();

	/**
	 * Return an account with a given username, or else null.
	 */
	public Account loadByUsername(String username);

	/**
	 * Return an account with a given username and password, or else null.
	 */
	public Account loadByUsernameAndPassword(String userName, String password);

	/**
	 * Return an account with a given username and checksum, or else null
	 */
	public Account loadByUsernameAndChecksum(String userName, String checksum);
}
