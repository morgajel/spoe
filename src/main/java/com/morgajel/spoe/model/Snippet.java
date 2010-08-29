package com.morgajel.spoe.model;

import java.io.Serializable;


public class Snippet implements Serializable {

	// I have no idea what this is for, but eclipse tells me it's needed.
	private static final long serialVersionUID = 1L;
	
	
	//a Snippet is simply a block of text submitted by an Author for review.
	//Will eventually have Critiques associated with it.
	
	//The actual text in the snippet. This may need to be more advanced later, not sure.
    private String text;
    //The author associated with the snippet
    private Author author;

    //getters and setters are simple enough
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    
    public Author getAuthor() {
        return author;
    }
    public void setAuthor(Author author) {
        this.author = author;
    }
    
    //right now only returns text and author
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Author: " + author.getName());
        buffer.append("Text: " + text + ";");
        return buffer.toString();
    }

}
