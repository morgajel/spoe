package com.morgajel.spoe.controller;


import java.util.Locale;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.service.AccountService;
import com.morgajel.spoe.service.SnippetService;

/**
 * Welcome page.
 */
@Controller
public class WelcomeController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SnippetService snippetService;
    @Autowired
    private MessageSource messageSource;

    public static final Locale LOCALE = Locale.getDefault();

    private static final transient Logger LOGGER = Logger.getLogger(WelcomeController.class);

    /**
     * Displays the welcome page.
     * @return ModelAndView
     */
    @RequestMapping("/")
    public ModelAndView defaultview() {
        LOGGER.info("something witty");
        ModelAndView mav = new ModelAndView();
        String message = "";
        mav.addObject("message", message);
        mav.setViewName("welcome");
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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountService.loadByUsername(username);
    }

}
