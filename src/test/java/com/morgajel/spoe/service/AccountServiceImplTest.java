package com.morgajel.spoe.service;


import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.springframework.validation.Errors;
import com.morgajel.spoe.model.Account;

import java.util.List;


//--


public class AccountServiceImplTest {


	private AccountServiceImpl accservice;
	
	@Before
	public void setUp() throws Exception {
		accservice = new AccountServiceImpl();
	}
	@Test
	public void testAddAccount() {
		Account account = new Account();
		//accservice.addAccount(account);
		//TODO I have no idea how to test this.
	}
	@Test
	public void testListAccount() {
		//assertNotNull(accservice.listAccounts()) ;
		//TODO I have no idea how to test this.
	}
	@Test
	public void testFindAccountByUsername() {
		
		//assertNotNull(service.findAccountByUsername("goodusername")) ;
		//assertNull(service.findAccountByUsername("badusername")) ;
		//TODO I have no idea how to test this.
	}
	
}
