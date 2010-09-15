package com.morgajel.spoe.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Before;

import com.morgajel.spoe.dao.AccountDao;
import com.morgajel.spoe.model.Account;

import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    private AccountDao mockDao;
	private AccountServiceImpl service;
	
	@Before
	public void setUp() throws Exception {
		service = new AccountServiceImpl();
        mockDao = mock(AccountDao.class);
        service.setAccountDao(mockDao);
	}

	@Test
	public void testAddAccount() {
		//accservice.addAccount(account);
		//TODO I have no idea how to test this.
	}

	@Test
	public void testListAccount() {
		
		List<Account> results = new ArrayList<Account>();
		results.add(new Account());
		results.add(new Account());
		when(mockDao.listAccounts()).thenReturn(results);
		assertEquals(results,service.listAccounts());
	}

	@Test
	public void testFindAccountByUsername() {
		String username = "testUserName";
		Account results = new Account();
		when(mockDao.loadByUsername(username)).thenReturn(results);
		assertEquals(service.loadByUsername(username), results);
		when(mockDao.loadByUsername(username)).thenReturn(null);
		assertNull(service.loadByUsername(username));
	}
	
    public void testServiceLogin() {
        Account results = new Account();
        String username = "testUserName";
        String password = "testPassword";

        when(mockDao.loadByUsernameAndPassword(username, password)).thenReturn(results);
        assertTrue(service.login(username, password));
        when(mockDao.loadByUsernameAndPassword(username, password)).thenReturn(null);
        assertFalse(service.login(username, password));
   }
}
