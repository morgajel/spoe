package com.morgajel.spoe.controller;


import com.morgajel.spoe.domain.Account;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AccountController {
    private Logger  logger = Logger.getLogger("com.morgajel.spoe.controller.AccountController");

    
    private Map<Long, Account> accounts = new ConcurrentHashMap<Long, Account>();
    // TODO does this need to be an instance variable?
    private ModelAndView mav=new ModelAndView();
    
    @RequestMapping("/register")
    public ModelAndView getRegistrationForm(Account account) {
        this.mav = new ModelAndView("account/registrationForm");
        this.mav.addObject("account", account);
        logger.error(" getregistrationForm loaded");
        return this.mav;

    }
    @RequestMapping(value="/register.submit",method=RequestMethod.POST)
    public String create(@Valid Account account, BindingResult result) {
//        if (result.hasErrors()) {
            return "account/registrationSuccess";
//        }
//        this.accounts.put(account.assignId(), account);
//        return "redirect:/account/" + account.getAccountId();
    }
//
//    @RequestMapping(value="{id}", method=RequestMethod.GET)
//    public String getView(@PathVariable Long id, Model model) {
//        Account account = this.accounts.get(id);
//        if (account == null) {
//            throw new ResourceNotFoundException(id);
//        }
//        model.addAttribute(account);
//        return "account/view";
//    }

	public Map<Long, Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Map<Long, Account> accounts) {
		this.accounts = accounts;
	}

	public ModelAndView getMav() {
		return mav;
	}

	public void setMav(ModelAndView mav) {
		this.mav = mav;
	}
    
}
