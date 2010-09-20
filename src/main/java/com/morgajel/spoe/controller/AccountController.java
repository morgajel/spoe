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

//TODO add headers
//TODO get deoxy working
//TODO add more logging
//TODO add more tests
@Controller
public class AccountController {

	private VelocityEngine velocityEngine;
	private MailSender mailSender;
	private SimpleMailMessage templateMessage;
	private String registrationTemplate;
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	private transient static Logger logger = Logger.getLogger("com.morgajel.spoe.controller.AccountController");

	//=============================Activate account==================================
	@RequestMapping(value = "/activate/{username}/{checksum}", method = RequestMethod.GET)
	public ModelAndView activateAccount(@PathVariable String username,@PathVariable String checksum,SetPasswordForm passform){
		logger.trace("trying to activate "+username+" with checksum "+checksum );
		ModelAndView mav= new ModelAndView();
		try{
			logger.trace("attempting to load account of "+username );

			Account account = accountService.loadByUsernameAndChecksum(username,checksum);
			logger.info(account);
			if (account != null){
				String calculatedChecksum=createActivationChecksum(account);
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
	// TODO unit test
	@RequestMapping(value = "/register.submit", method = RequestMethod.POST)
	public ModelAndView addAccount(@ModelAttribute("account") Account account, BindingResult result) {
		ModelAndView mav= new ModelAndView();
		try{
			//Generate a password, then hash it so it's nice and long
			String tempHash=Account.hashText(Account.generatePassword(10));
			logger.trace("password set to '" +tempHash+"'");
			account.setHashedPassword(tempHash);
			logger.trace("password field set to '" + account.getPassword()+"'");

			sendRegEmail(account);
			logger.info("Email sent, adding account");
			accountService.addAccount(account);

			Role reviewerRole=roleService.loadByName("ROLE_REVIEWER");
			logger.info("ready to add "+reviewerRole.getName()+" to account "+account);
			account.addRole(reviewerRole);
			logger.info("created account "+ account.getUsername());
			logger.info("account has roles: "+ account.getRoles());
			accountService.saveAccount(account);
			String url="http://127.0.0.62:8080/account/activate/" + account.getUsername() + "/" + createActivationChecksum(account);//XXX
			mav.setViewName("account/registrationSuccess");
			mav.addObject("url",url); 		

		} catch(Exception ex) {
			// TODO: catch actual errors and handle them
			// TODO: tell the user wtf happened
			logger.error("Message failed to send:"+ex);
			mav.setViewName("account/registrationForm");
			mav.addObject("message", "There was an issue creating your account."
					+ "Please contact the administrator for assistance."
					+ "<!--" + ex.toString() + "-->");
		}
		return mav;
	}

	private String createActivationChecksum(Account account){
		String username=account.getUsername();
		String passfield=account.getPassword();
		//converting enabled to int rather than string representation so NamedQuery using mysql int works.
		Integer enabled= account.getEnabled()?1:0;
		logger.info("create checksum: "+username+" + "+passfield+" + "+enabled+" = "+Account.hashText(username+passfield+enabled));
		return  Account.hashText(username+passfield+enabled);
	}
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

	public MailSender getMailSender() {
		return this.mailSender;
	}
	//TODO why does this need a parameter?
	@RequestMapping("/register")
	public ModelAndView getRegistrationForm(Account account) {
		logger.info("getregistrationForm loaded");
		return new ModelAndView("account/registrationForm");
	}    
	public SimpleMailMessage getTemplateMessage() {
		return this.templateMessage;
	}
	public VelocityEngine getVelocityEngine() {
		return this.velocityEngine;
	}
	// TODO unit test
	public void sendRegEmail(Account account){
		logger.info("trying to send email...");
		String checksum= createActivationChecksum(account);
		String url="http://127.0.0.62:8080/account/activate/" + account.getUsername() + "/" + checksum;
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		registrationTemplate="/WEB-INF/templates/registrationEmail.vm";
		msg.setTo(account.getEmail());

		Map<String, String> model = new HashMap<String,String>();
		model.put("firstname", account.getFirstname());
		model.put("url", url);
		String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,registrationTemplate, model);
		msg.setText(result);
		logger.info("sending message to "+account.getEmail());
		logger.info(msg.getText());
		this.mailSender.send(msg);
	}

	public void setAccountService(AccountService accountService) {
		this.accountService=accountService;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	@RequestMapping(value="/activation.setpassword", method = RequestMethod.POST)
	public ModelAndView setPassword(SetPasswordForm passform) {
		ModelAndView  mav=new ModelAndView();

		if (passform.password.equals(passform.confirmPassword)){
			Account account = accountService.loadByUsernameAndChecksum(passform.getUsername(),passform.getChecksum());
			account.setEnabled(true);
			account.setHashedPassword(passform.password);
			accountService.addAccount(account);
			logger.info("set password, then display view."+passform);
			mav.setViewName("redirect:/");
		}else{
			logger.info("Your passwords did not match, try again.");	
			passform.setPassword("");
			passform.setConfirmPassword("");
			mav.setViewName("account/activationSuccess");
			mav.addObject("message","a simple message");
			mav.addObject("passform",passform);			
		}
		return mav;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService=roleService;
	}
	public void setTemplateMessage(SimpleMailMessage templateMessage) {
		this.templateMessage = templateMessage;
	}
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
}
