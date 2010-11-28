package com.morgajel.spoe.dao;

import java.util.List;

import com.morgajel.spoe.model.Snippet;
import com.morgajel.spoe.model.Account;

/**
 * Hibernate object interface used for storing and loading Snippets.
 */
public interface SnippetDao {

    /**
     * Save a provided snippet to the datasource.
     * @param snippet snippet to save
     */
    void saveSnippet(Snippet snippet);

    /**
     * List all snippets in the datasource.
     * @return List<Snippet>
     */
    List<Snippet> listSnippets();

    /**
     * Return  snippets with a given title, or else null.
     * @param title snippet title to use
     * @return List<Snippet>
     */
    List<Snippet> loadByTitle(String title);

    /**
     * Return an snippet with a given ID, or else null.
     * @param id snippet title to load
     * @return Snippet
     */
    Snippet loadById(Long id);

    /**
     * List all snippets in the datasource owned by a given account.
     * @param account account to look for
     * @return List<Snippet>
     */
    List<Snippet> loadByAuthor(Account account);

    /**
     * List all snippets in the datasource owned by a given account that are published.
     * @param account account to look for
     * @return List<Snippet>
     */
    List<Snippet> loadPublishedByAuthor(Account account);
    /**
     * List top X recently modified and published snippets.
     * @param count number to return
     * @return List<Snippet>
     */
    List<Snippet> loadRecentlyModifiedPublished(int count);
}
