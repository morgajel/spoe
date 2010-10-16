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
    private List<Snippet> mockSnippetList;
    @Before
    public void setUp() throws Exception {
        snippetService = new SnippetServiceImpl();
        mockSnippetList = mock(ArrayList.class);
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
        
        when(mockSnippetDao.listSnippets()).thenReturn(mockSnippetList);
        assertEquals(mockSnippetList, snippetService.listSnippets());
    }

    @Test
    public void testLoadByTitle() {
        when(mockSnippetDao.loadByTitle(title)).thenReturn(mockSnippetList);
        List<Snippet> snippets = snippetService.loadByTitle(title);
        assertEquals(mockSnippetList, snippets);
        when(mockSnippetDao.loadByTitle(title)).thenReturn(null);
        snippets = snippetService.loadByTitle(title);
        assertNull(snippets);
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
