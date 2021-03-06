package com.morgajel.spoe.web;


import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Account.Experience;

/**
 * This is used to capture and set the PersonalInformationForm fields.
 * It includes a list of all Personal Information you'd like to possibly modify.
 */
public class PersonalInformationForm {
    //
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    @Valid
    private Experience writingExperience;
    @Valid
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
