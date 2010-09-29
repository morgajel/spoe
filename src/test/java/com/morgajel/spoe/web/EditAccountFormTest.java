package com.morgajel.spoe.web;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.morgajel.spoe.model.Account;
import static org.mockito.Mockito.*;

public class EditAccountFormTest {
	private EditAccountForm editAccountForm;
	private Account mockAccount;
	private static final String firstname="Jesse";
	private static final String lastname="Morgan";
	private static final String email="morgo2@example.com";
	private static final String password="MatchedLuggage12345";

	@Before
	public void setUp() throws Exception {
		editAccountForm=new EditAccountForm();
		mockAccount=mock(Account.class);
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testGetAndSetConfirmEmail() {
		editAccountForm.setConfirmEmail(email);
		assertEquals(email,editAccountForm.getConfirmEmail());
	}
	@Test
	public void testGetAndSetFirstname() {
		editAccountForm.setFirstname(firstname);
		assertEquals(firstname,editAccountForm.getFirstname());
	}

	@Test
	public void testGetAndSetLastname() {
		editAccountForm.setLastname(lastname);
		assertEquals(lastname,editAccountForm.getLastname());
	}

	@Test
	public void testGetAndSetEmail() {
		editAccountForm.setEmail(email);
		assertEquals(email,editAccountForm.getEmail());
	}
	@Test
	public void testGetAndSetOldPassword() {
		editAccountForm.setOldPassword(password);
		assertEquals(password,editAccountForm.getOldPassword());
	}

	@Test
	public void testGetAndSetPassword() {
		editAccountForm.setPassword(password);
		assertEquals(password,editAccountForm.getPassword());
	}
	@Test
	public void testCompareEmails() {
		editAccountForm.setEmail(email);
		editAccountForm.setConfirmEmail(email);
		assertTrue(editAccountForm.compareEmails());
		editAccountForm.setConfirmEmail("bad@email.com");
		assertFalse(editAccountForm.compareEmails());
	}
	@Test
	public void testCompareNewPasswords() {
		editAccountForm.setPassword(password);
		editAccountForm.setConfirmPassword(password);
		assertTrue(editAccountForm.compareNewPasswords());
		editAccountForm.setConfirmPassword("off password");
		assertFalse(editAccountForm.compareNewPasswords());

	}
	
	
	
	@Test
	public void testLoadAccount() {
		when(mockAccount.getFirstname()).thenReturn(firstname);
		when(mockAccount.getLastname()).thenReturn(lastname);
		when(mockAccount.getPassword()).thenReturn(password);
		when(mockAccount.getEmail()).thenReturn(email);
		
		editAccountForm.setEmail("original@email.com");
		editAccountForm.setConfirmEmail("originalConfirm@email.com");
		editAccountForm.setOldPassword("oldpassword");
		editAccountForm.setPassword("newpassword");
		editAccountForm.setConfirmPassword("confirmpassword");
		
		editAccountForm.loadAccount(mockAccount);
		
		assertEquals(email,editAccountForm.getEmail());
		assertEquals(email,editAccountForm.getConfirmEmail());
		assertEquals("",editAccountForm.getOldPassword());
		assertEquals(editAccountForm.passwordHolder,editAccountForm.getPassword());
		assertEquals(editAccountForm.passwordHolder,editAccountForm.getConfirmPassword());
		

	}

}
