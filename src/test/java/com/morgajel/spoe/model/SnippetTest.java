package com.morgajel.spoe.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class SnippetTest {
    private Account mockAccount;
    private Snippet snippet;
    private Date mockDate;
    private Long snippetId = new Long("123123123");
    private String title = "One fake Article";
    private String content = "here's a snippet I want you to read.";
    private String username = "bobdole";

    @Before
    public void setUp() throws Exception {
        mockAccount = mock(Account.class);
        mockDate = mock(Date.class);
        snippet = new Snippet(mockAccount, title);
    }

    @After
    public void tearDown() throws Exception {
        snippet = null;
        mockAccount = null;
    }

    @Test
    public void testGetAndSetAccounts() {
        snippet.setAuthor(mockAccount);
        assertEquals(mockAccount, snippet.getAuthor());
    }

    @Test
    public void testGetAndSetSnippetId() {
        assertNull(snippet.getSnippetId());
        snippet.setSnippetId(snippetId);
        assertEquals(snippetId, snippet.getSnippetId());
    }

    @Test
    public void testGetAndSetTitle() {
        String newtitle = "new title";
        assertEquals(title, snippet.getTitle());
        snippet.setTitle(newtitle);
        assertEquals(newtitle, snippet.getTitle());
    }

    @Test
    public void testGetAndSetContent() {
        assertEquals("", snippet.getContent());
        snippet.setContent(content);
        assertEquals(content, snippet.getContent());
    }
    @Test
    public void testGetAndSetLastModifiedDate() {
        when(mockDate.clone()).thenReturn(mockDate);
        assertNotNull(snippet.getLastModifiedDate());
        snippet.setLastModifiedDate(mockDate);
        assertEquals(mockDate, snippet.getLastModifiedDate());
    }

    @Test
    public void testGetAndSetCreationDate() {
        when(mockDate.clone()).thenReturn(mockDate);
        assertNotNull(snippet.getCreationDate());
        snippet.setCreationDate(mockDate);
        assertEquals(mockDate, snippet.getCreationDate());
    }

    @Test
    public void testToString() {
        snippet.setTitle(title);
        snippet.setSnippetId(snippetId);
        String toString = "Snippet "
                    + "[ snippetId=" + snippetId
                    + ", title=" + title
                    + ", author=" + mockAccount
                    +  "]";
        assertEquals(toString, snippet.toString());
    }
}
