package com.morgajel.spoe.domain;

import static org.junit.Assert.*;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AccountTest {
	private Account account;
	
	@Before
	public void setUp() throws Exception {
        account = new Account();
	}

	@After
	public void tearDown() throws Exception {
		account=null;
	}
	
	@Test
	public void testGetAndSetAccountId() {
        Long accountId=new Long("123123123");
        assertNull(account.getAccountId());
        account.setAccountId(accountId);
        assertEquals(accountId,account.getAccountId());
	}

	@Test
	public void testGetAndSetEmail() {
        String testEmail="foo@bar.com";
        assertNull(account.getEmail());
        account.setEmail(testEmail);
        assertEquals(testEmail,account.getEmail());
	}
	@Test
	public void testGetAndSetConfirmEmail() {
        String testConfirmEmail="foo@bar.com";
        assertNull(account.getConfirmEmail());
        account.setConfirmEmail(testConfirmEmail);
        assertEquals(testConfirmEmail,account.getConfirmEmail());
	}
	@Test
	public void testEmailConfirmation() {
        String testConfirmEmail="foo@bar.com";
        String testEmail="foo@bar.com";
        String testBadConfirmEmail="fooyou@bar.com";
        assertNull(account.getConfirmEmail());
        assertNull(account.getEmail());
        //Verify same emails are the same
        account.setConfirmEmail(testConfirmEmail);
        account.setEmail(testEmail);
        assertEquals(account.getEmail(),account.getConfirmEmail());
        assertTrue(account.compareEmail());
        //Verify same emails are different        
        account.setConfirmEmail(testBadConfirmEmail);
        assertFalse( account.compareEmail());
	}
	
	@Test
	public void testGetAndSetUsername() {
        String testUsername="BobDole";
        assertNull(account.getUsername());
        account.setUsername(testUsername);
        assertEquals(testUsername,account.getUsername());
	}

	@Test
	public void testGetAndSetPassword() {
        String testPassword="12345MatchedLuggage";
        assertNull(account.getPassword());
        account.setPassword(testPassword);
        assertEquals(testPassword,account.getPassword());
	}

	@Test
	public void testGetAndSetEnabled() {
        assertNull(account.getEnabled());
        account.setEnabled(true);
        assertTrue(account.getEnabled());
        account.setEnabled(false);
        assertFalse(account.getEnabled());        
	}

	@Test
	public void testGetAndSetFirstname() {
        String testFirstname="Bob";
        assertNull(account.getFirstname());
        account.setFirstname(testFirstname);
        assertEquals(testFirstname,account.getFirstname());
	}

	@Test
	public void testGetAndSetLastname() {
        String testLastname="Dole";
        assertNull(account.getLastname());
        account.setLastname(testLastname);
        assertEquals(testLastname,account.getLastname());
	}

	@Test
	public void testGetAndSetLastAccessDate() {
        Date testDate=new Date();
        assertNull(account.getLastAccessDate());
        account.setLastAccessDate(testDate);
        assertEquals(testDate,account.getLastAccessDate());
	}

	@Test
	public void testGetAndSetCreationDate() {
        Date testDate=new Date();
        assertNull(account.getCreationDate());
        account.setCreationDate(testDate);
        assertEquals(testDate,account.getCreationDate());
	}

	@Test
	public void testToString() {
        Long accountId=new Long("123123123");
		account.setAccountId(accountId);
		Date creationDate=new Date();
		account.setCreationDate(creationDate);
		Date lastAccessDate=new Date();
		account.setLastAccessDate(lastAccessDate);
		String email="bob@example.com";
		account.setEmail(email);
		boolean enabled=true;
		account.setEnabled(enabled);
		String firstname="Bob";
		account.setFirstname(firstname);
		String lastname="Dole";
		account.setLastname(lastname);
		String password=("password1");
		account.setPassword(password);
		String username="bobdole";
		account.setUsername(username);
		
		String toString="Account [accountId=" + accountId + ", creationDate="
		+ creationDate + ", email=" + email + ", enabled=" + enabled
		+ ", firstname=" + firstname + ", lastAccessDate="
		+ lastAccessDate + ", lastname=" + lastname + ", password="
		+ password + ", username=" + username + "]";
	
		assertEquals(toString, account.toString());
	}
}
