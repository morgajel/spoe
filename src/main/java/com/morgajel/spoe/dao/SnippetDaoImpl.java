package com.morgajel.spoe.dao;
import org.apache.log4j.Logger;

import java.util.List;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Snippet;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * Implementation of the snippetDao interface, used to save and load snippets from the datasource.
 */
@Repository("snippetDao")
public class SnippetDaoImpl implements SnippetDao {

    @Autowired
    private SessionFactory sessionFactory;
    private static final transient Logger LOGGER = Logger.getLogger(SnippetDaoImpl.class);

    /**
     * Sets the Session Factory used to get the currentSession.
     * @param pSessionFactory sessionFactory to be used
     */
    public void setSessionFactory(SessionFactory pSessionFactory) {
        this.sessionFactory = pSessionFactory;
    }
    /**
     * Get the Session Factory used to get the currentSession.
     * @return SessionFactory
     */
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }
    /**
     * Saves a given snippet to the datasource.
     * @param snippet snippet to save
     */
    @Override
    public void saveSnippet(Snippet snippet) {
        sessionFactory.getCurrentSession().saveOrUpdate(snippet);
        LOGGER.info("saved snippet" + snippet);
    }
    @Override
    public List<Snippet> listSnippets() {
        return (List<Snippet>) sessionFactory.getCurrentSession().createCriteria(Snippet.class).list();
    }
    @Override
    public List<Snippet> loadByTitle(String title) {
        List<Snippet> sniplist = sessionFactory.getCurrentSession().getNamedQuery("findSnippetByTitle").setString("title", title).list();
            return sniplist;
    }
    @Override
    public Snippet loadById(Long id) {
        List<Snippet> sniplist = sessionFactory.getCurrentSession().getNamedQuery("findSnippetById").setLong("snippet_id", id).list();
        if (sniplist.size() > 0) {
            LOGGER.info("Loaded snippet " + sniplist.get(0));
            return (Snippet) sniplist.get(0);
        } else {
            return null;
        }
    }
    @Override
    public List<Snippet> loadByAuthor(Account account) {
        return (List<Snippet>) sessionFactory.getCurrentSession().getNamedQuery("findSnippetByAuthor").setLong("account_id", account.getAccountId()).list();
    }
    @Override
    public List<Snippet> loadPublishedByAuthor(Account account) {
        return (List<Snippet>) sessionFactory.getCurrentSession().getNamedQuery("findPublishedSnippetByAuthor").setLong("account_id", account.getAccountId()).list();
    }
}
