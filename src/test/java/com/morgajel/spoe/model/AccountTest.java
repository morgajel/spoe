package com.morgajel.spoe.model;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class AccountTest {
    private Account account;
    private Date mockDate;
    private Role mockRole;
    private String password = "12345MatchedLuggage";
    private String passwordHash = "2ddcee8b16fc4740c3d31db70d65b073c10b7c3f";
    private Long accountId = new Long("123123123");
    private String username = "bobDole";
    private String email = "foo@bar.com";
    private String firstname = "Bob";
    private String lastname = "Dole";
    @Before
    public void setUp() throws Exception {
        mockDate = mock(Date.class);
        mockRole = mock(Role.class);
        account = new Account();
    }

    @After
    public void tearDown() throws Exception {
        account = null;
    }


    @Test
    public void testVerifyPassword() {
        account.setHashedPassword(password);
        assertTrue(account.verifyPassword(password));
        assertFalse(account.verifyPassword("not it"));
    }

    @Test
    public void testAccountConstructor() {
        Account acc = new Account();
        assertFalse(acc.getEnabled());
    }

    @Test
    public void testGetAndSetAccountId() {
        assertNull(account.getAccountId());
        account.setAccountId(accountId);
        assertEquals(accountId, account.getAccountId());
    }

    @Test
    public void testGetAndSetEmail() {
        //TODO, make sure non-email addresses don't validate
        assertNull(account.getEmail());
        account.setEmail(email);
        assertEquals(email, account.getEmail());
        //TODO this should error if you pass it a non-email address.
    }

    @Test
    public void testGetAndSetUsername() {
        assertNull(account.getUsername());
        account.setUsername(username);
        assertEquals(username, account.getUsername());
        //TODO this should error if you pass it a non-alphanumeric username
    }

    @Test
    public void testGetAndSetPassword() {
        assertNull(account.getPassword());
        account.setHashedPassword(password);
        assertEquals(Account.hashText(password), account.getPassword());
    }

    @Test
    public void testGetAndSetEnabled() {
        assertFalse(account.getEnabled());
        account.setEnabled(true);
        assertTrue(account.getEnabled());
        account.setEnabled(false);
        assertFalse(account.getEnabled());
    }

    @Test
    public void testGetAndSetFirstname() {
        assertNull(account.getFirstname());
        account.setFirstname(firstname);
        assertEquals(firstname, account.getFirstname());
    }

    @Test
    public void testGetAndSetLastname() {
        assertNull(account.getLastname());
        account.setLastname(lastname);
        assertEquals(lastname, account.getLastname());
    }

    @Test
    public void testGetAndSetLastModifiedDate() {
        when(mockDate.clone()).thenReturn(mockDate);
        assertNotNull(account.getLastModifiedDate());
        account.setLastModifiedDate(mockDate);
        assertEquals(mockDate, account.getLastModifiedDate());
    }

    @Test
    public void testGetAndSetCreationDate() {
        when(mockDate.clone()).thenReturn(mockDate);
        assertNotNull(account.getCreationDate());
        account.setCreationDate(mockDate);
        assertEquals(mockDate, account.getCreationDate());
    }

    @Test
    public void testGetAndSetRoles() {
        HashSet<Role> mockRoles = mock(HashSet.class);
        account.setRoles(mockRoles);
        assertEquals(mockRoles, account.getRoles());
    }

    @Test
    public void testHashText() {
        String hash = Account.hashText(password);
        assertEquals(passwordHash, hash);
        //HULK SMASH, no way to test a missing algorithm
    }

    @Test
    public void textGeneratePasswordSmall() {

        String pass = Account.generatePassword(Account.MAXLENGTH - 1);
        assertEquals(Account.MAXLENGTH - 1, pass.length());
    }

    @Test
    public void textGeneratePasswordTooSmall() {
        String pass = Account.generatePassword(-1);
        assertEquals(Account.MAXLENGTH, pass.length());
    }

    @Test
    public void textGeneratePasswordTooBig() {
        String pass = Account.generatePassword(Account.MAXLENGTH + 1);
        assertEquals(Account.MAXLENGTH, pass.length());
    }

    @Test
    public void testAddRole() {
        assertFalse(account.getRoles().contains(mockRole));
        assertTrue(account.getRoles().isEmpty());
        account.addRole(mockRole);
        assertTrue(account.getRoles().contains(mockRole));
    }

    @Test
    public void testActivationChecksum() {
        account.setHashedPassword(password);
        account.setUsername(username);
        account.setEnabled(true);
        assertEquals("511fc691aae67b6af1f5a2e033db5cf474813ea2", account.activationChecksum());
        account.setEnabled(false);
        assertEquals("28a50a5f40f0817a391b8928c75c164a2f60adc6", account.activationChecksum());

    }

    @Test
    public void testToString() {
        account.setAccountId(accountId);
        Date creationDate = new Date();
        account.setCreationDate(creationDate);
        Date lastModifiedDate = new Date();
        account.setLastModifiedDate(lastModifiedDate);
        account.setEmail(email);
        boolean enabled = true;
        account.setEnabled(enabled);
        account.setFirstname(firstname);
        account.setLastname(lastname);
        account.setHashedPassword(password);
        account.setUsername(username);
        String toString = "Account "
        + "[ accountId=" + accountId
        + ", username=" + username
        + ", email=" + email
        + ", password="    + Account.hashText(password)
        + ", enabled=" + enabled
        + ", firstname=" + firstname
        + ", lastname=" + lastname
        + ", lastModifiedDate=" +    lastModifiedDate
        + ", creationDate=" + creationDate
        + ", PASSWDCHARSET=" + Account.PASSWDCHARSET
        + ", ALGORITHM=" + Account.ALGORITHM
        +  "]";

        //FIXME fix this.
        assertEquals(toString, account.toString());
    }
}
