package com.morgajel.spoe.model;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class RoleTest {
	private Account mockAccount;
	private Role role;

	@Before
	public void setUp() throws Exception {
		role= new Role();
		mockAccount=mock(Account.class);
	}

	@After
	public void tearDown() throws Exception {
		role=null;
		mockAccount=null;
	}

	@Test
	public void testGetAndSetAccounts(){
		Set<Account> mockAccounts = mock(HashSet.class);
		role.setAccounts(mockAccounts);
		assertEquals(mockAccounts,role.getAccounts());
		
	}
//	
//	@Test
//	public void testGetRoleId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetRoleId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testToString() {
//		fail("Not yet implemented");
//	}

}
