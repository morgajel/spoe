package com.morgajel.spoe.controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.MailSender;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import static org.mockito.Mockito.*;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Snippet;
import com.morgajel.spoe.service.AccountService;
import com.morgajel.spoe.service.SnippetService;
import com.morgajel.spoe.web.EditSnippetForm;



public class SnippetControllerTest {
    private AccountService mockAccountService;
    private SnippetService mockSnippetService;
    private SecurityContext mockContext;
    private EditSnippetForm mockEditSnippetForm;
    private Account mockAccount;
    private Snippet mockSnippet;
    private MailSender mockMailSender;
    private SnippetController snippetController;
    private String username = "bobdole";
    private String content = "Four score and seven years ago.";
    private String title = "Abe's Speech";
    private Long snippetId = 123123L;
    private Long accountId = 456456L;
    @Before
    public void setUp() throws Exception {
        mockAccountService = mock(AccountService.class);
        mockSnippetService = mock(SnippetService.class);
        mockMailSender = mock(MailSender.class);
        mockAccount = mock(Account.class);
        mockSnippet = mock(Snippet.class);
        mockEditSnippetForm = mock(EditSnippetForm.class);
        mockContext = mock(SecurityContext.class, RETURNS_DEEP_STUBS);

        snippetController = new SnippetController();
        snippetController.setAccountService(mockAccountService);
        snippetController.setSnippetService(mockSnippetService);
        snippetController.setMailSender(mockMailSender);
        SecurityContextHolder.setContext(mockContext);
    }

    @After
    public void tearDown() throws Exception {
        mockAccountService = null;
        mockSnippetService = null;
        mockMailSender = null;
        snippetController = null;
    }

    @Test
    public void testGetAndSetMailSender() {
        snippetController.setMailSender(mockMailSender);
        assertEquals(mockMailSender, snippetController.getMailSender());
    }

    @Test
    public void testGetAndSetAccountService() {
        snippetController.setAccountService(mockAccountService);
        assertEquals(mockAccountService, snippetController.getAccountService());
    }

    @Test
    public void testGetContextAccount() {
        when(mockContext.getAuthentication().getName()).thenReturn(username);
        when(mockAccountService.loadByUsername(username)).thenReturn(mockAccount);
        Account account = snippetController.getContextAccount();
        assertEquals(mockAccount, account);
    }

    @Test
    public void testSaveSnippetNewSnippet() {
        when(mockEditSnippetForm.getContent()).thenReturn(content);
        when(mockEditSnippetForm.getTitle()).thenReturn(title);
        when(mockEditSnippetForm.getSnippetId()).thenReturn(null);
        when(mockContext.getAuthentication().getName()).thenReturn(username);
        when(mockAccount.getAccountId()).thenReturn(accountId);
        when(mockAccountService.loadByUsername(username)).thenReturn(mockAccount);

        ModelAndView mav = snippetController.saveSnippet(mockEditSnippetForm);

        verify(mockSnippetService, times(1)).saveSnippet((Snippet) anyObject());
        assertEquals("Snippet saved.", mav.getModel().get("message"));
        assertEquals("snippet/editSnippet", mav.getViewName());
        assertEquals(mockEditSnippetForm, mav.getModel().get("editSnippetForm"));
    }

    @Test
    public void testSaveSnippetExistingSnippet() {
        when(mockEditSnippetForm.getContent()).thenReturn(content);
        when(mockEditSnippetForm.getTitle()).thenReturn(title);
        when(mockEditSnippetForm.getSnippetId()).thenReturn(snippetId);
        when(mockContext.getAuthentication().getName()).thenReturn(username);
        when(mockAccount.getAccountId()).thenReturn(accountId);
        when(mockSnippet.getAuthor()).thenReturn(mockAccount);
        when(mockAccount.getUsername()).thenReturn(username);
        when(mockAccountService.loadByUsername(username)).thenReturn(mockAccount);
        when(mockSnippetService.loadById(snippetId)).thenReturn(mockSnippet);

        ModelAndView mav = snippetController.saveSnippet(mockEditSnippetForm);

        verify(mockSnippetService, times(1)).saveSnippet((Snippet) anyObject());
        assertEquals("Snippet saved.", mav.getModel().get("message"));
        assertEquals("snippet/editSnippet", mav.getViewName());
        assertEquals(mockEditSnippetForm, mav.getModel().get("editSnippetForm"));
    }

    public void testSaveSnippetNotYourSnippet() {
        Account otherUser = mock(Account.class);
        when(mockEditSnippetForm.getContent()).thenReturn(content);
        when(mockEditSnippetForm.getTitle()).thenReturn(title);
        when(mockEditSnippetForm.getSnippetId()).thenReturn(snippetId);
        when(mockContext.getAuthentication().getName()).thenReturn(username);
        when(mockAccount.getAccountId()).thenReturn(accountId);
        when(mockSnippet.getAuthor()).thenReturn(otherUser);
        when(otherUser.getUsername()).thenReturn("otheruser");
        when(mockAccount.getUsername()).thenReturn(username);
        when(mockAccountService.loadByUsername(username)).thenReturn(mockAccount);
        when(mockSnippetService.loadById(snippetId)).thenReturn(mockSnippet);

        ModelAndView mav = snippetController.saveSnippet(mockEditSnippetForm);

        verify(mockSnippetService, times(1)).saveSnippet((Snippet) anyObject());
        assertEquals("I'm sorry, you're not the author of this snippet.", mav.getModel().get("message"));
        assertEquals("snippet/snippetFailure", mav.getViewName());
    }

    public void testSaveSnippetNotFound() {
        when(mockEditSnippetForm.getContent()).thenReturn(content);
        when(mockEditSnippetForm.getTitle()).thenReturn(title);
        when(mockEditSnippetForm.getSnippetId()).thenReturn(snippetId);
        when(mockContext.getAuthentication().getName()).thenReturn(username);
        when(mockAccount.getAccountId()).thenReturn(accountId);
        when(mockSnippet.getAuthor()).thenReturn(mockAccount);
        when(mockAccount.getUsername()).thenReturn(username);
        when(mockAccountService.loadByUsername(username)).thenReturn(mockAccount);
        when(mockSnippetService.loadById(snippetId)).thenReturn(null);

        ModelAndView mav = snippetController.saveSnippet(mockEditSnippetForm);

        verify(mockSnippetService, times(1)).saveSnippet((Snippet) anyObject());
        assertEquals("I'm sorry, that snippet wasn't found.", mav.getModel().get("message"));
        assertEquals("snippet/snippetFailure", mav.getViewName());
    }

    public void testSaveSnippetException() {
        when(mockEditSnippetForm.getContent()).thenReturn(content);
        when(mockEditSnippetForm.getTitle()).thenReturn(title);
        when(mockEditSnippetForm.getSnippetId()).thenReturn(snippetId);
        stub(mockContext.getAuthentication()).toThrow(new RuntimeException());
        when(mockAccount.getAccountId()).thenReturn(accountId);
        when(mockSnippet.getAuthor()).thenReturn(mockAccount);
        when(mockAccount.getUsername()).thenReturn(username);
        when(mockAccountService.loadByUsername(username)).thenReturn(mockAccount);
        when(mockSnippetService.loadById(snippetId)).thenReturn(null);

        ModelAndView mav = snippetController.saveSnippet(mockEditSnippetForm);

        verify(mockSnippetService, times(0)).saveSnippet((Snippet) anyObject());
        assertEquals("something failed", mav.getModel().get("message"));
        assertEquals("snippet/snippetFailure", mav.getViewName());
    }

    @Test
    public void testEditSnippet() {
        when(mockEditSnippetForm.getContent()).thenReturn(content);
        when(mockEditSnippetForm.getTitle()).thenReturn(title);
        when(mockEditSnippetForm.getSnippetId()).thenReturn(snippetId);
        when(mockContext.getAuthentication().getName()).thenReturn(username);
        when(mockAccountService.loadByUsername(username)).thenReturn(mockAccount);
        when(mockSnippetService.loadById(snippetId)).thenReturn(mockSnippet);
        when(mockSnippet.getAuthor()).thenReturn(mockAccount);
        when(mockAccount.getUsername()).thenReturn(username);

        ModelAndView mav = snippetController.editSnippet(snippetId, mockEditSnippetForm);

        verify(mockEditSnippetForm, times(1)).loadSnippet((Snippet) anyObject());
        assertEquals("snippet/editSnippet", mav.getViewName());
    }

//    @Test
//    public void testDisplaySnippet() {
//        fail("Not yet implemented");
//    }
//
//    @Test
//    public void testCreateSnippetForm() {
//        fail("Not yet implemented");
//    }
//
//    @Test
//    public void testDefaultView() {
//        fail("Not yet implemented");
//    }

}
