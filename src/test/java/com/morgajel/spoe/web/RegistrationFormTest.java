package com.morgajel.spoe.web;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.apache.velocity.app.VelocityEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContext;

import com.morgajel.spoe.controller.AccountController;
import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Role;
import com.morgajel.spoe.service.AccountService;
import com.morgajel.spoe.service.RoleService;

public class RegistrationFormTest {
	private RegistrationForm registrationForm;
	private static final String username="morgo2";
	private static final String firstname="Jesse";
	private static final String lastname="Morgan";
	private static final String email="morgo2@example.com";
	private static final String password="MatchedLuggage12345";
	@Before
	public void setUp() throws Exception {
		registrationForm=new RegistrationForm();
	}

	@After
	public void tearDown() throws Exception {
		registrationForm=null;
	}

	
	@Test
	public void testGetAndSetUsername() {
		registrationForm.setUsername(username);
		assertEquals(username,registrationForm.getUsername());
	}
	@Test
	public void testGetAndSetConfirmEmail() {
		registrationForm.setConfirmEmail(email);
		assertEquals(email,registrationForm.getConfirmEmail());
	}
	@Test
	public void testGetAndSetFirstname() {
		registrationForm.setFirstname(firstname);
		assertEquals(firstname,registrationForm.getFirstname());
	}

	@Test
	public void testGetAndSetLastname() {
		registrationForm.setLastname(lastname);
		assertEquals(lastname,registrationForm.getLastname());
	}

	@Test
	public void testGetAndSetEmail() {
		registrationForm.setEmail(email);
		assertEquals(email,registrationForm.getEmail());
	}

}
