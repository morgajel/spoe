package com.morgajel.spoe.web;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.morgajel.spoe.annotation.ValidUsername;
/**
 * Forgot Password Form is used when resetting a password.
 * @author jmorgan
 *
 */
public class ForgotPasswordForm {
//TODO need documentation and tests
    @ValidUsername
    private String username;
    @Email
    private String email;
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
