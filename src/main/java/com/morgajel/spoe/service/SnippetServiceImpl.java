package com.morgajel.spoe.service;

import java.util.List;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Snippet;
import com.morgajel.spoe.dao.SnippetDao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default Snippet Service Implementation used for managing SnippetDao.
 */
@Service("snippetService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SnippetServiceImpl implements SnippetService {

    @Autowired
    private SnippetDao snippetDao;
    private static transient Logger logger = Logger.getLogger(SnippetServiceImpl.class);

    /**
     * Set snippetDao for accessing the datasource.
     * @param snippet the snippetDao you wish to set
     */
    @Override
    public void saveSnippet(Snippet snippet) {
        snippetDao.saveSnippet(snippet);
    }

    @Override
    public List<Snippet> listSnippets() {
        return snippetDao.listSnippets();
    }

    @Override
    public Snippet loadByTitle(String title) {
        logger.info("Loading snippet " + title);
        Snippet snippet = snippetDao.loadByTitle(title);
        return snippet;
    }

    @Override
    public Snippet loadById(Long id) {
        logger.info("Loading snippet " + id);
        Snippet snippet = snippetDao.loadById(id);
        return snippet;
    }

    @Override
    public List<Snippet> loadByAuthor(Account account) {
        logger.info("Loading snippets owned by  " + account.getUsername());
        List<Snippet> snippet = snippetDao.loadByAuthor(account);
        return snippet;
    }
}
