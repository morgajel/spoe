package com.morgajel.spoe.controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.morgajel.spoe.domain.Account;


public class AccountControllerTest {
	private AccountController accountcontroller;
	private Account account;

	@Before
	public void setUp() throws Exception {
		this.accountcontroller=new AccountController();
		this.account=new Account();
	}

	@After
	public void tearDown() throws Exception {
		this.accountcontroller=null;
		this.account=null;
	}

	@Test
	public void getRegistrationFormTest() {
//		assertNull(accountcontroller.getMav());
//	    accountcontroller.getRegistrationForm(account);
//	    assertNotNull(accountcontroller.getMav());
	    
       
	}
	
	
	
	
	
}
