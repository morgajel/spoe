package com.morgajel.spoe.web;

import java.util.Collection;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Account.Experience;

/**
 * This is used to capture and set the EditAccountForm fields.
 * It includes a list of all account parameters you'd like to possibly modify.
 */
public class PreferredTagForm {
    //
    private String firstname;
    private String lastname;
    private Experience writingExperience;
    private Experience reviewingExperience;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String pFirstname) {
        this.firstname = pFirstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String pLastname) {
        this.lastname = pLastname;
    }

    public Experience getWritingExperience() {
        return writingExperience;
    }

    public void setWritingExperience(Experience pWritingExperience) {
        this.writingExperience = pWritingExperience;
    }

    public Experience getReviewingExperience() {
        return reviewingExperience;
    }

    public void setReviewingExperience(Experience pReviewingExperience) {
        this.reviewingExperience = pReviewingExperience;
    }
    public static Experience[] getExperienceList() {
        return Experience.values();
    }
    
    /**
     * This is used to display basic account information in the PersonalInformationForm field.
     * @param account Account you wish to load
     */
    public void loadAccount(Account account) {
        firstname = account.getFirstname();
        lastname = account.getLastname();
        writingExperience = account.getWritingExperience();
        reviewingExperience = account.getReviewingExperience();
    }

    @Override
    public String toString() {
        return "PersonalInformationForm [firstname=" + firstname
                + ", lastname=" + lastname + ", writingExperience="
                + writingExperience + ", reviewingExperience="
                + reviewingExperience + "]";
    }
}
