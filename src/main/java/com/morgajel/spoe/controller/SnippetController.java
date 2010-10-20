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
import com.morgajel.spoe.model.Snippet;
import com.morgajel.spoe.service.AccountService;
import com.morgajel.spoe.service.SnippetService;
import com.morgajel.spoe.web.EditSnippetForm;

/**
 * Controls all account interactions from changing passwords, registering and activating accounts, etc.
 */
@Controller
public class SnippetController extends MultiActionController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SnippetService snippetService;

    private MailSender mailSender;

    private static final transient Logger LOGGER = Logger.getLogger(SnippetController.class);

    public void setMailSender(MailSender pMailSender) {
        //NOTE this may not be used anytime soon.
        this.mailSender = pMailSender;
    }

    public MailSender getMailSender() {
        //TODO is this needed?
        return this.mailSender;
    }

    /**
     * Saves the results of the incoming snippet form.
     * @param editSnippetForm the form you are submitting with snippet data.
     * @return ModelAndView mav
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveSnippet(EditSnippetForm editSnippetForm) {
        ModelAndView mav = new ModelAndView();
        try {
            LOGGER.debug("trying to save " + editSnippetForm);
            Account account = getContextAccount();
            if (editSnippetForm.getSnippetId() == null) {
                LOGGER.info("no snippetId found, saving as a new snippet " + editSnippetForm);
                Snippet snippet = new Snippet(account, editSnippetForm);
                snippet.setAuthor(account);
                snippet.setAccountId(account.getAccountId());
                snippetService.saveSnippet(snippet);
                LOGGER.info("saved new snippet " + snippet);
                mav.setViewName("snippet/editSnippet");
                mav.addObject("message", "Snippet saved.");
            } else {
                LOGGER.info("snippetId found, verifying it's a real snippet " + editSnippetForm);
                Snippet snippet = snippetService.loadById(editSnippetForm.getSnippetId());
                if (snippet != null) {
                    if (snippet.getAuthor().getUsername().equals(account.getUsername())) {
                        LOGGER.info("look, '" + snippet.getSnippetId() + "' is a real snippet and owned by " + account.getUsername());
                        snippet.setTitle(editSnippetForm.getTitle());
                        snippet.setContent(editSnippetForm.getContent());

                        LOGGER.info("snippet " + snippet.getSnippetId() + "is about to be saved...");
                        snippetService.saveSnippet(snippet);
                        LOGGER.info("saved existing snippet " + snippet.getSnippetId());

                        Snippet newSnippet = snippetService.loadById(snippet.getSnippetId());
                        editSnippetForm.loadSnippet(newSnippet);
                        LOGGER.info("refreshing editSnippetForm with " + editSnippetForm);
                        mav.setViewName("snippet/editSnippet");
                        mav.addObject("message", "Snippet saved.");
                    } else {
                        mav.setViewName("snippet/snippetFailure");
                        mav.addObject("message", "I'm sorry, you're not the author of this snippet.");
                    }
                } else {
                    mav.setViewName("snippet/snippetFailure");
                    mav.addObject("message", "I'm sorry, that snippet wasn't found.");
                }
            }

        } catch (Exception ex) {
            // TODO catch actual errors and handle them
            // TODO tell the user wtf happened
            LOGGER.error("damnit, something failed." + ex);
            mav.setViewName("snippet/snippetFailure");
            mav.addObject("message", "something failed.");
        }
        mav.addObject("editSnippetForm", editSnippetForm);
        return mav;
    }
    /**
     * Displays the form for editing a given snippet.
     * @param snippetId The ID of the snippet you wish to edit.
     * @param editSnippetForm the snippetForm the user is presented with containing the snippet info
     * @return ModelAndView mav
     */
    @RequestMapping(value = "/edit/{snippetId}", method = RequestMethod.GET)
    public ModelAndView editSnippet(@PathVariable Long snippetId, EditSnippetForm editSnippetForm) {
        LOGGER.debug("trying to edit " + snippetId);
        ModelAndView mav = new ModelAndView();
        try {
            Snippet snippet = snippetService.loadById(snippetId);
            Account account = getContextAccount();
            LOGGER.info(snippet);
            if (snippet != null) {
                LOGGER.info("Snippet found");
                if (account != null && snippet.getAuthor().getUsername().equals(account.getUsername())) {
                    LOGGER.info("Username matches " + snippet.getAuthor().getUsername());
                    mav.setViewName("snippet/editSnippet");
                    editSnippetForm.loadSnippet(snippet); //TODO change to importSnippet
                    mav.addObject("editSnippetForm", editSnippetForm);
                } else {
                    LOGGER.info(account.getUsername() + " isn't the author " + snippet.getAuthor().getUsername());
                    String message = "I'm sorry, Only the author can edit a snippet.";
                    mav.setViewName("snippet/viewSnippet");
                    mav.addObject("snippet", snippet);
                    mav.addObject("message", message);
                }
            } else {
                LOGGER.info("snippet doesn't exist");
                String message = "I'm sorry, " + snippetId + " was not found.";
                mav.setViewName("snippet/snippetFailure");
                mav.addObject("message", message);
            }
        } catch (Exception ex) {
            // TODO catch actual errors and handle them
            // TODO tell the user wtf happened
            LOGGER.error("damnit, something failed.", ex);
            mav.setViewName("snippet/snippetFailure");
            mav.addObject("message", "Something failed while trying to display " + snippetId + ".");
        }
        return mav;
    }

    /**
     * Displays the basic info for a given Snippet.
     * @param snippetId The snippet you wish to display.
     * @return ModelAndView mav
     */
    @RequestMapping(value = "/id/{snippetId}", method = RequestMethod.GET)
    public ModelAndView displaySnippet(@PathVariable Long snippetId) {
        LOGGER.debug("trying to display " + snippetId);
        ModelAndView mav = new ModelAndView();
        try {
            Account account = getContextAccount();
            Snippet snippet = snippetService.loadById(snippetId);
            LOGGER.info(snippet);
            if (snippet != null) {
                if (account != null && snippet.getAuthor().getUsername().equals(account.getUsername())) {
                    //FIXME html should not be here, sloppy sloppy.
                    mav.addObject("editlink", "<div style='float:right;'><a href='/snippet/edit/" + snippet.getSnippetId() + "'>[edit]</a></div>");
                }
                mav.addObject("message", "<!-- nothing important-->");
                mav.setViewName("snippet/viewSnippet");
                mav.addObject("snippet", snippet);
            } else {
                String message = "I'm sorry, " + snippetId + " was not found.";
                LOGGER.error(message);
                mav.setViewName("snippet/viewSnippet");
                mav.addObject("message", message);
            }
        } catch (Exception ex) {
            // TODO catch actual errors and handle them
            // TODO tell the user wtf happened
            LOGGER.error("damnit, something failed.", ex);
            mav.setViewName("snippet/snippetFailure");
            mav.addObject("message", "Something failed while trying to display " + snippetId + ".");
        }
        return mav;
    }

    /**
     * This will display the snippet creation form.
     * @return ModelAndView mav
     */
    @RequestMapping("/create")
    public ModelAndView createSnippetForm() {
        LOGGER.info("showing the createSnippetForm");
        ModelAndView  mav = new ModelAndView();
        mav.setViewName("snippet/editSnippet");
        mav.addObject("editSnippetForm", new EditSnippetForm());
        return mav;
    }

    /**
     * This is the default view for snippet, a catch-all for most any one-offs.
     * Will show the generic snippet page.
     * @return ModelAndView mav
     */
    @RequestMapping
    public ModelAndView defaultView() {
        LOGGER.info("showing the default view");
        ModelAndView  mav = new ModelAndView();
        mav.setViewName("snippet/view");
        mav.addObject("message", "show the default view for snippets");

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

    /**
     * Returns the account for the current context.
     * @return Account
     */
    public Account getContextAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountService.loadByUsername(username);
    }

}
