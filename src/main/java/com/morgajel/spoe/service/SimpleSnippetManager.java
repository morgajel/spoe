package com.morgajel.spoe.service;

//import java.util.ArrayList;  <-- sample said I needed this, eclipse says I don't.
import java.util.List;



import com.morgajel.spoe.domain.Critique;
import com.morgajel.spoe.domain.Snippet;

//Copied and tweaked from ProductManager in tutorial
public class SimpleSnippetManager implements SnippetManager {

	private static final long serialVersionUID = 1L;
    private List<Snippet> snippets;
    
	public void addCritique(Critique critique) {
		// TODO Auto-generated method stub, add this later.
        throw new UnsupportedOperationException();
	}


    public List<Snippet> getSnippets() {
        return snippets;
    }

    public void setSnippets(List<Snippet> snippets) {
        this.snippets=snippets;        
    }

}