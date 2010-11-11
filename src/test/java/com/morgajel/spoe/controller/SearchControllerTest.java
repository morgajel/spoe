/**
 * 
 */
package com.morgajel.spoe.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.service.AccountService;
import com.morgajel.spoe.service.SnippetService;

/**
 * @author jmorgan
 *
 */
public class SearchControllerTest {

    private AccountService mockAccountService;
    private SnippetService mockSnippetService;
    private SecurityContext mockContext;
    private Account mockAccount;
    private static final String USERNAME = "bobdole";

    private SearchController searchController;
    @Before
    public void setUp() throws Exception {
        mockAccountService = mock(AccountService.class);
        mockSnippetService = mock(SnippetService.class);
        mockAccount = mock(Account.class);
        mockContext = mock(SecurityContext.class, RETURNS_DEEP_STUBS);
        searchController = new SearchController();
        searchController.setAccountService(mockAccountService);
        SecurityContextHolder.setContext(mockContext);
    }

    @After
    public void tearDown() throws Exception {
        searchController = null;
    }

    @Test
    public void testQuickSearch() {
        String searchQuery = "fun search";
        ModelAndView mav = searchController.quickSearch(searchQuery);
        assertEquals("search/results", mav.getViewName());
        assertEquals("Results for '" + searchQuery + "'", mav.getModel().get("message"));
    }

    @Test
    public void testGetAndSetAccountService() {
        searchController.setAccountService(mockAccountService);
        assertEquals(mockAccountService, searchController.getAccountService());
    }
    @Test
    public void testGetAndSetSnippetService() {
        searchController.setSnippetService(mockSnippetService);
        assertEquals(mockSnippetService, searchController.getSnippetService());
    }
    
    @Test
    public void testGetContextAccount() {
        when(mockContext.getAuthentication().getName()).thenReturn(USERNAME);
        when(mockAccountService.loadByUsername(USERNAME)).thenReturn(mockAccount);
        Account account = searchController.getContextAccount();
        assertEquals(mockAccount, account);
    }

}
