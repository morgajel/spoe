package com.morgajel.spoe.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Before;

import com.morgajel.spoe.dao.AccountDao;
import com.morgajel.spoe.model.Account;

import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    private AccountDao mockAccountDao;
    private Account mockAccount;
    private AccountServiceImpl accountService;
    private final String username = "morgo2";
//    private final String passfield="255edd2793e5286d4441ea6bfba734b59e915864";
    private final String tempHash = "df9dd14cbdb3b00f8a54b66f489241e8aeb903ff";
    private final String checksum = "279d8d8a18b94782ef606fbbadd6c011b1692ad0"; //morgo2+temphash+0
    @Before
    public void setUp() throws Exception {
        accountService = new AccountServiceImpl();
        mockAccountDao = mock(AccountDao.class);
        mockAccount = mock(Account.class);
        accountService.setAccountDao(mockAccountDao);
    }
    @Test
    public void testSaveAccount() {
        accountService.saveAccount(mockAccount);
        verify(mockAccountDao).saveAccount(mockAccount);
    }

    @Test
    public void testAddAccount() {
        accountService.addAccount(mockAccount);
        verify(mockAccountDao).saveAccount(mockAccount);
    }

    @Test
    public void testListAccount() {
        List<Account> accounts = mock(ArrayList.class);
        when(mockAccountDao.listAccounts()).thenReturn(accounts);
        assertEquals(accounts, accountService.listAccounts());
    }

    @Test
    public void testFindAccountByUsername() {
        Account results = new Account();
        when(mockAccountDao.loadByUsername(username)).thenReturn(results);
        assertEquals(accountService.loadByUsername(username), results);
        when(mockAccountDao.loadByUsername(username)).thenReturn(null);
        assertNull(accountService.loadByUsername(username));
    }

    @Test
    public void testServiceLogin() {
        when(mockAccountDao.loadByUsernameAndPassword(username, tempHash)).thenReturn(mockAccount);
        assertTrue(accountService.login(username, tempHash));
        when(mockAccountDao.loadByUsernameAndPassword(username, tempHash)).thenReturn(null);
        assertFalse(accountService.login(username, tempHash));
   }
    @Test
    public void testListAccounts() {
        List<Account> mockList = mock(List.class);
        when(mockAccountDao.listAccounts()).thenReturn(mockList);
        List<Account> accounts = accountService.listAccounts();
        assertEquals(mockList, accounts);
    }

    @Test
    public void testSetAccountDao() {
        accountService.setAccountDao(mockAccountDao);
        assertEquals(mockAccountDao, accountService.getAccountDao());
    }
    @Test
    public void testLoadByUsernameAndChecksum() {
        when(mockAccountDao.loadByUsernameAndChecksum(username, checksum)).thenReturn(mockAccount);
        Account account = accountService.loadByUsernameAndChecksum(username, checksum);
        assertEquals(mockAccount, account);
    }
}
