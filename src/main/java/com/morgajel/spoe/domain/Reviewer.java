package com.morgajel.spoe.domain;

import java.io.Serializable;

public class Reviewer implements Serializable {


	// I have no idea what this is for, but eclipse tells me it's needed.
	private static final long serialVersionUID = 1L;
	
	// A reviewer is the basic type of user on the site. They are
	// able to read public snippets created by authors and critique them.
	//a Snippet is simply a block of text submitted by an Author for review.
    private String name;

    public String getName() {
    	// TODO: make sure to change this to firstname/lastname, etc.
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String toString() {
    	// Return all the info on a reviewer. currently only their name.
        StringBuffer buffer = new StringBuffer();
        buffer.append("Name: " + name + ";");
        return buffer.toString();
    }

}
