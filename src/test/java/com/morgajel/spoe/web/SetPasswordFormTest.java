package com.morgajel.spoe.web;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * Tests for SetPasswordForm.
 * @author jmorgan
 *
 */
public class SetPasswordFormTest {

    private SetPasswordForm passwordForm;
    /**
     * Setup initializes all of the mockups used in each test.
     * @throws Exception generic exception
     */
    @Before
    public void setUp() throws Exception {
         passwordForm = new SetPasswordForm();
    }
    /**
     * Tear down the test and destroy any mockups and objects used.
     * @throws Exception generic exception
     */
    @After
    public void tearDown() throws Exception {
        passwordForm = null;
    }
    /**
     * TODO this is incomplete.
     */
    @Test
    public final void testSetPasswordForm() {
        //TODO wtf is this supposed to test? anything?
    }
    /**
     * Test SetAndGetChecksum functionality.
     */
    @Test
    public final void testSetAndGetChecksum() {
        String checksum = "1234567890123456789012345678901234567890";
        assertNull(passwordForm.getChecksum());
        passwordForm.setChecksum(checksum);
        assertEquals(checksum, passwordForm.getChecksum());
    }
    /**
     * Test SetAndGetUsername functionality.
     */
    @Test
    public final void testSetAndGetUsername() {
        String username = "bobdole";
        assertNull(passwordForm.getUsername());
        passwordForm.setUsername(username);
        assertEquals(username, passwordForm.getUsername());
    }
    /**
     * Test SetAndGetPassword functionality.
     */
    @Test
    public final void testSetAndGetPassword() {
        String password = "password123";
        assertNull(passwordForm.getPassword());
        passwordForm.setPassword(password);
        assertEquals(password, passwordForm.getPassword());
    }
    /**
     * Test SetAndGetConfirmPassword functionality.
     */
    @Test
    public final void testSetAndGetConfirmPassword() {
        String confirmPassword = "password123";
        assertNull(passwordForm.getConfirmPassword());
        passwordForm.setConfirmPassword(confirmPassword);
        assertEquals(confirmPassword, passwordForm.getConfirmPassword());
    }
    /**
     * Test ComparePasswords functionality.
     */
    @Test
    public final void testComparePasswords() {
        String password = "password123";
        String badPassword = "badpassword";
        passwordForm.setPassword(password);
        passwordForm.setConfirmPassword(password);
        assertTrue(passwordForm.comparePasswords());
        passwordForm.setConfirmPassword(badPassword);
        assertFalse(passwordForm.comparePasswords());
    }
}
