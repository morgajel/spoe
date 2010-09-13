package com.morgajel.spoe.controller;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import org.springframework.ui.ModelMap;
import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.controller.AccountController;
import com.morgajel.spoe.service.AccountServiceImpl;
import org.springframework.test.web.*;
import org.springframework.mock.web.MockHttpSession;

//TODO how do you test a controller which uses a service implementation that is created via beans?
public class AccountControllerTest {
	private AccountController accountcontroller;
	private Account account;
	
	@Before
	public void setUp() throws Exception {
		this.accountcontroller = new AccountController();
		this.account = new Account();
	}
	@After
	public void tearDown() throws Exception {
		this.accountcontroller = null;
		this.account = null;
	}

	@Test
	public void testGetRegistrationForm(){
		ModelAndView mav= accountcontroller.getRegistrationForm(account);
		assertEquals(mav.getViewName(),"account/registrationForm");
//		public ModelAndView getRegistrationForm(Account account) 
//			logger.info("getregistrationForm loaded");
//			return new ModelAndView("account/registrationForm");
	}
}
