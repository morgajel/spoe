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
	private Account mockAccount;
	private static final String username="morgo2";
	//private final String passfield="255edd2793e5286d4441ea6bfba734b59e915864";
	private static final String tempHash="df9dd14cbdb3b00f8a54b66f489241e8aeb903ff";
	private static final String checksum="279d8d8a18b94782ef606fbbadd6c011b1692ad0"; //morgo2+temphash+0
	@Before
	public void setUp() throws Exception {
        mockSessionFactory = mock(SessionFactory.class,RETURNS_DEEP_STUBS);
        mockAccount=mock(Account.class);
        accountDao = new AccountDaoImpl();
        accountDao.setSessionFactory(mockSessionFactory);
        
	}

	@After
	public void tearDown() throws Exception {
		this.accountDao = null;
		mockSessionFactory=null;
		mockAccount=null;
	}

	@Test
	public void testSaveAccount(){
		accountDao.saveAccount(mockAccount);
		verify(mockAccount).setLastAccessDate((Date) anyObject());
		verify(mockSessionFactory).getCurrentSession();
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
//		List<Account> acclist=new ArrayList<Account>();
//		acclist.add(mockAccount);
//		when(mockSessionFactory.getCurrentSession().getNamedQuery("findAccountByUsername").setString("username", username).list()).thenReturn(acclist);
//		Account account=accountDao.loadByUsernameAndChecksum(username, checksum);
//		assertEquals(mockAccount,account);
	}
	@Test
	public void testLoadByUsernameAndPassword(){
		List<Account> acclist=new ArrayList<Account>();
		acclist.add(mockAccount);
		when(mockSessionFactory.getCurrentSession().getNamedQuery("findAccountByUsernameAndPassword")
				.setString("username", username).setString("password", tempHash).list()).thenReturn(acclist);
		Account account=accountDao.loadByUsernameAndPassword(username, tempHash);
		assertEquals(mockAccount,account);

	}

	@Test
	public void testLoadByUsernameAndChecksum(){
		List<Account> acclist=new ArrayList<Account>();
		acclist.add(mockAccount);
		when(mockSessionFactory.getCurrentSession().getNamedQuery("findAccountByUsernameAndChecksum").setString("username", username).setString("checksum", checksum).list()).thenReturn(acclist);
		Account account=accountDao.loadByUsernameAndChecksum(username, checksum);
		assertEquals(mockAccount,account);
	}

}
