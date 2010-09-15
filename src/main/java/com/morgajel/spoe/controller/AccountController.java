package com.morgajel.spoe.controller;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.service.AccountService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;

import org.springframework.web.bind.annotation.PathVariable;
import org.apache.commons.lang.StringUtils;

//TODO add headers
//TODO get deoxy working
//TODO add more logging
//TODO add more tests
@Controller
public class AccountController {

    private VelocityEngine velocityEngine;
    private MailSender mailSender;
    private SimpleMailMessage templateMessage;
    @Autowired
    private AccountService accountService;
	private transient static Logger logger = Logger.getLogger("com.morgajel.spoe.controller.AccountController");
	    
    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
    public VelocityEngine getVelocityEngine() {
        return this.velocityEngine;
    }

	//TODO why does this need a parameter?
	@RequestMapping("/register")
	public ModelAndView getRegistrationForm(Account account) {
		logger.info("getregistrationForm loaded");
		return new ModelAndView("account/registrationForm");
	}

    
// TODO unit test
    @RequestMapping(value = "/register.submit", method = RequestMethod.POST)
	public ModelAndView addAccount(@ModelAttribute("account") Account account, BindingResult result) {
    	ModelAndView mav= new ModelAndView();
    	try{
    		//Generate a password, then hash it so it's nice and long
    		String passphrase=Account.hashText(Account.generatePassword(10));
    		logger.trace("generated a nice passphrase to send the user:"+passphrase);
    		//Set the password to a combo of username+passphrase
        	account.setPassword(Account.hashText(passphrase+account.getUsername()));
        	logger.trace("set the password field to "+account.getPassword());
        	//send them a url that contains the username and passphrase
    		sendRegEmail(account,passphrase);
    		accountService.addAccount(account);
    		logger.info("created account "+ account.getUsername());
    		mav.setViewName("account/registrationSuccess");
    		
    	} catch(Exception ex) {
    		// TODO: catch actual errors and handle them
    		// TODO: tell the user wtf happened
    		logger.error("Message failed to send:");
    		logger.error(ex);
    		mav.setViewName("account/registrationForm");
    		mav.addObject("message", "There was an issue creating your account."
    				+ "Please contact the administrator for assistance."
    				+ "<!--" + ex.toString() + "-->");
    	}
		return mav;
	}

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
    public MailSender getMailSender() {
        return this.mailSender;
    }
    
    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
    public SimpleMailMessage getTemplateMessage() {
    	return this.templateMessage;
    }    
    
	// TODO unit test
	private void sendRegEmail(Account account, String passphrase) throws Exception {
		logger.info("trying to send email...");
		String url="http://spoe.morgajel.com/account/activate/" + account.getUsername() + "/" + passphrase;
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(account.getEmail());
        
	    Map model = new HashMap();
	    model.put("firstname", account.getFirstname());
	    model.put("url", url);
	    String result = null;
	    try {
	        // notificationTemplate.vm must be in your classpath
	        result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
	                        "/WEB-INF/templates/registrationEmail.vm", model);
	    } catch (VelocityException e) {
	        e.printStackTrace();
	    }
	    msg.setText(result);
    
        this.mailSender.send(msg);
		logger.info("message sent to "+account.getEmail());
		logger.info(msg.getText());
	}
	// TODO unit test
	@RequestMapping(value = "/activate/{username}/{passphrase}", method = RequestMethod.GET)
    public ModelAndView activateAccount(@PathVariable String username,@PathVariable String passphrase){
    	logger.trace("trying to activate "+username+" with hash "+passphrase );
    	ModelAndView mav= new ModelAndView();
    	try{
    		Account account = accountService.loadByUsername(username);
    		logger.trace("compare"+Account.hashText(passphrase+username) +" with password field "+account.getPassword() );	
    		if (Account.hashText(passphrase+username).equals(account.getPassword())){//XXX
    			logger.info("Holy shit it worked:");	
    			account.setEnabled(true);
    			accountService.addAccount(account);
    			mav.setViewName("account/activationSuccess");
    		}else{
    			logger.info("aw, fail");	
    			mav.setViewName("account/activationFailure");
    		}
    	} catch(Exception ex) {
    		// TODO: catch actual errors and handle them
    		// TODO: tell the user wtf happened
    		logger.error("damnit, something failed.");
			mav.setViewName("account/activationFailure");
    	}
		return mav;
	}
}
