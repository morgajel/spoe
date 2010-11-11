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
    private Snippet mockSnippet;
    private static final String PASSWORD = "12345MatchedLuggage";
    private static final String PASSWORDHASH = "2ddcee8b16fc4740c3d31db70d65b073c10b7c3f";
    private static final Long ACCOUNTID = 123123123L;
    private static final String USERNAME = "bobDole";
    private static final String EMAIL = "foo@bar.com";
    private static final String FIRSTNAME = "Bob";
    private static final String LASTNAME = "Dole";

    @Before
    public void setUp() throws Exception {
        mockDate = mock(Date.class);
        mockRole = mock(Role.class);
        mockSnippet = mock(Snippet.class);
        account = new Account();
    }

    @After
    public void tearDown() throws Exception {
        account = null;
    }

    @Test
    public void testAddSnippet() {
        HashSet<Snippet> snippetSet = new HashSet<Snippet>();
        account.setSnippets(snippetSet);
        assertFalse(account.getSnippets().contains(mockSnippet));
        assertEquals(snippetSet, account.getSnippets());
        account.addSnippet(mockSnippet);
        assertTrue(account.getSnippets().contains(mockSnippet));
    }

    @Test
    public void testVerifyPassword() {
        account.setHashedPassword(PASSWORD);
        assertTrue(account.verifyPassword(PASSWORD));
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
        account.setAccountId(ACCOUNTID);
        assertEquals(ACCOUNTID, account.getAccountId());
    }

    @Test
    public void testGetAndSetEmail() {
        //TODO, make sure non-email addresses don't validate
        assertNull(account.getEmail());
        account.setEmail(EMAIL);
        assertEquals(EMAIL, account.getEmail());
        //TODO this should error if you pass it a non-email address.
    }

    @Test
    public void testGetAndSetUsername() {
        assertNull(account.getUsername());
        account.setUsername(USERNAME);
        assertEquals(USERNAME, account.getUsername());
        //TODO this should error if you pass it a non-alphanumeric username
    }

    @Test
    public void testGetAndSetPassword() {
        assertNull(account.getPassword());
        account.setHashedPassword(PASSWORD);
        assertEquals(Account.hashText(PASSWORD), account.getPassword());
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
        account.setFirstname(FIRSTNAME);
        assertEquals(FIRSTNAME, account.getFirstname());
    }

    @Test
    public void testGetAndSetLastname() {
        assertNull(account.getLastname());
        account.setLastname(LASTNAME);
        assertEquals(LASTNAME, account.getLastname());
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
    public void testGetAndSetWritingExperience() {
        assertEquals(Account.Experience.None, account.getWritingExperience());
        account.setWritingExperience(Account.Experience.Advanced);
        assertEquals(Account.Experience.Advanced, account.getWritingExperience());
    }
    @Test
    public void testGetAndSetReviewingExperience() {
        assertEquals(Account.Experience.None, account.getReviewingExperience());
        account.setReviewingExperience(Account.Experience.Advanced);
        assertEquals(Account.Experience.Advanced, account.getReviewingExperience());
    }
    @Test
    public void testGetAndSetPrimaryIMName() {
        assertEquals("", account.getPrimaryIMName());
        account.setPrimaryIMName(USERNAME);
        assertEquals(USERNAME, account.getPrimaryIMName());
    }
    @Test
    public void testGetAndSetSecondaryIMName() {
        assertEquals("", account.getSecondaryIMName());
        account.setSecondaryIMName(USERNAME);
        assertEquals(USERNAME, account.getSecondaryIMName());
    }
    @Test
    public void testGetAndSetPrimaryIM() {
        assertEquals(Account.IMProtocol.none, account.getPrimaryIM());
        account.setPrimaryIM(Account.IMProtocol.AIM);
        assertEquals(Account.IMProtocol.AIM, account.getPrimaryIM());
    }
    @Test
    public void testGetAndSetSecondaryIM() {
        assertEquals(Account.IMProtocol.none, account.getSecondaryIM());
        account.setSecondaryIM(Account.IMProtocol.AIM);
        assertEquals(Account.IMProtocol.AIM, account.getSecondaryIM());
    }

    @Test
    public void testHashText() {
        String hash = Account.hashText(PASSWORD);
        assertEquals(PASSWORDHASH, hash);
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
        account.setHashedPassword(PASSWORD);
        account.setUsername(USERNAME);
        account.setEnabled(true);
        assertEquals("511fc691aae67b6af1f5a2e033db5cf474813ea2", account.activationChecksum());
        account.setEnabled(false);
        assertEquals("28a50a5f40f0817a391b8928c75c164a2f60adc6", account.activationChecksum());

    }

    @Test
    public void testToString() {
        account.setAccountId(ACCOUNTID);
        Date creationDate = new Date();
        account.setCreationDate(creationDate);
        Date lastModifiedDate = new Date();
        account.setLastModifiedDate(lastModifiedDate);
        account.setEmail(EMAIL);
        boolean enabled = true;
        account.setEnabled(enabled);
        account.setFirstname(FIRSTNAME);
        account.setLastname(LASTNAME);
        account.setHashedPassword(PASSWORD);
        account.setUsername(USERNAME);
        String toString = "Account "
        + "[ accountId=" + ACCOUNTID
        + ", username=" + USERNAME
        + ", email=" + EMAIL
        + ", password="    + Account.hashText(PASSWORD)
        + ", enabled=" + enabled
        + ", firstname=" + FIRSTNAME
        + ", lastname=" + LASTNAME
        + ", lastModifiedDate=" +    lastModifiedDate
        + ", creationDate=" + creationDate
        + ", PASSWDCHARSET=" + Account.PASSWDCHARSET
        + ", ALGORITHM=" + Account.ALGORITHM
        +  "]";

        //FIXME fix this.
        assertEquals(toString, account.toString());
    }
}
