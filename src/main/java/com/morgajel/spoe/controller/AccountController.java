package com.morgajel.spoe.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Role;

import com.morgajel.spoe.service.AccountService;
import com.morgajel.spoe.service.RoleService;
import com.morgajel.spoe.web.ForgotPasswordForm;
import com.morgajel.spoe.web.PasswordChangeForm;
import com.morgajel.spoe.web.PersonalInformationForm;
import com.morgajel.spoe.web.RegistrationForm;
import com.morgajel.spoe.web.SetPasswordForm;

/**
 * Controls all account interactions from changing passwords, registering and activating accounts, etc.
 */
@Controller
public class AccountController extends MultiActionController {

    private VelocityEngine velocityEngine;
    private MailSender mailSender;
    private String baseUrl;
    private SimpleMailMessage templateMessage;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;


    private static final String REG_EMAIL_TMPL = "registrationEmail.vm";
    private static final String RESET_PWRD_TMPL = "resetPasswordEmail.vm";
    private static final transient Logger LOGGER = Logger.getLogger(AccountController.class);
/**
 * Reset account by comparing a given username and checksum against a given account.
 * Account must be enabled for this to work. Maps /reset/{username}/{checksum}
 * @return ModelAndView mav
 * @param username username name of user to activate
 * @param checksum hash of username, random password hash and enabled status
 * @param passform the password form you'll send to change the password.
 */
@RequestMapping(value = "/reset/{username}/{checksum}", method = RequestMethod.GET)
public ModelAndView resetPassword(@PathVariable String username, @PathVariable String checksum, SetPasswordForm passform) {
    LOGGER.trace("trying to reset " + username + " with checksum " + checksum);
    ModelAndView mav = new ModelAndView();
    try {
        LOGGER.trace("attempting to load account of " + username);
        Account account = accountService.loadByUsernameAndChecksum(username, checksum);
        LOGGER.info(account);
        if (account != null) {
            String calculatedChecksum = account.activationChecksum();
            LOGGER.trace("compare given checksum " + checksum + " with calculated checksum " + calculatedChecksum);
            if (account.getEnabled()) {
                LOGGER.info("Holy shit it worked:");
                passform.setChecksum(checksum);
                passform.setUsername(username);
                mav.setViewName("account/activationSuccess");
                mav.addObject("message", "Please enter a new password");
                mav.addObject("passform", passform);
            } else {
                LOGGER.info("account is disabled");
                String message = "I'm sorry, this account has been disabled; please contact the administrator.";
                mav.setViewName("account/activationFailure");
                mav.addObject("message", message);
            }
        } else {
            String message = "I'm sorry, that account doesn't exist or the url was incomplete.";
            mav.setViewName("account/activationFailure");
            mav.addObject("message", message);
        }
    } catch (Exception ex) {
        // TODO catch actual errors and handle them
        // TODO tell the user wtf happened
        LOGGER.error("damnit, something failed.", ex);
        mav.setViewName("account/activationFailure");
        mav.addObject("message", "<!-- something horrible happened -->");
    }
    return mav;
}

    /**
     * Provide a user with a form for submitting a username or email address to request a password reset.
     * @param forgotPasswordForm form containing user info
     * @return ModelAndView mav
     */
    @RequestMapping(value = "/forgotPassword.submit", method = RequestMethod.POST)
    public ModelAndView forgotPasswordForm(@Valid ForgotPasswordForm forgotPasswordForm) {
        ModelAndView mav = new ModelAndView();
        LOGGER.info("someone forgot their password...");

        //send email with auth/pwreset info
        try {
            Account account = accountService.loadByUsernameOrEmail(forgotPasswordForm.getUsername(), forgotPasswordForm.getEmail());
            LOGGER.info("checking to see if " + account + " exists");
            if (account != null) {
                LOGGER.info("account found, sending email...");
                String url = baseUrl + "/reset/" + account.getUsername() + "/" + account.activationChecksum();
                sendResetPasswordEmail(account, url);
                LOGGER.info("email sent.");
                mav.addObject("message", "Password Request sent; check your email");
            } else {
                LOGGER.info("That user wasn't found, neither username " + forgotPasswordForm.getUsername() + " or email " + forgotPasswordForm.getEmail());
                mav.addObject("message", "I'm sorry, I couldn't find your username or email address.");
                mav.addObject("forgotPasswordForm", forgotPasswordForm);
            }
        } catch (Exception ex) {
            //Do something Witty.
            LOGGER.info("d'oh, exception!", ex);
            mav.addObject("message", "Oooh, something bad happened...");
        }
        LOGGER.info("sent password.");

        mav.setViewName("account/forgotPasswordForm");
        return mav;
    }
    /**
     * Provide a user with a form for submitting a username or email address to request a password reset.
     * @return ModelAndView mav
     */
    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public ModelAndView forgotPasswordForm() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("account/forgotPasswordForm");
        mav.addObject("forgotPasswordForm", new ForgotPasswordForm());
        LOGGER.info("showing forgot password form.");
        return mav;
    }
    /**
     * Activate account by comparing a given username and checksum against a given account.
     * Account must be disabled for this to work. Maps /activate/{username}/{checksum}
     * @return ModelAndView mav
     * @param username username name of user to activate
     * @param checksum hash of username, random password hash and enabled status
     * @param passform the password form you'll send to change the password.
     */
    @RequestMapping(value = "/activate/{username}/{checksum}", method = RequestMethod.GET)
    public ModelAndView activateAccount(@PathVariable String username, @PathVariable String checksum, SetPasswordForm passform) {
        //NOTE I understand why I need to pass passform on the way out, but what/how/why am I passing it in? where is it coming from?
        LOGGER.trace("trying to activate " + username + " with checksum " + checksum);
        ModelAndView mav = new ModelAndView();
        try {
            LOGGER.trace("attempting to load account of " + username);

            Account account = accountService.loadByUsernameAndChecksum(username, checksum);
            LOGGER.info(account);
            if (account != null) {
                String calculatedChecksum = account.activationChecksum();
                LOGGER.trace("compare given checksum " + checksum + " with calculated checksum " + calculatedChecksum);
                if (!account.getEnabled()) {
                    LOGGER.info("Holy shit it worked:");
                    passform.setChecksum(checksum);
                    passform.setUsername(username);
                    mav.setViewName("account/activationSuccess");
                    mav.addObject("message", "a simple message");
                    mav.addObject("passform", passform);
                } else {
                    LOGGER.info("account already enabled");
                    String message = "I'm sorry, this account has already been enabled.";
                    mav.setViewName("account/activationFailure");
                    mav.addObject("message", message);

                }
            } else {
                String message = "I'm sorry, that account doesn't exist, is already enabled, or the url was incomplete.";
                mav.setViewName("account/activationFailure");
                mav.addObject("message", message);
            }
        } catch (Exception ex) {
            // TODO catch actual errors and handle them
            // TODO tell the user wtf happened
            LOGGER.error("damnit, something failed.", ex);
            mav.addObject("message", "<!-- something bad -->");
            mav.setViewName("account/activationFailure");
        }
        return mav;
    }
    /**
     * Displays the given user's public information.
     * @param username username you wish to display.
     * @return ModelAndView mav
     */
    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public ModelAndView displayUser(@PathVariable String username) {
        LOGGER.debug("trying to display " + username);
        ModelAndView mav = new ModelAndView();
        try {
            Account account = accountService.loadByUsername(username);
            LOGGER.info(account);
            if (account != null && !username.equals("anonymousUser")) {
                mav.addObject("message", username);
                mav.setViewName("account/viewUser");
                mav.addObject("account", account);
            } else {
                LOGGER.info("account doesn't exist");
                String message = "I'm sorry, " + username + " was not found.";
                mav.setViewName("account/viewUser");
                mav.addObject("message", message);
            }
        } catch (Exception ex) {
            // TODO catch actual errors and handle them
            // TODO tell the user wtf happened
            LOGGER.error("Something failed while trying to display " + username, ex);
            mav.addObject("message", "Something failed while trying to display " + username);
            mav.setViewName("account/activationFailure");
        }
        return mav;
    }

    /**
     * Create an account with the given information, then send the user an activation email.
     * @return ModelAndView
     * @param registrationForm contains initial user information
     * @param result I don't know that bind results is needed.
     */
    @RequestMapping(value = "/register.submit", method = RequestMethod.POST)
    public ModelAndView createAccount(@ModelAttribute("registrationForm") @Valid RegistrationForm registrationForm, BindingResult result) {
        // TODO unit test
        ModelAndView mav = new ModelAndView();
        try {
            if (registrationForm.getEmail().equals(registrationForm.getConfirmEmail())) {
                Account account = new Account();
                account.importRegistration(registrationForm);
                account.setHashedPassword(Account.generatePassword(Account.MAXLENGTH));
                LOGGER.trace("password field set to '" + account.getPassword() + "', sending email...");
                String url = baseUrl + "/activate/" + account.getUsername() + "/" + account.activationChecksum();
                sendRegEmail(account, url);
                LOGGER.info("Email sent, adding account " + account.getUsername());
                accountService.addAccount(account);

                //FIXME this role addition should be done in the account service I think.
                Role reviewerRole = roleService.loadByName("ROLE_REVIEWER");
                LOGGER.info("ready to add " + reviewerRole.getName() + " to account " + account);
                account.addRole(reviewerRole);

                LOGGER.info("created account " + account.getUsername());
                accountService.saveAccount(account);

                mav.setViewName("account/registrationSuccess");
                mav.addObject("url", url);
                mav.addObject("account", account);
            } else {
                LOGGER.error("Email addresses did not match.");
                mav.setViewName("account/registrationForm");
                mav.addObject("message", "Sorry, your Email addresses didn't match.");


            }

        } catch (Exception ex) {
            // TODO catch actual errors and handle them
            // TODO tell the user wtf happened
            LOGGER.error("Message failed to send:", ex);
            mav.addObject("message", "There was an issue creating your account."
                    + "Please contact the administrator for assistance.");
            mav.setViewName("account/registrationForm");
        }
        return mav;
    }

    /**
     * Displays the registration form for users to log in.
     * @param registrationForm the form needed to register
     * @return ModelAndView
     */
    @RequestMapping("/register")
    public ModelAndView getRegistrationForm(RegistrationForm registrationForm) {
        LOGGER.info("getregistrationForm loaded");
        ModelAndView  mav = new ModelAndView();
        mav.addObject("registrationForm", registrationForm);
        mav.setViewName("account/registrationForm");
        return mav;
    }
    /**
     * Displays the form for Editing your account.
     * @param personalInformationForm personal Information Form
     * @return ModelAndView mav
     */
    @RequestMapping(value = "/edit")
    public ModelAndView editAccountForm() {
        ModelAndView  mav = new ModelAndView();
        PersonalInformationForm personalInformationForm = new PersonalInformationForm(); 
        PasswordChangeForm passwordChangeForm = new PasswordChangeForm();
        Account account = getContextAccount();
        personalInformationForm.loadAccount(account);
        mav.addObject("personalInformationForm", personalInformationForm);
        mav.addObject("passwordChangeForm", passwordChangeForm);
        mav.setViewName("account/editAccountForm");
        return mav;
    }
    /**
     * Saves changes when editing your account.
     * @param personalInformationForm Personal Information Form
     * @return ModelAndView mav
     */
    @RequestMapping(value = "/personalInformation.submit", method = RequestMethod.POST)
    public ModelAndView savePersonalInformationForm(@Valid PersonalInformationForm personalInformationForm) {
        ModelAndView  mav = new ModelAndView();
        Account account = getContextAccount();
        LOGGER.info("loaded account " + null);
        PasswordChangeForm passwordChangeForm = new PasswordChangeForm();
        if (account != null) {
            account.setLastname(personalInformationForm.getLastname());
            LOGGER.info("set last name to  " + account.getLastname());
            account.setFirstname(personalInformationForm.getFirstname());
            LOGGER.info("set first name to  " + account.getFirstname());
            accountService.saveAccount(account);
            LOGGER.info("saved account " + account);
            mav.addObject("message", "Personal Information Updated.");
        } else {
            LOGGER.error(SecurityContextHolder.getContext().getAuthentication().getName()
                    + ": is the name found in the context, but no account was found.");
            mav.addObject("message", "Odd, your account wasn't found.. are you logged in?");
        }
        mav.addObject("personalInformationForm", personalInformationForm);
        mav.addObject("passwordChangeForm", passwordChangeForm);
        mav.setViewName("account/editAccountForm");
        return mav;
    }

    /**
     * Saves changes when editing your account.
     * @param passwordChangeForm Password Change Form
     * @return ModelAndView mav
     */
    @RequestMapping(value = "/passwordChange.submit", method = RequestMethod.POST)
    public ModelAndView savePasswordChangeForm(PasswordChangeForm passwordChangeForm) {
        ModelAndView  mav = new ModelAndView();
        Account account = getContextAccount();
        PersonalInformationForm personalInformationForm = new PersonalInformationForm();
        if (account != null) {
            personalInformationForm.loadAccount(account);
            if (account.verifyPassword(passwordChangeForm.getCurrentPassword())) {
                if (passwordChangeForm.compareNewPasswords()) {
                    account.setHashedPassword(passwordChangeForm.getNewPassword());
                    accountService.saveAccount(account);
                    mav.addObject("message", "Password updated.");
                } else {
                    mav.addObject("message", "Sorry, your confirmation password didn't match.");
                }
            } else {
                mav.addObject("message", "That isn't you're current password.");
            }
        } else {
            mav.addObject("message", "Odd, your account wasn't found, so I couldn't update..");
        }
        mav.addObject("passwordChangeForm", new PasswordChangeForm());
        mav.addObject("personalInformationForm", personalInformationForm);
        mav.setViewName("account/editAccountForm");
        return mav;
    }

    /**
     * Takes the checksum and username and sets it to the password that the user provides.
     * Account is enabled if passwords match and username/checksum is found.
     * Will bounce you back to activationSuccess if you give it mismatched passwords.
     * @param passform password form
     * @return ModelAndView mav
     */
    @RequestMapping(value = "/activate.setpassword", method = RequestMethod.POST)
    public ModelAndView setPassword(SetPasswordForm passform) {
        ModelAndView  mav = new ModelAndView();
        if (passform.getPassword().equals(passform.getConfirmPassword())) {
            Account account = accountService.loadByUsernameAndChecksum(passform.getUsername(), passform.getChecksum());
            account.setEnabled(true);
            account.setHashedPassword(passform.getPassword());
            accountService.addAccount(account);
            LOGGER.info("set password, then display view: " + passform);

            mav.addObject("message", "Your account has been created!");
            mav.setViewName("redirect:/");
        } else {
            //FIXME unrelated to this code; cache static js files
            LOGGER.info("Your passwords did not match, try again.");
            passform.setPassword("");
            passform.setConfirmPassword("");
            mav.setViewName("account/activationSuccess");
            //FIXME this should be an error
            mav.addObject("message", "Your passwords did not match, try again.");
            mav.addObject("passform", passform);
        }
        return mav;
    }
    /**
     * This is the default view for account, a catch-all for most any one-offs.
     * Will show user's account information in the future.
     * @return ModelAndView mav
     */
    @RequestMapping
    public ModelAndView defaultView() {
        LOGGER.info("showing the default view");
        ModelAndView  mav = new ModelAndView();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountService.loadByUsername(username);
        mav.setViewName("account/view");
        mav.addObject("message", "");
        mav.addObject("account", account);
        return mav;
    }

    public MailSender getMailSender() {
        //TODO is this needed?
        return this.mailSender;
    }

    public SimpleMailMessage getTemplateMessage() {
        //TODO might be able to remove it and replace with reflection.
        return this.templateMessage;
    }

    public VelocityEngine getVelocityEngine() {
        //TODO might be able to remove it and replace with reflection.
        return this.velocityEngine;
    }
    /**
     * Sends Reset Password email to user which includes an activation link.
     * @param account user account to send an email
     * @param url URL to use for activation
     */
    public void sendResetPasswordEmail(Account account, String url) {
        LOGGER.info("trying to send email...");

        Map<String, String> model = new HashMap<String, String>();
        model.put("firstname", account.getFirstname());
        model.put("url", url);

        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        //FIXME BOO, use property file!
        msg.setFrom("SPoE Password Reset <resetPassword@morgajel.com>");
        msg.setSubject("Need to Reset your Password?");
        msg.setTo(account.getEmail());
        LOGGER.info("sending message to " + account.getEmail());

        msg.setText(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, RESET_PWRD_TMPL, model));
        LOGGER.info(msg.getText());

        this.mailSender.send(msg);
    }
    /**
     * Sends Registration email to user which includes an activation link.
     * @param account user account to send an email
     * @param url URL to use for activation
     */
    public void sendRegEmail(Account account, String url) {
        LOGGER.info("trying to send email...");

        Map<String, String> model = new HashMap<String, String>();
        model.put("firstname", account.getFirstname());
        model.put("url", url);

        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        //FIXME BOO, use property file!
        msg.setFrom("SPoE Registration <registration@morgajel.com>");
        msg.setSubject("Activate your account");
        msg.setTo(account.getEmail());
        LOGGER.info("sending message to " + account.getEmail());

        msg.setText(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, REG_EMAIL_TMPL, model));
        LOGGER.info(msg.getText());

        this.mailSender.send(msg);
    }

    public void setAccountService(AccountService pAccountService) {
        this.accountService = pAccountService;
    }
    public AccountService getAccountService() {
        return this.accountService;
    }
    public void setMailSender(MailSender pMailSender) {
        this.mailSender = pMailSender;
    }
    public void setRoleService(RoleService pRoleService) {
        this.roleService = pRoleService;
    }
    public RoleService getRoleService() {
        return this.roleService;
    }
    public void setTemplateMessage(SimpleMailMessage pTemplateMessage) {
        this.templateMessage = pTemplateMessage;
    }

    public void setVelocityEngine(VelocityEngine pVelocityEngine) {
        this.velocityEngine = pVelocityEngine;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
    public void setBaseUrl(String pBaseUrl) {
        this.baseUrl = pBaseUrl;
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
