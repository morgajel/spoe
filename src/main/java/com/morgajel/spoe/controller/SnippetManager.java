package com.morgajel.spoe.controller;

import java.io.Serializable;
import java.util.List;

import com.morgajel.spoe.model.Critique;
import com.morgajel.spoe.model.Snippet;

//Copied and tweaked from ProductManager in tutorial	
	public interface SnippetManager extends Serializable{
		
	    public void addCritique(Critique critique);
	    
	    public List<Snippet> getSnippets();

}
