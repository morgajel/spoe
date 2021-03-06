package com.morgajel.spoe.service;

import java.util.List;

import com.morgajel.spoe.model.Snippet;
import com.morgajel.spoe.model.Account;

public interface SnippetService {
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
     * Return snippets with a given title, or else null.
     * @param title snippet title to load
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
     * List all snippets in the datasource owned by a given account.
     * @param account account to look for
     * @return List<Snippet>
     */
    List<Snippet> loadPublishedByAuthor(Account account);
    /**
     * List x published snippets that were recently modified.
     * @param count number of snippets to return
     * @return List<Snippet>
     */
    List<Snippet> loadRecentlyModifiedPublished(int count);
}
