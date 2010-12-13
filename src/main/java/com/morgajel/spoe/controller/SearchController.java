package com.morgajel.spoe.controller;


import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Snippet;
import com.morgajel.spoe.service.AccountService;
import com.morgajel.spoe.service.SnippetService;

/**
 * Controls all Search interactions.
 */
@Controller
public class SearchController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SnippetService snippetService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private SessionFactory sessionFactory;

    public static final Locale LOCALE = Locale.getDefault();

    private static final transient Logger LOGGER = Logger.getLogger(SearchController.class);

    /**
     * Displays the results for a given search.
     * @param searchQuery the text you're searching for
     * @return ModelAndView mav
     */
    @RequestMapping(value = "")
    public ModelAndView quickSearch(@RequestParam("q") final String searchQuery) {
        LOGGER.info("Searching for " + searchQuery);
        ModelAndView mav = new ModelAndView();
        String message;
        try {
            //FIXME need to sanitize user input
            //FIXME need to remove unpublished data
            //FIXME need to display author
            //FIXME need to search text
            Session session = sessionFactory.openSession();
            FullTextSession fullTextSession = Search.getFullTextSession(session);
            Transaction tx = fullTextSession.beginTransaction();

            String[] fields = new String[]{"title", "authors.name", "lastModifiedDate"};
            MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, new StandardAnalyzer());
            org.apache.lucene.search.Query query = parser.parse(searchQuery);
            org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, Snippet.class);

            List results = hibQuery.list();

            tx.commit();
            session.close();

            mav.addObject("results", results);
            message = messageSource.getMessage("search.results", new Object[] {searchQuery}, LOCALE);
        } catch (Exception ex) {
            message = messageSource.getMessage("search.searchfailed", new Object[] {searchQuery}, LOCALE);
            LOGGER.error(message, ex);

        }
        mav.addObject("message", message);
        mav.setViewName("search/results");
        return mav;
    }

    public void setAccountService(AccountService pAccountService) {
        this.accountService = pAccountService;
    }

    public AccountService getAccountService() {
        return this.accountService;
    }

    public void setSnippetService(SnippetService pSnippetService) {
        this.snippetService = pSnippetService;
    }

    public SnippetService getSnippetService() {
        return this.snippetService;
    }
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource pMessageSource) {
        this.messageSource = pMessageSource;
    }
    /**
     * Returns the account for the current context.
     * @return Account
     */
    public Account getContextAccount() {
        return accountService.loadByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
