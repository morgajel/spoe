package com.morgajel.spoe.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.morgajel.spoe.model.Account;

import org.hibernate.SessionFactory;
import static org.mockito.Mockito.*;
/**
 * Set of tests to ensure AccountDaoImpl behavior.
 * @author jmorgan
 *
 */
public class AccountDaoImplTest {
    private AccountDaoImpl accountDao;
    private SessionFactory mockSessionFactory;
    private Account mockAccount;
    private final String username = "morgo2";
    //private final String passfield = "255edd2793e5286d4441ea6bfba734b59e915864";
    private final String tempHash = "df9dd14cbdb3b00f8a54b66f489241e8aeb903ff";
    private final String checksum = "279d8d8a18b94782ef606fbbadd6c011b1692ad0"; //morgo2+temphash+0
    /**
     * Setup is run before each set of tests.
     * @throws Exception Thrown on setup.
     */
    @Before
    public void setUp() throws Exception {
        mockSessionFactory = mock(SessionFactory.class, RETURNS_DEEP_STUBS);
        mockAccount = mock(Account.class);
        accountDao = new AccountDaoImpl();
        accountDao.setSessionFactory(mockSessionFactory);
    }
    /**
     * Tear down is run after each set of tests.
     * @throws Exception exception thrown on tear down.
     */
    @After
    public void tearDown() throws Exception {
        this.accountDao = null;
        mockSessionFactory = null;
        mockAccount = null;
    }
    /**
     * Test SaveAccountFunctionality.
     */
    @Test
    public void testSaveAccount() {
        accountDao.saveAccount(mockAccount);
        verify(mockAccount).setLastModifiedDate((Date) anyObject());
        verify(mockSessionFactory).getCurrentSession();
    }
    /**
     * Test ListAccounts to make sure a proper list is returned.
     */
    @Test
    public void testListAccounts() {
        List<Account> results = new ArrayList<Account>();
        results.add(new Account());
        results.add(new Account());
        //using deep stubs, boooo
        when(mockSessionFactory.getCurrentSession().createCriteria(Account.class).list()).thenReturn(results);
        assertEquals(results, accountDao.listAccounts());
    }
    /**
     * Test LoadByUsername to ensure success when an account is found.
     */
    @Test
    public void testLoadByUsernameSuccess() {
        List<Account> acclist = new ArrayList<Account>();
        acclist.add(mockAccount);
        when(mockSessionFactory.getCurrentSession().getNamedQuery("findAccountByUsername")
                .setString("username", username).list()).thenReturn(acclist);
        Account account = accountDao.loadByUsername(username);
        assertEquals(mockAccount, account);
    }
    /**
     * Test LoadByUsername to ensure failure when an account is not found.
     */
    @Test
    public void testLoadByUsernameFail() {
        List<Account> acclist = new ArrayList<Account>();
        when(mockSessionFactory.getCurrentSession().getNamedQuery("findAccountByUsername")
                .setString("username", username).list()).thenReturn(acclist);
        Account account = accountDao.loadByUsername(username);
        assertEquals(null, account);
    }
    /**
     * LoadByUsernameAndPassword to ensure success when an account is found.
     */
    @Test
    public void testLoadByUsernameAndPasswordSuccess() {
        List<Account> acclist = new ArrayList<Account>();
        acclist.add(mockAccount);
        when(mockSessionFactory.getCurrentSession().getNamedQuery("findAccountByUsernameAndPassword")
                .setString("username", username).setString("password", tempHash).list()).thenReturn(acclist);
        Account account = accountDao.loadByUsernameAndPassword(username, tempHash);
        assertEquals(mockAccount, account);
    }
    /**
     * Test LoadByUsernameAndPassword to ensure failure when an account is not found.
     */
    @Test
    public void testLoadByUsernameAndPasswordFail() {
        List<Account> acclist = new ArrayList<Account>();
        when(mockSessionFactory.getCurrentSession().getNamedQuery("findAccountByUsernameAndPassword")
                .setString("username", username).setString("password", tempHash).list()).thenReturn(acclist);
        Account account = accountDao.loadByUsernameAndPassword(username, tempHash);
        assertEquals(null, account);
    }
    /**
     * LoadByUsernameAndChecksum to ensure success when an account is found.
     */
    @Test
    public void testLoadByUsernameAndChecksumSuccess() {
        List<Account> acclist = new ArrayList<Account>();
        acclist.add(mockAccount);
        when(mockSessionFactory.getCurrentSession().getNamedQuery("findAccountByUsernameAndChecksum").setString("username", username).setString("checksum", checksum).list()).thenReturn(acclist);
        Account account = accountDao.loadByUsernameAndChecksum(username, checksum);
        assertEquals(mockAccount, account);
    }
    /**
     * Test LoadByUsernameAndChecksum to ensure failure when an account is not found.
     */
    @Test
    public void testLoadByUsernameAndChecksumFail() {
        List<Account> acclist = new ArrayList<Account>();
        when(mockSessionFactory.getCurrentSession().getNamedQuery("findAccountByUsernameAndChecksum").setString("username", username).setString("checksum", checksum).list()).thenReturn(acclist);
        Account account = accountDao.loadByUsernameAndChecksum(username, checksum);
        assertEquals(null, account);
    }
}
