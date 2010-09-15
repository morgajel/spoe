package com.morgajel.spoe.controller;

import static org.junit.Assert.*;


import org.apache.velocity.app.VelocityEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.ModelAndView;
import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.controller.AccountController;
import static org.mockito.Mockito.*;

//TODO how do you test a controller which uses a service implementation that is created via beans?
public class AccountControllerTest {
	private AccountController accountcontroller;
	private Account account;
	
	@Before
	public void setUp() throws Exception {
		accountcontroller = new AccountController();
		account = mock(Account.class);
	}
	@After
	public void tearDown() throws Exception {
		accountcontroller = null;
		account = null;
	}

	

	@Test
	public void testSetAndGetTemplateMessage(){ 
		SimpleMailMessage simpleMailMessage = mock(SimpleMailMessage.class);
		accountcontroller.setTemplateMessage(simpleMailMessage);
		//using deep stubs, boooo
		assertEquals(accountcontroller.getTemplateMessage(),simpleMailMessage);
	}		
	@Test
	public void testSetAndGetMailSender(){ 
		MailSender mailSender = mock(MailSender.class);
		accountcontroller.setMailSender(mailSender);
		//using deep stubs, boooo
		assertEquals(accountcontroller.getMailSender(),mailSender);
	}		
	
	@Test
	public void testSetAndGetVelocityEngine(){ 
		VelocityEngine velocityEngine = mock(VelocityEngine.class);
		accountcontroller.setVelocityEngine(velocityEngine);
		//using deep stubs, boooo
		assertEquals(accountcontroller.getVelocityEngine(),velocityEngine);
	}	
	@Test
	public void testGetRegistrationForm(){
		ModelAndView mav= accountcontroller.getRegistrationForm(account);
		assertEquals(mav.getViewName(),"account/registrationForm");
	}
}
