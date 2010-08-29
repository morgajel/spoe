package com.morgajel.spoe.model;

import com.morgajel.spoe.model.Author;

import junit.framework.TestCase;

public class AuthorTest extends TestCase {
    private Author author;
    protected void setUp() throws Exception {
        author = new Author();
    }
    

    public void testSetAndGetName() {
        String testName = "Ziggy Swift";
        assertNull(author.getName());
        author.setName(testName);
        assertEquals(testName, author.getName());
    }
  
}

