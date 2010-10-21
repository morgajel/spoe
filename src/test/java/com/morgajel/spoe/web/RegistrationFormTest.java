package com.morgajel.spoe.web;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.apache.velocity.app.VelocityEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContext;

import com.morgajel.spoe.controller.AccountController;
import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Role;
import com.morgajel.spoe.service.AccountService;
import com.morgajel.spoe.service.RoleService;

public class RegistrationFormTest {
    private RegistrationForm registrationForm;
    private static final String USERNAME = "morgo2";
    private static final String FIRSTNAME = "Jesse";
    private static final String LASTNAME = "Morgan";
    private static final String EMAIL = "morgo2@example.com";
    private static final String PASSWORD = "MatchedLuggage12345";
    @Before
    public void setUp() throws Exception {
        registrationForm = new RegistrationForm();
    }

    @After
    public void tearDown() throws Exception {
        registrationForm = null;
    }

    @Test
    public void testGetAndSetUsername() {
        registrationForm.setUsername(USERNAME);
        assertEquals(USERNAME, registrationForm.getUsername());
    }
    @Test
    public void testGetAndSetConfirmEmail() {
        registrationForm.setConfirmEmail(EMAIL);
        assertEquals(EMAIL, registrationForm.getConfirmEmail());
    }
    @Test
    public void testGetAndSetFirstname() {
        registrationForm.setFirstname(FIRSTNAME);
        assertEquals(FIRSTNAME, registrationForm.getFirstname());
    }

    @Test
    public void testGetAndSetLastname() {
        registrationForm.setLastname(LASTNAME);
        assertEquals(LASTNAME, registrationForm.getLastname());
    }

    @Test
    public void testGetAndSetEmail() {
        registrationForm.setEmail(EMAIL);
        assertEquals(EMAIL, registrationForm.getEmail());
    }
}
