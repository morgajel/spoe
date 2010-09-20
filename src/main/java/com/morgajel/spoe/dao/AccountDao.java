package com.morgajel.spoe.dao;

import com.morgajel.spoe.model.Account;
import java.util.List;

public interface AccountDao {

	  // To Save the account detail
	  public void saveAccount ( Account account );
	  
	  // To get list of all accounts
	  public List<Account> listAccounts();
	  public Account loadAccountFromSession();
	  public Account loadByUsername(String username);
	  public Account loadByUsernameAndPassword(String userName, String password);
	  public Account loadByUsernameAndChecksum(String userName, String checksum);
}
