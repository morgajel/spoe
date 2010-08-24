package com.morgajel.spoe.domain;

import com.morgajel.spoe.domain.Reviewer;

import junit.framework.TestCase;

public class ReviewerTest extends TestCase {
    private Reviewer reviewer;
    protected void setUp() throws Exception {
        reviewer = new Reviewer();
    }
    

    public void testSetAndGetName() {
        String testName = "Jackie Morgan";
        assertNull(reviewer.getName());
        reviewer.setName(testName);
        assertEquals(testName, reviewer.getName());
    }
  
}

