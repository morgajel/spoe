package com.morgajel.spoe.controller;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	private Role mockRole;
	private AccountService mockAccountService;
	private RoleService mockRoleService;
	private MailSender mockMailSender;
    private SimpleMailMessage mockTemplateMessage;
    private VelocityEngine mockVelocityEngine;
	private final String username="morgo2";
	private final String passfield="255edd2793e5286d4441ea6bfba734b59e915864";
	private final String tempHash="df9dd14cbdb3b00f8a54b66f489241e8aeb903ff";
	private final String checksum="279d8d8a18b94782ef606fbbadd6c011b1692ad0"; //morgo2+temphash+0
    
	@Before
	public void setUp() throws Exception {
		mockAccountService = mock(AccountService.class);
		mockAccount = mock(Account.class);
		mockRole = mock(Role.class);
		mockMailSender = mock(MailSender.class);
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
		mockTemplateMessage=null;
		mockVelocityEngine=null;
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
		when(mockAccountService.loadByUsername(username)).thenReturn(mockAccount);
		when(mockAccount.getPassword()).thenReturn("some wrong passphrase");
		when(mockAccount.getEnabled()).thenReturn(true);
		ModelAndView results=accountController.activateAccount(username, checksum,new SetPasswordForm());
		assertEquals("account/activationFailure",results.getViewName());

	}
	@Test
	public void testActivateAccountThrowException(){
		//Test throw exception caught
		stub(mockAccountService.loadByUsername(username)).toThrow(new RuntimeException());
		ModelAndView results=accountController.activateAccount(username, checksum,new SetPasswordForm());
		assertEquals("account/activationFailure",results.getViewName());
	}
	
	@Test
	public void testSendRegEmail(){
		when(mockAccount.getPassword()).thenReturn(passfield);
		when(mockAccount.getUsername()).thenReturn(username);
		when(mockAccount.getEnabled()).thenReturn(false);
		
		accountController.sendRegEmail(mockAccount);
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
	public void testGetRegistrationForm(){
		ModelAndView mav= accountController.getRegistrationForm(mockAccount);
		assertEquals(mav.getViewName(),"account/registrationForm");
		
	}
}
