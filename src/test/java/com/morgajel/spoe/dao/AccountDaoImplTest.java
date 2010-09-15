package com.morgajel.spoe.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.dao.AccountDaoImpl;

import org.hibernate.SessionFactory;
import static org.mockito.Mockito.*;

public class AccountDaoImplTest {
	private AccountDaoImpl accountDao;
	private SessionFactory mockSessionFactory;

	@Before
	public void setUp() throws Exception {
		this.accountDao = new AccountDaoImpl();
        mockSessionFactory = mock(SessionFactory.class,RETURNS_DEEP_STUBS);
        accountDao.setSessionFactory(mockSessionFactory);
	}

	@After
	public void tearDown() throws Exception {
		this.accountDao = null;
	}
	
	@Test
	public void testSaveAccount() {
		Account result=new Account();
		//FIXME: not sure how to test a void method.
	}
	@Test
	public void testListAccounts(){
		List<Account> results = new ArrayList<Account>();
		results.add(new Account());
		results.add(new Account());
		//using deep stubs, boooo
		when(mockSessionFactory.getCurrentSession().createCriteria(Account.class).list()).thenReturn(results);
		assertEquals(results,accountDao.listAccounts());
	}
	@Test
	public void testLoadByUsername(){
		Account result=new Account();
		String username="realusername";
		//using deep stubs, boooo
		when(mockSessionFactory.getCurrentSession().getNamedQuery("findAccountByUsername").setString("username", username).list().get(0)).thenReturn(result);
		assertEquals(result,accountDao.loadByUsername(username));
	}
	@Test
	public void testLoadByUsernameAndPassword(){
		String username="realusername";
		String password="realpassword";
		Account result =new Account();
		//using deep stubs, boooo
		when(mockSessionFactory.getCurrentSession().getNamedQuery("findAccountByUsernameAndPassword").setString("username", username).setString("password", password).list().get(0)).thenReturn(result);
		assertEquals(result,accountDao.loadByUsernameAndPassword(username, password));
	}
}
