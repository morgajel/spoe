package com.morgajel.spoe.web;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Role;
import com.morgajel.spoe.model.Snippet;

/**
 * @author jmorgan
 *
 */
public class ForgotPasswordFormTest {

    private static final String USERNAME = "bobDole";
    private static final String EMAIL = "foo@bar.com";
    private ForgotPasswordForm forgotPasswordForm;
    @Before
    public void setUp() throws Exception {
        forgotPasswordForm = new ForgotPasswordForm();
    }
    @After
    public void tearDown() throws Exception {
        forgotPasswordForm = null;
    }
    @Test
    public void testGetAndSetUsername() {
        assertNull(forgotPasswordForm.getUsername());
        forgotPasswordForm.setUsername(USERNAME);
        assertEquals(USERNAME, forgotPasswordForm.getUsername());
    }

    @Test
    public void testGetAndSetEmail() {
        assertNull(forgotPasswordForm.getEmail());
        forgotPasswordForm.setEmail(EMAIL);
        assertEquals(EMAIL, forgotPasswordForm.getEmail());
    }

}
