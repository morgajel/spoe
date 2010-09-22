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

@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {

	@Autowired
	private SessionFactory sessionFactory;
	private transient static Logger logger = Logger.getLogger("com.morgajel.spoe.dao.AccountDao");

	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
		
	}
	@Override
	public void saveAccount(Account account) {
		logger.info("setting last access date before saving for "+account.getUsername());
	    account.setLastAccessDate(new Date());
	    sessionFactory.getCurrentSession().saveOrUpdate(account);
	    logger.info("account appears saved");
	}
	@Override
	public Account loadByUsername(String username){
		//This, my friends, is a clusterfuck
		//TODO refactor with other loadBy*
		logger.info("loading accountDao"+username);
		Session session=sessionFactory.getCurrentSession();
		Query nq=session.getNamedQuery("findAccountByUsername");
		List accountList= nq.setString("username", username).list();
		Account account= (Account)accountList.get(0);
		logger.info("++++ loaded account"+account);
		return account;

	}
	
	@Override
	public List<Account> listAccounts() {
	    return (List<Account>) sessionFactory.getCurrentSession().createCriteria(Account.class).list();	
	}
	
	@Override
	public Account loadByUsernameAndPassword(String username, String password) {
		//This is a lot of text to return a simple account
		return (Account) sessionFactory.getCurrentSession().getNamedQuery("findAccountByUsernameAndPassword").setString("username", username).setString("password", password).list().get(0);
	}
	@Override
	public Account loadByUsernameAndChecksum(String username, String checksum) {
		//This is a lot of text to return a simple account
		logger.debug("attempting to load user by "+username+" and "+checksum );
		Session session=sessionFactory.getCurrentSession();
		logger.debug("snagged current session "+session );
		Query nq=session.getNamedQuery("findAccountByUsernameAndChecksum");
		logger.debug("snagged current quer y"+nq );
		List accountList= nq.setString("username", username).setString("checksum", checksum).list();
		logger.debug("snagged accountList "+accountList );
		Account account= (Account)accountList.get(0);
		logger.info("++++ loaded account "+account);
		return account;

	}
}
