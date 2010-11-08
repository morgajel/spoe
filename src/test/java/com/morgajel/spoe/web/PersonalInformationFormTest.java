/**
 * 
 */
package com.morgajel.spoe.web;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Role;
import com.morgajel.spoe.model.Account.Experience;

/**
 * @author jmorgan
 *
 */
public class PersonalInformationFormTest {

    private static final String FIRSTNAME = "Bob";
    private static final String LASTNAME = "Dole";
    private static final Experience WRITINGEXPERIENCE = Account.Experience.Advanced;
    private static final Experience REVIEWINGEXPERIENCE = Account.Experience.Novice;
    private PersonalInformationForm personalInformationForm;
    private Account mockAccount;

    @Before
    public void setUp() throws Exception {
        mockAccount = mock(Account.class);
        personalInformationForm = new PersonalInformationForm();
    }

    @After
    public void tearDown() throws Exception {
        mockAccount = null;
        personalInformationForm = null;
    }

    @Test
    public void testSetFirstname() {
        assertNull(personalInformationForm.getFirstname());
        personalInformationForm.setFirstname(FIRSTNAME);
        assertEquals(FIRSTNAME, personalInformationForm.getFirstname());
    }

    @Test
    public void testSetLastname() {
        assertNull(personalInformationForm.getLastname());
        personalInformationForm.setLastname(LASTNAME);
        assertEquals(LASTNAME, personalInformationForm.getLastname());
    }

    @Test
    public void testSetWritingExperience() {
        assertNull(personalInformationForm.getWritingExperience());
        personalInformationForm.setWritingExperience(WRITINGEXPERIENCE);
        assertEquals(WRITINGEXPERIENCE, personalInformationForm.getWritingExperience());
    }

    @Test
    public void testSetReviewingExperience() {
        assertNull(personalInformationForm.getReviewingExperience());
        personalInformationForm.setReviewingExperience(REVIEWINGEXPERIENCE);
        assertEquals(REVIEWINGEXPERIENCE, personalInformationForm.getReviewingExperience());
    }

    @Test
    public void testgetExperienceList() {
        assertArrayEquals(Account.Experience.values(), personalInformationForm.getExperienceList());
    }

    @Test
    public void testLoadAccount() {
        assertNull(personalInformationForm.getLastname());
        assertNull(personalInformationForm.getFirstname());
        assertNull(personalInformationForm.getWritingExperience());
        assertNull(personalInformationForm.getReviewingExperience());
        when(mockAccount.getFirstname()).thenReturn(FIRSTNAME);
        when(mockAccount.getLastname()).thenReturn(LASTNAME);
        when(mockAccount.getReviewingExperience()).thenReturn(REVIEWINGEXPERIENCE);
        when(mockAccount.getWritingExperience()).thenReturn(WRITINGEXPERIENCE);
        personalInformationForm.loadAccount(mockAccount);
        assertEquals(LASTNAME, personalInformationForm.getLastname());
        assertEquals(FIRSTNAME, personalInformationForm.getFirstname());
        assertEquals(WRITINGEXPERIENCE, personalInformationForm.getWritingExperience());
        assertEquals(REVIEWINGEXPERIENCE, personalInformationForm.getReviewingExperience());
    }

    @Test
    public void testToString() {
        personalInformationForm.setLastname(LASTNAME);
        personalInformationForm.setFirstname(FIRSTNAME);
        personalInformationForm.setWritingExperience(WRITINGEXPERIENCE);
        personalInformationForm.setReviewingExperience(REVIEWINGEXPERIENCE);
        String toString = "PersonalInformationForm [firstname=" + FIRSTNAME
        + ", lastname=" + LASTNAME + ", writingExperience="
        + WRITINGEXPERIENCE + ", reviewingExperience="
        + REVIEWINGEXPERIENCE + "]";
        assertEquals(toString, personalInformationForm.toString());
    }

}
