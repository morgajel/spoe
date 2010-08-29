package com.morgajel.spoe.model;

import com.morgajel.spoe.model.Author;
import com.morgajel.spoe.model.Snippet;

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