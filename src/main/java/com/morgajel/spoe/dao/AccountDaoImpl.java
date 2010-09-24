package com.morgajel.spoe.dao;
import org.apache.log4j.Logger;
import java.util.Date;
import java.util.List;

import com.morgajel.spoe.model.Account;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the AccountDao interface, used to save and load accounts from the datasource
 */
@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {

	@Autowired
	private SessionFactory sessionFactory;
	private transient static Logger logger = Logger.getLogger("com.morgajel.spoe.dao.AccountDao");

	/**
	 * Sets the Session Factory used to get the currentSession. 
	 */
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
		
	}
	/**
	 * returns a query based on a querystring and username, since that appears to be the 
	 * common denominator among load methods. 
	 */
	private Query loadQueryByUsername(String queryString,String username){
		Session session = sessionFactory.getCurrentSession();
		return session.getNamedQuery(queryString).setString("username", username);
	}
	/**
	 * Saves a given account to the datasource. 
	 */
	@Override
	public void saveAccount(Account account) {
		logger.info("setting last access date before saving for "+account.getUsername());
	    account.setLastAccessDate(new Date());
	    sessionFactory.getCurrentSession().saveOrUpdate(account);
	    logger.info("account appears saved");
	}
	/**
	 * Returns a List of all accounts from the datasource with no qualifications. 
	 */
	@Override
	public List<Account> listAccounts() {
		logger.debug("attempting to list accounts" );
	    return (List<Account>) sessionFactory.getCurrentSession().createCriteria(Account.class).list();	
	}

	/**
	 * returns an account matching a given username. will return null if none is found. 
	 */
	@Override
	public Account loadByUsername(String username){
		logger.debug("attempting to load user by "+username+" and password" );
		Account account= (Account) loadQueryByUsername("findAccountByUsername",username).list().get(0);
		logger.info("Loaded account "+account);
		return account;
	}
	/**
	 * returns an account matching a given username and password. Will return null if none is found. 
	 */
	@Override
	public Account loadByUsernameAndPassword(String username, String password) {
		logger.debug("attempting to load user by "+username+" and password" );
		Account account= (Account) loadQueryByUsername("findAccountByUsernameAndPassword", username).setString("password", password).list().get(0);
		logger.info("Loaded account "+account);
		return account;
	}
	/**
	 * returns an account matching a given username and checksum. Will return null if none is found. 
	 */
	@Override
	public Account loadByUsernameAndChecksum(String username, String checksum) {
		//This is a lot of text to return a simple account
		logger.debug("attempting to load user by "+username+" and "+checksum );
		Account account= (Account) loadQueryByUsername("findAccountByUsernameAndChecksum",username).setString("checksum", checksum).list().get(0);
		logger.info("Loaded account "+account);
		return account;
	}
}
