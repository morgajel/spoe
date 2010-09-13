package com.morgajel.spoe.dao;

import com.morgajel.spoe.model.Account;
import java.util.Date;
import java.util.List;

public interface AccountDao {

	  // To Save the account detail
	  public void saveAccount ( Account account );
	  
	  public Account findAccountByUsername(String username); 
	  
	  // To get list of all accounts
	  public List<Account> listAccounts();
	
}
