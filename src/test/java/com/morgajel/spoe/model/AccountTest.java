package com.morgajel.spoe.model;

import static org.junit.Assert.*;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.morgajel.spoe.model.Account;

public class AccountTest {
	private Account account;

	@Before
	public void setUp() throws Exception {
		account = new Account();
	}

	@After
	public void tearDown() throws Exception {
		account = null;
	}

//	verifyPassword(String)
	@Test
	public void testVerifyPassword() {
		String password="123123";
		account.setHashedPassword(password);
		assertTrue(account.verifyPassword(password));
		assertFalse(account.verifyPassword("not it"));
	}
//	Account()
	@Test
	public void testAccountConstructor() {
		Account account = new Account();
		assertFalse(account.getEnabled());
	}
//	getAccountId()
//	setAccountId(Long)
	@Test
	public void testGetAndSetAccountId() {
		Long accountId = new Long("123123123");
		assertNull(account.getAccountId());
		account.setAccountId(accountId);
		assertEquals(accountId, account.getAccountId());
	}
//	getEmail()
//	setEmail(String)
	@Test
	public void testGetAndSetEmail() {
		//TODO, make sure non-email addresses don't validate
		String testEmail = "foo@bar.com";
		assertNull(account.getEmail());
		account.setEmail(testEmail);
		assertEquals(testEmail, account.getEmail());
		//TODO this should error if you pass it a non-email address.
	}
//	getUsername()
//	setUsername(String)
	@Test
	public void testGetAndSetUsername() {
		String testUsername = "BobDole";
		assertNull(account.getUsername());
		account.setUsername(testUsername);
		assertEquals(testUsername, account.getUsername());
		//TODO this should error if you pass it a non-alphanumeric username
	}
//	getPassword()
//	setPassword(String)
	@Test
	public void testGetAndSetPassword() {
		String testPassword = "12345MatchedLuggage";
		assertNull(account.getPassword());
		account.setHashedPassword(testPassword);
		assertEquals(Account.hashText(testPassword), account.getPassword());
	}
//	getEnabled()
//	setEnabled(Boolean)
	@Test
	public void testGetAndSetEnabled() {
		assertFalse(account.getEnabled());
		account.setEnabled(true);
		assertTrue(account.getEnabled());
		account.setEnabled(false);
		assertFalse(account.getEnabled());
	}
//	getFirstname()
//	setFirstname(String)
	@Test
	public void testGetAndSetFirstname() {
		String testFirstname = "Bob";
		assertNull(account.getFirstname());
		account.setFirstname(testFirstname);
		assertEquals(testFirstname, account.getFirstname());
	}
//	getLastname()
//	setLastname(String)
	@Test
	public void testGetAndSetLastname() {
		String testLastname = "Dole";
		assertNull(account.getLastname());
		account.setLastname(testLastname);
		assertEquals(testLastname, account.getLastname());
	}
//	getLastAccessDate()
//	setLastAccessDate(Date)
	@Test
	public void testGetAndSetLastAccessDate() {
		Date testDate = new Date();
		assertNotNull(account.getLastAccessDate());
		account.setLastAccessDate(testDate);
		assertEquals(testDate, account.getLastAccessDate());
	}
//	getCreationDate()
//	setCreationDate(Date)
	@Test
	public void testGetAndSetCreationDate() {
		Date testDate = new Date();
		//TODO should this really be null?
		assertNotNull(account.getCreationDate());
		//TODO should this really be settable?
		account.setCreationDate(testDate);
		assertEquals(testDate, account.getCreationDate());
	}
//	toString()
	@Test
	public void testToString() {
		Long accountId = new Long("123123123");
		account.setAccountId(accountId);
		Date creationDate = new Date();
		account.setCreationDate(creationDate);
		Date lastAccessDate = new Date();
		account.setLastAccessDate(lastAccessDate);
		String email = "bob@example.com";
		account.setEmail(email);
		boolean enabled = true;
		account.setEnabled(enabled);
		String firstname = "Bob";
		account.setFirstname(firstname);
		String lastname = "Dole";
		account.setLastname(lastname);
		String password = ("password1");
		account.setHashedPassword(password);
		String username = "bobdole";
		account.setUsername(username);
		String toString = "Account "
		+ "[ accountId=" + accountId 
		+ ", username=" + username
		+ ", email=" + email
		+ ", password="	+ Account.hashText(password)
		+ ", enabled=" + enabled
		+ ", firstname=" + firstname
		+ ", lastname=" + lastname
		+ ", lastAccessDate=" +	lastAccessDate
		+ ", creationDate=" + creationDate
		+ ", PASSWDCHARSET=" + Account.PASSWDCHARSET
		+ ", ALGORITHM=" + Account.ALGORITHM
		+  "]";

		//FIXME fix this.
		assertEquals(toString, account.toString());
	}
}
