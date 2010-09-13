package com.morgajel.spoe.dao;

import java.util.Date;
import java.util.List;

import com.morgajel.spoe.model.Account;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveAccount(Account account) {
	    account.setLastAccessDate(new Date());
	    sessionFactory.getCurrentSession().saveOrUpdate(account);
	}
	@Override
	public Account findAccountByUsername(String username){
		//This, my friends, is a clusterfuck
		//TODO clean this up
		Query nq=sessionFactory.getCurrentSession().getNamedQuery("findAccountByUsername");
		Account account= (Account) nq.setString("username", username).list().get(0);
		return account;
	}
	
	@Override
    @SuppressWarnings("unchecked")
    //TODO I don't like suppressed warnings
	public List<Account> listAccounts() {
	    return (List<Account>) sessionFactory.getCurrentSession().createCriteria(Account.class).list();	
	}

}
