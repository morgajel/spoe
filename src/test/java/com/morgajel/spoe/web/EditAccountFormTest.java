package com.morgajel.spoe.web;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.morgajel.spoe.model.Account;
import static org.mockito.Mockito.*;
/**
 * Test the EditAccountForm.
 * @author jmorgan
 *
 */
public class EditAccountFormTest {
    private EditAccountForm editAccountForm;
    private Account mockAccount;
    private static final String FIRSTNAME = "Jesse";
    private static final String LASTNAME = "Morgan";
    private static final String EMAIL = "morgo2@example.com";
    private static final String PASSWORDS = "MatchedLuggage12345";

    @Before
    public void setUp() throws Exception {
        editAccountForm = new EditAccountForm();
        mockAccount = mock(Account.class);
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testGetAndSetConfirmEmail() {
        editAccountForm.setConfirmEmail(EMAIL);
        assertEquals(EMAIL, editAccountForm.getConfirmEmail());
    }
    @Test
    public void testGetAndSetFirstname() {
        editAccountForm.setFirstname(FIRSTNAME);
        assertEquals(FIRSTNAME, editAccountForm.getFirstname());
    }

    @Test
    public void testGetAndSetLastname() {
        editAccountForm.setLastname(LASTNAME);
        assertEquals(LASTNAME, editAccountForm.getLastname());
    }

    @Test
    public void testGetAndSetEmail() {
        editAccountForm.setEmail(EMAIL);
        assertEquals(EMAIL, editAccountForm.getEmail());
    }
    @Test
    public void testGetAndSetOldPassword() {
        editAccountForm.setOldPassword(PASSWORDS);
        assertEquals(PASSWORDS, editAccountForm.getOldPassword());
    }

    @Test
    public void testGetAndSetPassword() {
        editAccountForm.setPassword(PASSWORDS);
        assertEquals(PASSWORDS, editAccountForm.getPassword());
    }
    @Test
    public void testCompareEmails() {
        editAccountForm.setEmail(EMAIL);
        editAccountForm.setConfirmEmail(EMAIL);
        assertTrue(editAccountForm.compareEmails());
        editAccountForm.setConfirmEmail("bad@email.com");
        assertFalse(editAccountForm.compareEmails());
    }
    @Test
    public void testCompareNewPasswords() {
        editAccountForm.setPassword(PASSWORDS);
        editAccountForm.setConfirmPassword(PASSWORDS);
        assertTrue(editAccountForm.compareNewPasswords());
        editAccountForm.setConfirmPassword("off password");
        assertFalse(editAccountForm.compareNewPasswords());

    }

    @Test
    public void testLoadAccount() {
        when(mockAccount.getFirstname()).thenReturn(FIRSTNAME);
        when(mockAccount.getLastname()).thenReturn(LASTNAME);
        when(mockAccount.getPassword()).thenReturn(PASSWORDS);
        when(mockAccount.getEmail()).thenReturn(EMAIL);

        editAccountForm.setEmail("original@email.com");
        editAccountForm.setConfirmEmail("originalConfirm@email.com");
        editAccountForm.setOldPassword("oldpassword");
        editAccountForm.setPassword("newpassword");
        editAccountForm.setConfirmPassword("confirmpassword");

        editAccountForm.loadAccount(mockAccount);

        assertEquals(EMAIL, editAccountForm.getEmail());
        assertEquals(EMAIL, editAccountForm.getConfirmEmail());
        assertEquals("", editAccountForm.getOldPassword());
        assertEquals(editAccountForm.getPasswordHolder(), editAccountForm.getPassword());
        assertEquals(editAccountForm.getPasswordHolder(), editAccountForm.getConfirmPassword());
    }

}
