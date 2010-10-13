package com.morgajel.spoe.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Role;
import com.morgajel.spoe.model.Snippet;
import com.morgajel.spoe.service.AccountService;
import com.morgajel.spoe.service.RoleService;
import com.morgajel.spoe.service.SnippetService;

/**
 * Controls all account interactions from changing passwords, registering and activating accounts, etc.
 */
@Controller
public class SnippetController extends MultiActionController {
	
    @Autowired
    private AccountService accountService;
    @Autowired
    private SnippetService snippetService;
    @Autowired
    private RoleService roleService;
    private MailSender mailSender;

    private static transient Logger logger = Logger.getLogger(SnippetController.class);
    
    /**
     * Sets the mailSender to send registration emails.
     * @param pMailSender set the MailSender
     */
    public void setMailSender(MailSender pMailSender) {
        this.mailSender = pMailSender;
    }
    /**
     * Returns the mailSender. Currently only used by Unit Tests, and might be
     * replaced by reflection if I can.
     * @return MailSender
     */
    public MailSender getMailSender() {
        //TODO is this needed?
        return this.mailSender;
    }
    
    /**
     * Displays the form for editing a given snippet
     * @param snippetId The snippet you wish to edit.
     * @return ModelAndView mav
     */
    @RequestMapping(value = "/edit/{snippetId}", method = RequestMethod.GET)
    public ModelAndView editSnippet(@PathVariable Long snippetId) {
        logger.debug("trying to edit " + snippetId);
        ModelAndView mav = new ModelAndView();
        try {
            Snippet snippet = snippetService.loadById(snippetId);
            Account account=getContextAccount();
            logger.info(snippet);
            if (snippet != null) {
                if (snippet.getAuthor().getUsername().equals(account.getUsername())){
                    mav.setViewName("snippet/editSnippet");
                    mav.addObject("content", snippet.getContent());
                    mav.addObject("title", snippet.getTitle());
                }else{
                    logger.info(account.getUsername() + " isn't the author " + snippet.getAuthor().getUsername());
                    String message = "I'm sorry, Only the author can edit a snippet.";
                    mav.setViewName("snippet/viewSnippet");
                    mav.addObject("snippet", snippet);
                    mav.addObject("message", message);                	
                }            	
            } else {
                logger.info("snippet doesn't exist");
                String message = "I'm sorry, " + snippetId + " was not found.";
                mav.setViewName("snippet/snippetFailure");
                mav.addObject("message", message);
            }
        } catch (Exception ex) {
            // TODO catch actual errors and handle them
            // TODO tell the user wtf happened
            logger.error("damnit, something failed." + ex);
            mav.setViewName("snippet/snippetFailure");
            mav.addObject("message", "Something failed while trying to display " + snippetId);
        }
        return mav;
    }    
    
    /**
     * Displays the basic info for a given Snippet
     * @param snippetId The snippet you wish to display.
     * @return ModelAndView mav
     */
    @RequestMapping(value = "/id/{snippetId}", method = RequestMethod.GET)
    public ModelAndView displaySnippet(@PathVariable Long snippetId) {
        logger.debug("trying to display " + snippetId);
        ModelAndView mav = new ModelAndView();
        Account account=getContextAccount();
        try {
            Snippet snippet = snippetService.loadById(snippetId);
            logger.info(snippet);
            if (snippet != null) {
                if (snippet.getAuthor().getUsername().equals(account.getUsername())){
                	mav.addObject("editlink", "<div style='float:right;'><a href='/snippet/edit/"+snippet.getSnippetId()+"'>[edit]</a></div>");	
                }
                mav.addObject("message", "<!-- nothing important-->");
                mav.setViewName("snippet/viewSnippet");
                mav.addObject("snippet", snippet);
            } else {
                logger.info("account doesn't exist");
                String message = "I'm sorry, " + snippetId + " was not found.";
                mav.setViewName("snippet/viewSnippet");
                mav.addObject("message", message);
            }
        } catch (Exception ex) {
            // TODO catch actual errors and handle them
            // TODO tell the user wtf happened
            logger.error("damnit, something failed." + ex);
            mav.setViewName("snippet/snippetFailure");
            mav.addObject("message", "Something failed while trying to display " + snippetId);
        }
        return mav;
    }

    /**
     * This will display the snippet creation form.
     * @return ModelAndView mav
     */
    @RequestMapping("/create")
    public ModelAndView createSnippetForm() {
        logger.info("showing the createSnippetForm");
        ModelAndView  mav = new ModelAndView();
        mav.setViewName("snippet/editSnippet");
        mav.addObject("content", "It was the best of times, it was the worst of times.");
        return mav;
    }

    /**
     * This is the default view for snippet, a catch-all for most any one-offs.
     * Will show the generic snippet page.
     * @return ModelAndView mav
     */
    @RequestMapping
    public ModelAndView defaultView() {
        logger.info("showing the default view");
        ModelAndView  mav = new ModelAndView();
        mav.setViewName("snippet/view");
        mav.addObject("message", "show the default view for snippets");

        return mav;
    }

    /**
     * Sets the Role Service which is autowired.
     * @param pRoleService set the RoleService
     */
    public void setRoleService(RoleService pRoleService) {
        this.roleService = pRoleService;
    }    
    
    /**
     * Sets the Account Service which is autowired.
     * @param pAccountService set the account service for controlling DAOs
     */
    public void setAccountService(AccountService pAccountService) {
        this.accountService = pAccountService;
    }
    /**
     * Returns the account for the current context.
     * @return Account
     */
    public Account getContextAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountService.loadByUsername(username);
    }
    
}