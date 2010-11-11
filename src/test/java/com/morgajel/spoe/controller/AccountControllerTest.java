package com.morgajel.spoe.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.velocity.app.VelocityEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Role;
import com.morgajel.spoe.model.Snippet;
import com.morgajel.spoe.service.AccountService;
import com.morgajel.spoe.service.RoleService;
import com.morgajel.spoe.web.ForgotPasswordForm;
import com.morgajel.spoe.web.PasswordChangeForm;
import com.morgajel.spoe.web.PersonalInformationForm;
import com.morgajel.spoe.web.RegistrationForm;
import com.morgajel.spoe.web.SetPasswordForm;
import static org.mockito.Mockito.*;

/**
 * Tests the Account Controller.
 * @author jmorgan
 *
 */
public class AccountControllerTest {
    private AccountController accountController;
    private Account mockAccount;
    private Snippet mockSnippet;
    private SecurityContext mockContext;
    private Role mockRole;
    private AccountService mockAccountService;
    private PasswordChangeForm mockPasswordChangeForm;
    private RegistrationForm mockRegistrationForm;
    private RoleService mockRoleService;
    private MailSender mockMailSender;
    private SetPasswordForm mockPassForm;
    private SimpleMailMessage mockTemplateMessage;
    private VelocityEngine mockVelocityEngine;
    private PersonalInformationForm mockPersonalInformationForm;
    private static final String USERNAME = "morgo2";
    private static final String FIRSTNAME = "Jesse";
    private static final String LASTNAME = "Morgan";
    private static final String EMAIL = "morgo2@example.com";
    private static final String PASSFIELD = "255edd2793e5286d4441ea6bfba734b59e915864";
    private static final String PASSWORD = "MatchedLuggage12345";
    //private final String tempHash="df9dd14cbdb3b00f8a54b66f489241e8aeb903ff";
    private static final String CHECKSUM = "279d8d8a18b94782ef606fbbadd6c011b1692ad0"; //morgo2+temphash+0
    private static final String BASEURL = "http://example.com/";
    /**
     * Create the intial mockups and classes that are used with each run.
     * @throws Exception generic exception
     */
    @Before
    public void setUp() throws Exception {
        mockAccountService = mock(AccountService.class);
        mockRoleService = mock(RoleService.class);
        mockAccount = mock(Account.class);
        mockContext = mock(SecurityContext.class, RETURNS_DEEP_STUBS);
        mockRole = mock(Role.class);
        mockSnippet = mock(Snippet.class);
        mockMailSender = mock(MailSender.class);
        mockPassForm = mock(SetPasswordForm.class);
        mockRegistrationForm = mock(RegistrationForm.class);
        mockPasswordChangeForm = mock(PasswordChangeForm.class);
        mockTemplateMessage = mock(SimpleMailMessage.class);
        mockVelocityEngine = mock(VelocityEngine.class);
        mockPersonalInformationForm = mock(PersonalInformationForm.class);
        List<Account> accountList = new ArrayList();
        accountList.add(mockAccount);
        List<Snippet> snippetList = new ArrayList();
        snippetList.add(mockSnippet);
        accountController = new AccountController();
        accountController.setAccountService(mockAccountService);
        accountController.setRoleService(mockRoleService);
        accountController.setMailSender(mockMailSender);
        accountController.setTemplateMessage(mockTemplateMessage);
        accountController.setVelocityEngine(mockVelocityEngine);
    }
    /**
     * Tears down all mockups and classes between each test.
     * @throws Exception generic exception
     */
    @After
    public void tearDown() throws Exception {
        mockAccountService = null;
        mockAccount = null;
        mockRole = null;
        mockMailSender = null;
        mockPassForm = null;
        mockPasswordChangeForm = null;
        mockTemplateMessage = null;
        mockVelocityEngine = null;
        mockContext = null;
        accountController = null;
    }
    /**
     * Test ActivateAccount's Best case scenario.
     */
    @Test
    public void testActivateAccountBestcase() {

        // Test Bestcase scenario success
        when(mockAccount.getPassword()).thenReturn(PASSFIELD);
        when(mockAccount.getUsername()).thenReturn(USERNAME);
        when(mockAccount.getEnabled()).thenReturn(false);
        when(mockAccountService.loadByUsername(USERNAME)).thenReturn(mockAccount);
        when(mockAccountService.loadByUsernameAndChecksum(USERNAME, CHECKSUM)).thenReturn(mockAccount);
        ModelAndView results = accountController.activateAccount(USERNAME, CHECKSUM, new SetPasswordForm());
        assertEquals("account/activationSuccess", results.getViewName());
    }
    /**
     * Test ActivateAccount when there is no matching account.
     */
    @Test
    public void testActivateAccountNoMatch() {

        // non-existent account failure
        when(mockAccountService.loadByUsername(USERNAME)).thenReturn(null);
        when(mockAccountService.loadByUsernameAndChecksum(USERNAME, CHECKSUM)).thenReturn(null);
        ModelAndView results = accountController.activateAccount(USERNAME, CHECKSUM, new SetPasswordForm());
        assertEquals("account/activationFailure", results.getViewName());
        //TODO assertEquals("I'm sorry, that account was not found.",results.someting());
    }
    /**
     * Test AciveAccount mismatched checksum.
     */
    @Test
    public void testActivateAccountChecksumMismatch() {
        //Test checksum mismatch
        when(mockAccountService.loadByUsername(USERNAME)).thenReturn(mockAccount);
        when(mockAccount.getPassword()).thenReturn("some wrong passfield");
        when(mockAccount.getEnabled()).thenReturn(false);
        ModelAndView results = accountController.activateAccount(USERNAME, CHECKSUM, new SetPasswordForm());
        assertEquals("account/activationFailure", results.getViewName());
    }
    /**
     * Test AcivateAccount when account is already Activated.
     */
    @Test
    public void testActivateAccountAlreadyActivated() {
        //Test already enabled failure
        when(mockAccountService.loadByUsernameAndChecksum(USERNAME, CHECKSUM)).thenReturn(mockAccount);
        when(mockAccount.getEnabled()).thenReturn(true);
        ModelAndView results = accountController.activateAccount(USERNAME, CHECKSUM, new SetPasswordForm());
        assertEquals("account/activationFailure", results.getViewName());

    }
    /**
     * Test ActivateAccount when something throws an Exception.
     */
    @Test
    public void testActivateAccountThrowException() {
        //Test throw exception caught
        stub(mockAccountService.loadByUsernameAndChecksum(USERNAME, CHECKSUM)).toThrow(new IndexOutOfBoundsException());
        ModelAndView results = accountController.activateAccount(USERNAME, CHECKSUM, new SetPasswordForm());
        assertEquals("account/activationFailure", results.getViewName());
        assertEquals("<!-- something bad -->", results.getModel().get("message"));
    }
    /**
     * Test the Registration Mail Sender to ensure the gears spin.
     */
    @Test
    public void testSendRegEmail() {
        when(mockAccount.getPassword()).thenReturn(PASSFIELD);
        when(mockAccount.getUsername()).thenReturn(USERNAME);
        when(mockAccount.getEnabled()).thenReturn(false);

        accountController.sendRegEmail(mockAccount, "http://example.com/sometesturl");
        verify(mockMailSender).send((SimpleMailMessage) anyObject());

    }

    /**
     * Test the Set and Get Template Messages.
     */
    @Test
    public void testSetAndGetTemplateMessage() {
        SimpleMailMessage simpleMailMessage = mock(SimpleMailMessage.class);
        accountController.setTemplateMessage(simpleMailMessage);
        //using deep stubs, boooo
        assertEquals(accountController.getTemplateMessage(), simpleMailMessage);
    }
    /**
     * Test the Set and Get Mail Sender.
     */
    @Test
    public void testSetAndGetMailSender() {
        MailSender mailSender = mock(MailSender.class);
        accountController.setMailSender(mailSender);
        //using deep stubs, boooo
        assertEquals(accountController.getMailSender(), mailSender);
    }
    /**
     * Test the Set and get for the velocity engine.
     */
    @Test
    public void testSetAndGetVelocityEngine() {
        VelocityEngine velocityEngine = mock(VelocityEngine.class);
        accountController.setVelocityEngine(velocityEngine);
        //using deep stubs, boooo
        assertEquals(accountController.getVelocityEngine(), velocityEngine);
    }
    /**
     * Test the results of the Default View.
     */
    @Test
    public void testDefaultView() {
        SecurityContextHolder.setContext(mockContext);
        when(mockContext.getAuthentication().getName()).thenReturn(USERNAME);
        when(mockAccountService.loadByUsername(USERNAME)).thenReturn(mockAccount);
        ModelAndView mav = accountController.defaultView();
        assertEquals("account/view", mav.getViewName());
        assertEquals("", mav.getModel().get("message"));
    }
    /**
     * Test to make sure the Registration form is displayed.
     */
    @Test
    public void testGetRegistrationForm() {
        ModelAndView mav = accountController.getRegistrationForm(mockRegistrationForm);
        assertEquals(mav.getViewName(), "account/registrationForm");
    }
    /**
     * Text CreateAccount to make sure it works.
     */
    @Test
    public void testCreateAccountSuccess() {

        when(mockRegistrationForm.getUsername()).thenReturn(USERNAME);
        when(mockRegistrationForm.getFirstname()).thenReturn(FIRSTNAME);
        when(mockRegistrationForm.getLastname()).thenReturn(LASTNAME);
        when(mockRegistrationForm.getEmail()).thenReturn(EMAIL);
        when(mockRegistrationForm.getConfirmEmail()).thenReturn(EMAIL);

        when(mockRoleService.loadByName("ROLE_REVIEWER")).thenReturn(mockRole);
        ModelAndView result = accountController.createAccount(mockRegistrationForm, null);
        Account account = (Account) result.getModel().get("account");

        verify(mockAccountService).addAccount((Account) anyObject());
        verify(mockRoleService).loadByName("ROLE_REVIEWER");

        verify(mockAccountService).saveAccount((Account) anyObject());
        assertEquals(accountController.getBaseUrl() + "/activate/" + USERNAME + "/" + account.activationChecksum(),
                    result.getModel().get("url"));
        assertEquals("account/registrationSuccess", result.getViewName());
    }
    /**
     * Test CreateAccount with a bad email address.
     */
    @Test
    public void testCreateAccountBadEmail() {
        when(mockRegistrationForm.getUsername()).thenReturn(USERNAME);
        when(mockRegistrationForm.getFirstname()).thenReturn(FIRSTNAME);
        when(mockRegistrationForm.getLastname()).thenReturn(LASTNAME);
        when(mockRegistrationForm.getEmail()).thenReturn(EMAIL);
        when(mockRegistrationForm.getConfirmEmail()).thenReturn("wrong@Email.com");

        ModelAndView result = accountController.createAccount(mockRegistrationForm, null);
        assertEquals("account/registrationForm", result.getViewName());
        assertEquals("Sorry, your Email addresses didn't match.", result.getModel().get("message"));
    }
    /**
     * Test CreateAccount when an Exception is thrown.
     */
    @Test
    public void testCreateAccountException() {
        stub(mockAccount.getUsername()).toThrow(new IndexOutOfBoundsException());
        ModelAndView result = accountController.createAccount(mockRegistrationForm, null);
        assertEquals("account/registrationForm", result.getViewName());
        assertEquals("There was an issue creating your account."
                + "Please contact the administrator for assistance.", result.getModel().get("message"));
    }
    /**
     * Test to make sure SetPassword works.
     */
    @Test
    public void testSetPasswordSuccess() {
        when(mockPassForm.getPassword()).thenReturn(PASSFIELD);
        when(mockPassForm.getConfirmPassword()).thenReturn(PASSFIELD);
        when(mockPassForm.getUsername()).thenReturn(USERNAME);
        when(mockPassForm.getChecksum()).thenReturn(CHECKSUM);
        when(mockAccountService.loadByUsernameAndChecksum(USERNAME, CHECKSUM)).thenReturn(mockAccount);

        ModelAndView result = accountController.setPassword(mockPassForm);

        verify(mockAccount).setEnabled(true);
        verify(mockAccount).setHashedPassword(PASSFIELD);
        assertEquals("redirect:/", result.getViewName());
    }

    @Test
    public void testGetAndSetBaseUrl() {
            assertNull(accountController.getBaseUrl());
            accountController.setBaseUrl(BASEURL);
            assertEquals(BASEURL, accountController.getBaseUrl());
    }
    @Test
    public void testGetAndSetAccountService() {
        assertEquals(mockAccountService, accountController.getAccountService());
        accountController.setAccountService(null);
        assertNull(accountController.getAccountService());
    }
    @Test
    public void testGetAndSetRoleService() {
        assertEquals(mockRoleService, accountController.getRoleService());
        accountController.setRoleService(null);
        assertNull(accountController.getRoleService());
    }

    @Test
    public void testForgetPasswordForm() {
       ModelAndView mav = accountController.forgotPasswordForm();
       assertEquals("account/forgotPasswordForm", mav.getViewName());
       assertEquals(ForgotPasswordForm.class, mav.getModel().get("forgotPasswordForm").getClass());
    }

    @Test
    public void testEditAccountForm() {
        SecurityContextHolder.setContext(mockContext);
        when(mockContext.getAuthentication().getName()).thenReturn(USERNAME);
        when(mockAccountService.loadByUsername(USERNAME)).thenReturn(mockAccount);
        when(mockAccount.getFirstname()).thenReturn(FIRSTNAME);
        when(mockAccount.getLastname()).thenReturn(LASTNAME);

        ModelAndView mav = accountController.editAccountForm();
        assertEquals("account/editAccountForm", mav.getViewName());
        assertEquals(PersonalInformationForm.class, mav.getModel().get("personalInformationForm").getClass());
        assertEquals(PasswordChangeForm.class, mav.getModel().get("passwordChangeForm").getClass());
        PersonalInformationForm pif = (PersonalInformationForm) mav.getModel().get("personalInformationForm");
        assertEquals(FIRSTNAME, pif.getFirstname());
        assertEquals(LASTNAME, pif.getLastname());
    }

    @Test
    public void testSavePersonalInformationForm() {
        SecurityContextHolder.setContext(mockContext);
        when(mockContext.getAuthentication().getName()).thenReturn(USERNAME);
        when(mockAccountService.loadByUsername(USERNAME)).thenReturn(mockAccount);
        when(mockPersonalInformationForm.getFirstname()).thenReturn(FIRSTNAME);
        when(mockPersonalInformationForm.getLastname()).thenReturn(LASTNAME);

        ModelAndView mav = accountController.savePersonalInformationForm(mockPersonalInformationForm);

        assertEquals("Personal Information Updated.", mav.getModel().get("message"));
        assertEquals(mockPersonalInformationForm, mav.getModel().get("personalInformationForm"));
        assertEquals(PasswordChangeForm.class, mav.getModel().get("passwordChangeForm").getClass());
        assertEquals("account/editAccountForm", mav.getViewName());
    }

    @Test
    public void testSavePersonalInformationFormNoAccount() {
        SecurityContextHolder.setContext(mockContext);
        when(mockContext.getAuthentication().getName()).thenReturn(USERNAME);
        when(mockAccountService.loadByUsername(USERNAME)).thenReturn(null);

        ModelAndView mav = accountController.savePersonalInformationForm(mockPersonalInformationForm);

        assertEquals("Odd, your account wasn't found.. are you logged in?", mav.getModel().get("message"));
        assertEquals(mockPersonalInformationForm, mav.getModel().get("personalInformationForm"));
        assertEquals(PasswordChangeForm.class, mav.getModel().get("passwordChangeForm").getClass());
        assertEquals("account/editAccountForm", mav.getViewName());
    }
    /**
     * Test SetPassword when failing.
     */
    @Test
    public void testSetPasswordFail() {
        when(mockPassForm.getPassword()).thenReturn(PASSFIELD);
        when(mockPassForm.getConfirmPassword()).thenReturn("mismatching passfield");

        ModelAndView result = accountController.setPassword(mockPassForm);

        assertEquals("account/activationSuccess", result.getViewName());
        assertEquals("Your passwords did not match, try again.", result.getModel().get("message"));
        assertEquals(mockPassForm, result.getModel().get("passform"));
    }
    /**
     * Test GetContextAccount to pull a user's account from the Security Context.
     */
    @Test
    public void testGetContextAccount() {
        SecurityContextHolder.setContext(mockContext);
        when(mockContext.getAuthentication().getName()).thenReturn(USERNAME);
        when(mockAccountService.loadByUsername(USERNAME)).thenReturn(mockAccount);
        Account account = accountController.getContextAccount();
        assertEquals(mockAccount, account);
    }
    /**
     * Test displaying the PasswordChangeForm to make sure it returns properly.
     */
    @Test
    public void testSavePasswordChangeForm() {
        SecurityContextHolder.setContext(mockContext);
        when(mockContext.getAuthentication().getName()).thenReturn(USERNAME);
        when(mockAccountService.loadByUsername(USERNAME)).thenReturn(mockAccount);
        when(mockAccount.verifyPassword(PASSWORD)).thenReturn(true);
        when(mockPasswordChangeForm.getCurrentPassword()).thenReturn(PASSWORD);
        when(mockPasswordChangeForm.getNewPassword()).thenReturn(PASSWORD);
        when(mockPasswordChangeForm.getConfirmPassword()).thenReturn(PASSWORD);
        when(mockPasswordChangeForm.compareNewPasswords()).thenReturn(true);
        ModelAndView results = accountController.savePasswordChangeForm(mockPasswordChangeForm);
        assertEquals("account/editAccountForm", results.getViewName());
        //TODO need to test the rest of the code...
    }

    /**
     * Test DisplayUser finding a user.
     */
    @Test
    public void testDisplayUserSuccess() {
        when(mockAccount.getPassword()).thenReturn(PASSFIELD);
        when(mockAccount.getUsername()).thenReturn(USERNAME);
        when(mockAccount.getFirstname()).thenReturn(FIRSTNAME);
        when(mockAccount.getLastname()).thenReturn(LASTNAME);
        when(mockAccount.getEmail()).thenReturn(EMAIL);
        when(mockAccountService.loadByUsername(USERNAME)).thenReturn(mockAccount);

        ModelAndView mav = accountController.displayUser(USERNAME);

        assertEquals(USERNAME, mav.getModel().get("message"));
        assertEquals("account/viewUser", mav.getViewName());
        assertEquals(mockAccount, mav.getModel().get("account"));
    }
    /**
     * Test DisplayUser when a user is not found.
     */
    @Test
    public void testDisplayUserNotFound() {
        when(mockAccount.getPassword()).thenReturn(PASSFIELD);
        when(mockAccount.getUsername()).thenReturn(USERNAME);
        when(mockAccount.getFirstname()).thenReturn(FIRSTNAME);
        when(mockAccount.getLastname()).thenReturn(LASTNAME);
        when(mockAccount.getEmail()).thenReturn(EMAIL);
        when(mockAccountService.loadByUsername(USERNAME)).thenReturn(null);

        ModelAndView mav = accountController.displayUser(USERNAME);

        assertEquals("I'm sorry, " + USERNAME + " was not found.", mav.getModel().get("message"));
        assertEquals("account/viewUser", mav.getViewName());
    }
    /**
     * Test DisplayUser when an Exception is thrown.
     */
    @Test
    public void testDisplayUserException() {
        when(mockAccount.getPassword()).thenReturn(PASSFIELD);
        when(mockAccount.getUsername()).thenReturn(USERNAME);
        when(mockAccount.getFirstname()).thenReturn(FIRSTNAME);
        when(mockAccount.getLastname()).thenReturn(LASTNAME);
        when(mockAccount.getEmail()).thenReturn(EMAIL);
        stub(mockAccountService.loadByUsername(USERNAME)).toThrow(new IndexOutOfBoundsException());

        ModelAndView mav = accountController.displayUser(USERNAME);

        assertEquals("Something failed while trying to display " + USERNAME, mav.getModel().get("message"));
        assertEquals("account/activationFailure", mav.getViewName());
    }
}