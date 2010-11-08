package com.morgajel.spoe.web;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jmorgan
 *
 */
public class PasswordChangeFormTest {

    private static final String PASSWORD = "Jabberwocky";
    private static final String BADPASS = "Mismatch";
    private PasswordChangeForm passwordChangeForm;
    @Before
    public void setUp() throws Exception {
        passwordChangeForm = new PasswordChangeForm();
    }

    @After
    public void tearDown() throws Exception {
        passwordChangeForm = null;
    }

    @Test
    public void testGetAndSetCurrentPassword() {
        assertNull(passwordChangeForm.getCurrentPassword());
        passwordChangeForm.setCurrentPassword(PASSWORD);
        assertEquals(PASSWORD, passwordChangeForm.getCurrentPassword());
    }

    @Test
    public void testGetAndSetNewPassword() {
        assertNull(passwordChangeForm.getNewPassword());
        passwordChangeForm.setNewPassword(PASSWORD);
        assertEquals(PASSWORD, passwordChangeForm.getNewPassword());

    }

    @Test
    public void testSetConfirmPassword() {
        assertNull(passwordChangeForm.getConfirmPassword());
        passwordChangeForm.setConfirmPassword(PASSWORD);
        assertEquals(PASSWORD, passwordChangeForm.getConfirmPassword());

    }

    @Test
    public void testCompareNewPasswords() {
        passwordChangeForm.setNewPassword(PASSWORD);
        passwordChangeForm.setConfirmPassword(PASSWORD);
        assertTrue(passwordChangeForm.compareNewPasswords());
        passwordChangeForm.setConfirmPassword(BADPASS);
        assertFalse(passwordChangeForm.compareNewPasswords());
    }

}
