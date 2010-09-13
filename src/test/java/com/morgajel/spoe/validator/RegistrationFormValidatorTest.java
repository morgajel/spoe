package com.morgajel.spoe.validator;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import com.morgajel.spoe.model.Account;



public class RegistrationFormValidatorTest {

	private RegistrationFormValidator rfv;

	@Before
	public void setUp() throws Exception {
		this.rfv = new RegistrationFormValidator();
	}

	@After
	public void tearDown() throws Exception {
		this.rfv = null;
	}

	@Test
	public void testVerifyPassword() {
		assertTrue(rfv.supports(Account.class));
		assertFalse(rfv.supports(Long.class));
	}
	
	@Test
	public void testValidate() {
		//TODO: need to figure out how to test this.
	}
	
}
