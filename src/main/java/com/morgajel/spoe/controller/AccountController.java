package com.morgajel.spoe.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Role;
import com.morgajel.spoe.service.AccountService;
import com.morgajel.spoe.service.RoleService;
import com.morgajel.spoe.web.SetPasswordForm;

/** 
 * Controls all account interactions from changing passwords, registering and activating accounts, etc.
 */
@Controller
public class AccountController {

	private VelocityEngine velocityEngine;
	private MailSender mailSender;
	private SimpleMailMessage templateMessage;
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	
	
	private final String registrationTemplate ="/WEB-INF/templates/registrationEmail.vm";
	private final String activationUrl="http://127.0.0.62:8080/account/activate/";
	private transient static Logger logger = Logger.getLogger("com.morgajel.spoe.controller.AccountController");

	/** 
	 * Activate account by comparing a given username and checksum against a given account. 
	 * Account must be disabled for this to work. Maps /activate/{username}/{checksum}
	 */
	@RequestMapping(value = "/activate/{username}/{checksum}", method = RequestMethod.GET)
	public ModelAndView activateAccount(@PathVariable String username,@PathVariable String checksum,SetPasswordForm passform){
		logger.trace("trying to activate "+username+" with checksum "+checksum );
		ModelAndView mav= new ModelAndView();
		try{
			logger.trace("attempting to load account of "+username );

			Account account = accountService.loadByUsernameAndChecksum(username,checksum);
			logger.info(account);
			if (account != null){
				String calculatedChecksum=account.activationChecksum();
				logger.trace("compare given checksum "+checksum +" with calculated checksum "+calculatedChecksum);
				if (! account.getEnabled()){//XXX
					logger.info("Holy shit it worked:");	
					passform.setChecksum(checksum);
					passform.setUsername(username);
					mav.setViewName("account/activationSuccess");
					mav.addObject("message","a simple message");
					mav.addObject("passform",passform);
				}else{
					logger.info("account already enabled");
					String message="I'm sorry, this account has already been enabled.";
					mav.setViewName("account/activationFailure");
					mav.addObject("message",message);

				}
			}else{
				String message="I'm sorry, that account doesn't exist, is already enabled, or the url was incomplete.";
				mav.setViewName("account/activationFailure");
				mav.addObject("message",message);
			}
		} catch(Exception ex) {
			// TODO: catch actual errors and handle them
			// TODO: tell the user wtf happened
			logger.error("damnit, something failed."+ex);
			mav.setViewName("account/activationFailure");
			mav.addObject("message","<!--"+ex+"-->");
		}
		return mav;
	}
	
	/** 
	 * Create an account with the given information, then send the user an activation email.
	 */
	@RequestMapping(value = "/register.submit", method = RequestMethod.POST)
	public ModelAndView createAccount(@ModelAttribute("account") Account account, BindingResult result) {
		// TODO unit test
		ModelAndView mav= new ModelAndView();
		try{
			account.setHashedPassword(Account.generatePassword(10));
			logger.trace("password field set to '" + account.getPassword()+"', sending email...");

			String url=activationUrl + account.getUsername() + "/" + account.activationChecksum();
			sendRegEmail(account,url);
			logger.info("Email sent, adding account "+account.getUsername());
			accountService.addAccount(account);
			
			//FIXME this role addition should be done in the account service I think.
			Role reviewerRole=roleService.loadByName("ROLE_REVIEWER");
			logger.info("ready to add "+reviewerRole.getName()+" to account "+account);
			account.addRole(reviewerRole);
			
			logger.info("created account "+ account.getUsername());
			accountService.saveAccount(account);
			
			mav.setViewName("account/registrationSuccess");
			mav.addObject("url",url); 		

		} catch(Exception ex) {
			// TODO: catch actual errors and handle them
			// TODO: tell the user wtf happened
			logger.error("Message failed to send:"+ex);
			mav.setViewName("account/registrationForm");
			mav.addObject("message", "There was an issue creating your account."
					+ "Please contact the administrator for assistance.");
		}
		return mav;
	}
	
	/** 
	 * Displays the registration form for users to log in. 
	 */
	@RequestMapping("/register")
	public ModelAndView getRegistrationForm(Account account) {
		logger.info("getregistrationForm loaded");
		return new ModelAndView("account/registrationForm");
	}
	
	/** 
	 * Takes the checksum and username and sets it to the password that the user provides.
	 * Account is enabled if passwords match and username/checksum is found.
	 * Will bounce you back to activationSuccess if you give it mismatched passwords. 
	 */
	@RequestMapping(value="/activation.setpassword", method = RequestMethod.POST)
	public ModelAndView setPassword(SetPasswordForm passform) {
		ModelAndView  mav=new ModelAndView();
		if (passform.getPassword().equals(passform.getConfirmPassword())){
			Account account = accountService.loadByUsernameAndChecksum(passform.getUsername(),passform.getChecksum());
			account.setEnabled(true);
			account.setHashedPassword(passform.getPassword());
			accountService.addAccount(account);
			logger.info("set password, then display view."+passform);
			mav.setViewName("redirect:/");
		}else{
			logger.info("Your passwords did not match, try again.");	
			passform.setPassword("");
			passform.setConfirmPassword("");
			mav.setViewName("account/activationSuccess");
			//FIXME this should be an error
			mav.addObject("message","Your passwords did not match, try again.");
			mav.addObject("passform",passform);			
		}
		return mav;
	}
	
	/** 
	 * This is the default view for account, a catch-all for most any one-offs. 
	 * Will show user's account information in the future.
	 */
	@RequestMapping("*")
	public ModelAndView defaultView() {
		logger.info("showing the default view");
		ModelAndView  mav=new ModelAndView();

		String username= SecurityContextHolder.getContext().getAuthentication().getName();
		Account account=accountService.loadByUsername(username);

		mav.setViewName("account/view");
		mav.addObject("message","show the default view for "+username);
		mav.addObject("account",account);			
		return mav;
	}
	
	/** 
	 * Returns the mailSender. Currently only used by Unit Tests, and might be 
	 * replaced by reflection if I can. 
	 */
	public MailSender getMailSender() {
		//TODO is this needed?
		return this.mailSender;
	}

	/** 
	 * returns templateMessage, may only be used by Unit testing. 
	 */
	public SimpleMailMessage getTemplateMessage() {
		//TODO might be able to remove it and replace with reflection.
		return this.templateMessage;
	}

	/** 
	 * returns velocityEngine, may only be for unit testing. 
	 */
	public VelocityEngine getVelocityEngine() {
		//TODO might be able to remove it and replace with reflection.
		return this.velocityEngine;
	}

	/** 
	 * Sends Registration email to user which includes an activation link. 
	 */
	public void sendRegEmail(Account account,String url){
		logger.info("trying to send email...");

		Map<String, String> model = new HashMap<String,String>();
		model.put("firstname", account.getFirstname());
		model.put("url", url);
		
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);		

		msg.setTo(account.getEmail());
		logger.info("sending message to "+account.getEmail());

		msg.setText(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,registrationTemplate, model));
		logger.info(msg.getText());
		
		this.mailSender.send(msg);
	}
	
	/** 
	 * Sets the Account Service which is autowired. 
	 */
	public void setAccountService(AccountService accountService) {
		this.accountService=accountService;
	}
	
	/** 
	 * Sets the mailSender to send registration emails 
	 */
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	/** 
	 * Sets the Role Service which is autowired. 
	 */
	public void setRoleService(RoleService roleService) {
		this.roleService=roleService;
	}
	
	/** 
	 * Sets the Role Service which is autowired. 
	 */
	public void setTemplateMessage(SimpleMailMessage templateMessage) {
		this.templateMessage = templateMessage;
	}
	
	/** 
	 * Sets the Velocity Engine for sending templated emails. 
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	public String getActivationUrl(){
		return activationUrl;
	}
}
