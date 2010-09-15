package com.morgajel.spoe.dao;

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

	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
		
	}
	
	@Override
	public void saveAccount(Account account) {
	    account.setLastAccessDate(new Date());
	    sessionFactory.getCurrentSession().saveOrUpdate(account);
	}
	@Override
	public Account loadByUsername(String username){
		//This, my friends, is a clusterfuck
		//TODO refactor with other loadBy*
		Session session=sessionFactory.getCurrentSession();
		Query nq=session.getNamedQuery("findAccountByUsername");
		List accountList= nq.setString("username", username).list();
		return (Account) accountList.get(0);

	}
	
	@Override
	public List<Account> listAccounts() {
	    return (List<Account>) sessionFactory.getCurrentSession().createCriteria(Account.class).list();	
	}
	
	@Override
	public Account loadByUsernameAndPassword(String username, String password) {
		//This is a lot of text to return a simple
		return (Account) sessionFactory.getCurrentSession().getNamedQuery("findAccountByUsernameAndPassword").setString("username", username).setString("password", password).list().get(0);
	}

}
