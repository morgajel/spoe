package com.morgajel.spoe.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Snippet;

import static org.mockito.Mockito.*;

public class SnippetDaoImplTest {
    private Snippet mockSnippet;
    private Account mockAccount;
    private String title = "simple title";
    private Long snippetId = 123123L;
    private Long accountId = 345345L;
    private SessionFactory mockSessionFactory;
    private SnippetDaoImpl snippetDaoImpl;
    private List<Snippet> mockSnippetList;
    @Before
    public void setUp() throws Exception {
        mockSessionFactory = mock(SessionFactory.class, RETURNS_DEEP_STUBS);
        mockSnippetList = new ArrayList<Snippet>();
        mockSnippet = mock(Snippet.class);
        mockAccount = mock(Account.class);
        snippetDaoImpl = new SnippetDaoImpl();
        snippetDaoImpl.setSessionFactory(mockSessionFactory);
    }

    @After
    public void tearDown() throws Exception {
        snippetDaoImpl = null;
    }

    @Test
    public void testGetAndSetSessionFactory() {
        snippetDaoImpl.setSessionFactory(mockSessionFactory);
        assertEquals(mockSessionFactory, snippetDaoImpl.getSessionFactory());
    }

    @Test
    public void testSaveSnippet() {
        snippetDaoImpl.saveSnippet(mockSnippet);
        verify(mockSessionFactory, times(1)).getCurrentSession();
    }
//FIXME why lists in some places and sets in others
    @Test
    public void testListSnippets() {
        when(mockSessionFactory.getCurrentSession().createCriteria(Snippet.class).list()).thenReturn(mockSnippetList);
        List<Snippet> snippets = snippetDaoImpl.listSnippets();
        assertEquals(mockSnippetList, snippets);
    }

    @Test
    public void testLoadByTitle() {
        when(mockSessionFactory.getCurrentSession().getNamedQuery("findSnippetByTitle").setString("title", title).list()).thenReturn(mockSnippetList);
        List<Snippet> snippets = snippetDaoImpl.loadByTitle(title);
        assertEquals(mockSnippetList, snippets);
    }

    @Test
    public void testLoadById() {
        List<Snippet> snippetList = new ArrayList<Snippet>();
        when(mockSessionFactory.getCurrentSession().getNamedQuery("findSnippetById").setLong("snippet_id", snippetId).list()).thenReturn(snippetList);
        assertNull(snippetDaoImpl.loadById(snippetId));

        snippetList.add(mockSnippet);
        Snippet snippet = snippetDaoImpl.loadById(snippetId);
        assertEquals(mockSnippet, snippet);
    }

    @Test
    public void testLoadByAuthor() {
        when(mockAccount.getAccountId()).thenReturn(accountId);
        when(mockSessionFactory.getCurrentSession().getNamedQuery("findSnippetByAuthor").setLong("account_id", mockAccount.getAccountId()).list()).thenReturn(mockSnippetList);
        List<Snippet> snippets = snippetDaoImpl.loadByAuthor(mockAccount);
        assertEquals(mockSnippetList, snippets);
    }

}
