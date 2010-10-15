package com.morgajel.spoe.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.morgajel.spoe.dao.SnippetDao;
import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Snippet;

import static org.mockito.Mockito.*;

/**
 * ServiceServiceImpl Test.
 * @author jmorgan
 *
 */
public class SnippetServiceImplTest {
    private SnippetDao mockSnippetDao;
    private Snippet mockSnippet;
    private Account mockAccount;
    private SnippetServiceImpl snippetService;
    private final Long snippetId = 12345L;
    private String title = "sample title";

    @Before
    public void setUp() throws Exception {
        snippetService = new SnippetServiceImpl();
        mockSnippetDao = mock(SnippetDao.class);
        mockSnippet = mock(Snippet.class);
        mockAccount = mock(Account.class);
        snippetService.setSnippetDao(mockSnippetDao);
    }

    @After
    public void tearDown() throws Exception {
        snippetService = null;
        mockSnippetDao = null;
        mockSnippet = null;
    }

    @Test
    public void testSaveSnippet() {
        snippetService.saveSnippet(mockSnippet);
        verify(mockSnippetDao, times(1)).saveSnippet(mockSnippet);
    }

    @Test
    public void testListSnippets() {
        List<Snippet> snippets = new ArrayList<Snippet>();
        when(mockSnippetDao.listSnippets()).thenReturn(snippets);
        assertEquals(snippets, snippetService.listSnippets());
    }

    @Test
    public void testLoadByTitle() {
        when(mockSnippetDao.loadByTitle(title)).thenReturn(mockSnippet);
        Snippet snippet = snippetService.loadByTitle(title);
        assertEquals(snippet, mockSnippet);
        when(mockSnippetDao.loadByTitle(title)).thenReturn(null);
        snippet = snippetService.loadByTitle(title);
        assertNull(snippet);
    }

    @Test
    public void testLoadById() {
        when(mockSnippetDao.loadById(snippetId)).thenReturn(mockSnippet);
        Snippet snippet = snippetService.loadById(snippetId);
        assertEquals(snippet, mockSnippet);
        when(mockSnippetDao.loadById(snippetId)).thenReturn(null);
        snippet = snippetService.loadById(snippetId);
        assertNull(snippet);
    }

    @Test
    public void testLoadByAuthor() {
        List<Snippet> snippets = new ArrayList<Snippet>();
        when(mockSnippetDao.loadByAuthor(mockAccount)).thenReturn(snippets);
        List<Snippet> snippetlist = snippetService.loadByAuthor(mockAccount);
        assertEquals(snippets, snippetlist);
    }
}
