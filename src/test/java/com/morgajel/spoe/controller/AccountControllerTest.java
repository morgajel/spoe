package com.morgajel.spoe.controller;

import static org.junit.Assert.*;

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
import com.morgajel.spoe.service.AccountService;
import com.morgajel.spoe.service.RoleService;
import com.morgajel.spoe.web.SetPasswordForm;
import com.morgajel.spoe.controller.AccountController;
import static org.mockito.Mockito.*;

//TODO how do you test a controller which uses a service implementation that is created via beans?
public class AccountControllerTest {
	private AccountController accountController;
	private Account mockAccount;
	private SecurityContext mockContext;
	private Role mockRole;
	private AccountService mockAccountService;
	private RoleService mockRoleService;
	private MailSender mockMailSender;
	private SetPasswordForm mockPassForm;
    private SimpleMailMessage mockTemplateMessage;
    private VelocityEngine mockVelocityEngine;
	private final String username="morgo2";
	private final String passfield="255edd2793e5286d4441ea6bfba734b59e915864";
	private final String tempHash="df9dd14cbdb3b00f8a54b66f489241e8aeb903ff";
	private final String checksum="279d8d8a18b94782ef606fbbadd6c011b1692ad0"; //morgo2+temphash+0
    
	@Before
	public void setUp() throws Exception {
		mockAccountService = mock(AccountService.class);
		mockRoleService = mock(RoleService.class);
		mockAccount = mock(Account.class);
		mockContext = mock(SecurityContext.class,RETURNS_DEEP_STUBS);
		mockRole = mock(Role.class);
		mockMailSender = mock(MailSender.class);
		mockPassForm=mock(SetPasswordForm.class);
		mockTemplateMessage=mock(SimpleMailMessage.class);
		mockVelocityEngine=mock(VelocityEngine.class);
		accountController = new AccountController();
		accountController.setAccountService(mockAccountService);
		accountController.setRoleService(mockRoleService);
		accountController.setMailSender(mockMailSender);
		accountController.setTemplateMessage(mockTemplateMessage);
		accountController.setVelocityEngine(mockVelocityEngine);
	}
	@After
	public void tearDown() throws Exception {
		mockAccountService = null;
		mockAccount = null;
		mockRole = null;
		mockMailSender = null;
		mockPassForm=null;
		mockTemplateMessage=null;
		mockVelocityEngine=null;
		mockContext=null;
		accountController = null;
	}
	
	@Test
	public void testActivateAccountBestcase(){
		
		// Test Bestcase scenario success
		when(mockAccount.getPassword()).thenReturn(passfield);
		when(mockAccount.getUsername()).thenReturn(username);
		when(mockAccount.getEnabled()).thenReturn(false);
		when(mockAccountService.loadByUsername(username)).thenReturn(mockAccount);
		when(mockAccountService.loadByUsernameAndChecksum(username,checksum)).thenReturn(mockAccount);
		ModelAndView results=accountController.activateAccount(username, checksum,new SetPasswordForm());
		assertEquals("account/activationSuccess",results.getViewName());
	}
	@Test
	public void testActivateAccountNoMatch(){

		// non-existent account failure
		when(mockAccountService.loadByUsername(username)).thenReturn(null);
		when(mockAccountService.loadByUsernameAndChecksum(username,checksum)).thenReturn(null);
		ModelAndView results=accountController.activateAccount(username,checksum,new SetPasswordForm());
		assertEquals("account/activationFailure",results.getViewName());
		//TODO assertEquals("I'm sorry, that account was not found.",results.someting());
	}
	@Test
	public void testActivateAccountChecksumMismatch(){
		//Test checksum mismatch
		when(mockAccountService.loadByUsername(username)).thenReturn(mockAccount);
		when(mockAccount.getPassword()).thenReturn("some wrong passfield");
		when(mockAccount.getEnabled()).thenReturn(false);
		ModelAndView results=accountController.activateAccount(username, checksum,new SetPasswordForm());
		assertEquals("account/activationFailure",results.getViewName());
	}
	@Test
	public void testActivateAccountAlreadyEnabled(){
		//Test already enabled failure
		when(mockAccountService.loadByUsernameAndChecksum(username,checksum)).thenReturn(mockAccount);
		when(mockAccount.getEnabled()).thenReturn(true);
		ModelAndView results=accountController.activateAccount(username, checksum,new SetPasswordForm());
		assertEquals("account/activationFailure",results.getViewName());

	}
	@Test
	public void testActivateAccountThrowException(){
		//Test throw exception caught
		stub(mockAccountService.loadByUsernameAndChecksum(username,checksum)).toThrow(new RuntimeException());
		ModelAndView results=accountController.activateAccount(username, checksum,new SetPasswordForm());
		assertEquals("account/activationFailure",results.getViewName());
		assertEquals("<!--java.lang.RuntimeException-->",results.getModel().get("message"));
	}
	
	@Test
	public void testSendRegEmail(){
		when(mockAccount.getPassword()).thenReturn(passfield);
		when(mockAccount.getUsername()).thenReturn(username);
		when(mockAccount.getEnabled()).thenReturn(false);
		
		accountController.sendRegEmail(mockAccount,"http://example.com/sometesturl");
		verify(mockMailSender).send((SimpleMailMessage) anyObject());

	}
	
	
	@Test
	public void testSetAndGetTemplateMessage(){ 
		SimpleMailMessage simpleMailMessage = mock(SimpleMailMessage.class);
		accountController.setTemplateMessage(simpleMailMessage);
		//using deep stubs, boooo
		assertEquals(accountController.getTemplateMessage(),simpleMailMessage);
	}		
	@Test
	public void testSetAndGetMailSender(){ 
		MailSender mailSender = mock(MailSender.class);
		accountController.setMailSender(mailSender);
		//using deep stubs, boooo
		assertEquals(accountController.getMailSender(),mailSender);
	}		
	
	@Test
	public void testSetAndGetVelocityEngine(){ 
		VelocityEngine velocityEngine = mock(VelocityEngine.class);
		accountController.setVelocityEngine(velocityEngine);
		//using deep stubs, boooo
		assertEquals(accountController.getVelocityEngine(),velocityEngine);
	}
	@Test
	public void testDefaultView(){
		SecurityContextHolder.setContext(mockContext);
		when(mockContext.getAuthentication().getName()).thenReturn(username);
		when(mockAccountService.loadByUsername(username)).thenReturn(mockAccount);
		ModelAndView mav=accountController.defaultView();
		assertEquals("account/view",mav.getViewName());
		assertEquals("show the default view for "+username,mav.getModel().get("message"));
	}	
	@Test
	public void testGetRegistrationForm(){
		ModelAndView mav= accountController.getRegistrationForm(mockAccount);
		assertEquals(mav.getViewName(),"account/registrationForm");
	}
	@Test
	public void testCreateAccountSuccess(){

		when(mockAccount.getUsername()).thenReturn(username);
		when(mockAccount.activationChecksum()).thenReturn(checksum);
		when(mockRoleService.loadByName("ROLE_REVIEWER")).thenReturn(mockRole);

		ModelAndView result= accountController.createAccount(mockAccount, null);
		
		verify(mockAccount).activationChecksum();
		verify(mockAccountService).addAccount(mockAccount);
		verify(mockRoleService).loadByName("ROLE_REVIEWER");
		verify(mockAccount).addRole((Role) anyObject());
		verify(mockAccountService).saveAccount(mockAccount);
		assertEquals(accountController.activationurl+username+"/"+checksum,result.getModel().get("url"));
		assertEquals("account/registrationSuccess",result.getViewName());
	}
	@Test
	public void testCreateAccountFail(){
		stub(mockAccount.getUsername()).toThrow(new RuntimeException());
		ModelAndView result= accountController.createAccount(mockAccount, null);
		assertEquals("account/registrationForm",result.getViewName());
		assertEquals("There was an issue creating your account."
				+ "Please contact the administrator for assistance.",result.getModel().get("message"));	
	}
	@Test
	public void testSetPasswordSuccess(){
		when(mockPassForm.getPassword()).thenReturn(passfield);
		when(mockPassForm.getConfirmPassword()).thenReturn(passfield);
		when(mockPassForm.getUsername()).thenReturn(username);
		when(mockPassForm.getChecksum()).thenReturn(checksum);
		when(mockAccountService.loadByUsernameAndChecksum(username, checksum)).thenReturn(mockAccount);

		ModelAndView result= accountController.setPassword(mockPassForm);
		
		verify(mockAccount).setEnabled(true);
		verify(mockAccount).setHashedPassword(passfield);
		assertEquals("redirect:/",result.getViewName());
	}
	@Test
	public void testSetPasswordFail(){
		when(mockPassForm.getPassword()).thenReturn(passfield);
		when(mockPassForm.getConfirmPassword()).thenReturn("mismatching passfield");

		ModelAndView result= accountController.setPassword(mockPassForm);
		
		assertEquals("account/activationSuccess",result.getViewName());
		assertEquals("Your passwords did not match, try again.",result.getModel().get("message"));
		assertEquals(mockPassForm,result.getModel().get("passform"));
	}
}
