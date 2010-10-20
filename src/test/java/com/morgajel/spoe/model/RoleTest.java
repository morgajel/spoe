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
    private Long roleId = 123123123L;
    private String rolename = "ROLE_REVIEWER";

    @Before
    public void setUp() throws Exception {
        role = new Role();
        mockAccount = mock(Account.class);
    }

    @After
    public void tearDown() throws Exception {
        role = null;
        mockAccount = null;
    }

    @Test
    public void testGetAndSetAccounts() {
        HashSet<Account> mockAccounts = mock(HashSet.class);
        role.setAccounts(mockAccounts);
        assertEquals(mockAccounts, role.getAccounts());
    }

    @Test
    public void testGetAndSetRoleId() {
        assertNull(role.getRoleId());
        role.setRoleId(roleId);
        assertEquals(roleId, role.getRoleId());
    }

    @Test
    public void testGetAndSetName() {
        //TODO setRoleName vs setRoleID vs AccountName vs. AccountID, pick a convention.
        assertNull(role.getName());
        role.setName(rolename);
        assertEquals(rolename, role.getName());

    }
    @Test
    public void testAddAccount() {
        assertFalse(role.getAccounts().contains(mockAccount));
        assertTrue(role.getAccounts().isEmpty());
        role.addAccount(mockAccount);
        assertTrue(role.getAccounts().contains(mockAccount));
    }

    @Test
    public void testToString() {
        role.setName(rolename);
        role.setRoleId(roleId);
        String toString = "Role "
                    + "[ roleId=" + roleId
                    + ", name=" + rolename
                    +  "]";
        assertEquals(toString, role.toString());
    }

}
