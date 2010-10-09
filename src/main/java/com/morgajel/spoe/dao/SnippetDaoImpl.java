package com.morgajel.spoe.dao;
import org.apache.log4j.Logger;

import java.util.List;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Role;
import com.morgajel.spoe.model.Snippet;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository("snippetDao")
public class SnippetDaoImpl implements SnippetDao {

    @Autowired
    private SessionFactory sessionFactory;
    private static transient Logger logger = Logger.getLogger(SnippetDaoImpl.class);

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
    @Override
    public void saveSnippet(Snippet snippet) {
        sessionFactory.getCurrentSession().saveOrUpdate(snippet);
    }
    @Override
    public List<Snippet> listSnippets() {
        return (List<Snippet>) sessionFactory.getCurrentSession().createCriteria(Snippet.class).list();
    }
    @Override
    public Snippet loadByTitle(String title) {
        List sniplist = sessionFactory.getCurrentSession().getNamedQuery("findSnippetByTitle").setString("title", title).list();
        if (sniplist.size() > 0) {
            logger.info("Loaded snippet " + sniplist.get(0));
            return (Snippet) sniplist.get(0);
        } else {
            return null;
        }
    }
    @Override
    public Snippet loadById(Long id) {
        List sniplist = sessionFactory.getCurrentSession().getNamedQuery("findSnippetById").setLong("snippet_id", id).list();
        if (sniplist.size() > 0) {
            logger.info("Loaded snippet " + sniplist.get(0));
            return (Snippet) sniplist.get(0);
        } else {
            return null;
        }
    }
    @Override
    public List<Snippet> loadByAuthor(Account account) {
        return (List<Snippet>) sessionFactory.getCurrentSession().getNamedQuery("findSnippetByAuthor").setLong("account_id", account.getAccountId()).list();
    }

}