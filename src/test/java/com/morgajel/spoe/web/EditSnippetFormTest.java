package com.morgajel.spoe.web;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.morgajel.spoe.model.Snippet;
import static org.mockito.Mockito.*;

public class EditSnippetFormTest {

    private final Long snippetId = 12345L;
    private String content = "sample content";
    private String title = "sample title";
    private EditSnippetForm editSnippetForm;
    private Snippet mockSnippet;
    @Before
    public void setUp() throws Exception {
        editSnippetForm = new EditSnippetForm();
        mockSnippet = mock(Snippet.class);
    }

    @After
    public void tearDown() throws Exception {
        editSnippetForm = null;
    }

    @Test
    public void testGetAndSetSnippetId() {
        editSnippetForm.setSnippetId(snippetId);
        assertEquals(snippetId, editSnippetForm.getSnippetId());
    }

    @Test
    public void testGetAndSetContent() {
        editSnippetForm.setContent(content);
        assertEquals(content, editSnippetForm.getContent());

    }

    @Test
    public void testGetAndSetTitle() {
        editSnippetForm.setTitle(title);
        assertEquals(title, editSnippetForm.getTitle());
    }

    @Test
    public void testLoadSnippet() {
        when(mockSnippet.getTitle()).thenReturn(title);
        when(mockSnippet.getContent()).thenReturn(content);
        when(mockSnippet.getSnippetId()).thenReturn(snippetId);
        editSnippetForm.importSnippet(mockSnippet);
        assertEquals(title, editSnippetForm.getTitle());
        assertEquals(content, editSnippetForm.getContent());
        assertEquals(snippetId, editSnippetForm.getSnippetId());


    }

    @Test
    public void testToString() {
        String tostring = "EditSnippetForm [snippetID=" + snippetId + ", content="
        + content + ", title=" + title + "]";
        editSnippetForm.setSnippetId(snippetId);
        editSnippetForm.setContent(content);
        editSnippetForm.setTitle(title);
        assertEquals(tostring, editSnippetForm.toString());
    }
}
