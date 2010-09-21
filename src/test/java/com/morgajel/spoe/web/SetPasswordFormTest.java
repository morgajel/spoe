package com.morgajel.spoe.web;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SetPasswordFormTest {

	SetPasswordForm passwordForm ;
	@Before
	public void setUp() throws Exception {
		 passwordForm=new SetPasswordForm();
	}

	@After
	public void tearDown() throws Exception {
		passwordForm=null;
	}

	@Test
	public final void testSetPasswordForm() {
	}

	@Test
	public final void testSetAndGetChecksum() {
		String checksum = "1234567890123456789012345678901234567890";
		assertNull(passwordForm.getChecksum());
		passwordForm.setChecksum(checksum);
		assertEquals(checksum, passwordForm.getChecksum());
	}

	@Test
	public final void testSetAndGetUsername() {
		String username = "bobdole";
		assertNull(passwordForm.getUsername());
		passwordForm.setUsername(username);
		assertEquals(username, passwordForm.getUsername());
	}

	@Test
	public final void testSetAndGetPassword() {
		String password = "password123";
		assertNull(passwordForm.getPassword());
		passwordForm.setPassword(password);
		assertEquals(password, passwordForm.getPassword());
	}

	@Test
	public final void testSetAndGetConfirmPassword() {
		String confirmPassword = "password123";
		assertNull(passwordForm.getConfirmPassword());
		passwordForm.setConfirmPassword(confirmPassword);
		assertEquals(confirmPassword, passwordForm.getConfirmPassword());
	}

	@Test
	public final void testComparePasswords() {
		String password = "password123";
		String badPassword = "badpassword";
		passwordForm.setPassword(password);
		passwordForm.setConfirmPassword(password);
		assertTrue(passwordForm.comparePasswords());
		passwordForm.setConfirmPassword(badPassword);
		assertFalse(passwordForm.comparePasswords());
	}

}
