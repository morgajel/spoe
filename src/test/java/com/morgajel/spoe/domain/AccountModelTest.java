package com.morgajel.spoe.domain;

import static org.junit.Assert.*;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AccountModelTest {
	private AccountModel model;
	
	@Before
	public void setUp() throws Exception {
        model = new AccountModel();
	}

	@After
	public void tearDown() throws Exception {
		model=null;
	}
	
	@Test
	public void testGetAndSetAccountId() {
        Integer testNum=123123123;
        assertNull(model.getAccountId());
        model.setAccountId(testNum);
        assertEquals(testNum,model.getAccountId());
	}

	@Test
	public void testGetAndSetEmail() {
        String testEmail="foo@bar.com";
        assertNull(model.getEmail());
        model.setEmail(testEmail);
        assertEquals(testEmail,model.getEmail());
	}

	@Test
	public void testGetAndSetUsername() {
        String testUsername="BobDole";
        assertNull(model.getUsername());
        model.setUsername(testUsername);
        assertEquals(testUsername,model.getUsername());
	}


	@Test
	public void testGetAndSetPassword() {
        String testPassword="12345MatchedLuggage";
        assertNull(model.getPassword());
        model.setPassword(testPassword);
        assertEquals(testPassword,model.getPassword());
	}

	@Test
	public void testGetAndSetEnabled() {
        assertNull(model.getEnabled());
        model.setEnabled(true);
        assertTrue(model.getEnabled());
        model.setEnabled(false);
        assertFalse(model.getEnabled());        
	}

	@Test
	public void testGetAndSetFirstname() {
        String testFirstname="Bob";
        assertNull(model.getFirstname());
        model.setFirstname(testFirstname);
        assertEquals(testFirstname,model.getFirstname());
	}

	@Test
	public void testGetAndSetLastname() {
        String testLastname="Dole";
        assertNull(model.getLastname());
        model.setLastname(testLastname);
        assertEquals(testLastname,model.getLastname());
	}

	@Test
	public void testGetAndSetLastAccessDate() {
        Date testDate=new Date();
        assertNull(model.getLastAccessDate());
        model.setLastAccessDate(testDate);
        assertEquals(testDate,model.getLastAccessDate());
	}

	@Test
	public void testGetAndSetCreationDate() {
        Date testDate=new Date();
        assertNull(model.getCreationDate());
        model.setCreationDate(testDate);
        assertEquals(testDate,model.getCreationDate());
	}

}
