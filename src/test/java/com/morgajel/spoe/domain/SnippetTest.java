package com.morgajel.spoe.domain;

import com.morgajel.spoe.domain.Author;
import com.morgajel.spoe.domain.Snippet;

import junit.framework.TestCase;

public class SnippetTest extends TestCase {
    private Snippet snippet;
    protected void setUp() throws Exception {
        snippet = new Snippet();
    }
    

    public void testSetAndGetText() {
        String testText = "A Long Story";
        assertNull(snippet.getText());
        snippet.setText(testText);
        assertEquals(testText, snippet.getText());
    }

    public void testSetAndGetAuthor() {
        Author testAuthor = new Author();

        snippet.setAuthor(testAuthor);
        assertEquals(testAuthor, snippet.getAuthor());
    }
  
}