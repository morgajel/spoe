package com.morgajel.spoe.controller;


import javax.validation.Valid;

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
import com.morgajel.spoe.model.Snippet;
import com.morgajel.spoe.service.AccountService;
import com.morgajel.spoe.service.SnippetService;
import com.morgajel.spoe.web.EditSnippetForm;

/**
 * Controls all snippet interactions, etc.
 */
@Controller
public class ReviewController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SnippetService snippetService;

    private MailSender mailSender;

    private static final transient Logger LOGGER = Logger.getLogger(ReviewController.class);

    public void setMailSender(MailSender pMailSender) {
        //NOTE this may not be used anytime soon.
        this.mailSender = pMailSender;
    }

    public MailSender getMailSender() {
        //TODO is this needed?
        return this.mailSender;
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

    /**
     * Returns the account for the current context.
     * @return Account
     */
    public Account getContextAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountService.loadByUsername(username);
    }

}
