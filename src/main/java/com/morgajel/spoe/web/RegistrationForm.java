package com.morgajel.spoe.web;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Registration Form is used during account creation.
 * @author jmorgan
 *
 */
public class RegistrationForm {
//TODO need documentation and tests
    @Length(min = 4, max = 15)
    private String username;
    @Email
    private String email;
    @Email
    private String confirmEmail;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    /**
     * Returns the username.
     * @return String
     */
    public String getUsername() {
        return username;
    }
    /**
     * Sets the username.
     * @param pUsername username in form.
     */
    public void setUsername(String pUsername) {
        this.username = pUsername;
    }
    /**
     * Returns the confirmation email.
     * @return String
     */
    public String getConfirmEmail() {
        return confirmEmail;
    }
    /**
     * Sets the confirmation email.
     * @param pConfirmEmail the confirmation of the email address
     */
    public void setConfirmEmail(String pConfirmEmail) {
        this.confirmEmail = pConfirmEmail;
    }
    /**
     * Returns the first name of the user.
     * @return String
     */
    public String getFirstname() {
        return firstname;
    }
    /**
     * Sets the First name in the form.
     * @param pFirstname first name of user
     */
    public void setFirstname(String pFirstname) {
        this.firstname = pFirstname;
    }
    /**
     * Returns the Last name of the user.
     * @return String
     */
    public String getLastname() {
        return lastname;
    }
    /**
     * Sets the last name of the user.
     * @param pLastname last name of the user.
     */
    public void setLastname(String pLastname) {
        this.lastname = pLastname;
    }
    /**
     * Returns the email address of the user.
     * @return String
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the Email address.
     * @param pEmail Email address of the user.
     */
    public void setEmail(String pEmail) {
        this.email = pEmail;
    }
}
