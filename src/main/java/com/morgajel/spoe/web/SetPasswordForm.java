package com.morgajel.spoe.web;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Simple object to catch the initial setPassword form.
 */
public class SetPasswordForm {
    @Length(min = 40, max = 40)
    private String checksum;
    @Length(min = 4, max = 15)
    private String username;
    @NotEmpty
    @Min(value = 8)
    private String password;
    @NotEmpty
    @Min(value = 8)
    private String confirmPassword;

    /**
     * Returns the checksum provided by a hidden field in the form.
     * @return String
     */
    public String getChecksum() {
        return checksum;
    }
    /**
     * Sets the checksum; used to prove the user received the email.
     * @param pChecksum set the checksum in the form
     */
    public void setChecksum(String pChecksum) {
        this.checksum = pChecksum;
    }
    /**
     * Gets the username provided by the password form.
     * @return String
     */
    public String getUsername() {
        return username;
    }
    /**
     * Sets the username provided by the password form.
     * @param pUsername username for account
     */
    public void setUsername(String pUsername) {
        this.username = pUsername;
    }
    /**
     * Returns the password entered by the user- WARNING, PLAIN TEXT STILL!
     * @return String
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets the password entered by the user- WARNING, PLAIN TEXT STILL!
     * @param pPassword password set in form
     */
    public void setPassword(String pPassword) {
        this.password = pPassword;
    }
    /**
     * Returns the confirmation password entered by the user- WARNING, PLAIN TEXT STILL!
     * @return String
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }
    /**
     * Sets the confirmation password entered by the user- WARNING, PLAIN TEXT STILL!
     * @param pConfirmPassword confirmation password
     */
    public void setConfirmPassword(String pConfirmPassword) {
        this.confirmPassword = pConfirmPassword;
    }
    /**
     * Compares the passwords given, fails if they differ.
     * @return boolean
     */
    public boolean comparePasswords() {
        return this.password.equals(confirmPassword);
    }
}
