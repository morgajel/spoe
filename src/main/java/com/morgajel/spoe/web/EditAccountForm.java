package com.morgajel.spoe.web;

import com.morgajel.spoe.model.Account;

/**
 * This is used to capture and set the EditAccountForm fields.
 * It includes a list of all account parameters you'd like to possibly modify.
 */
public class EditAccountForm {

    //
    private String firstname;
    private String lastname;

    private String email;
    private String confirmEmail;
    private String oldPassword;
    private String password;
    private String confirmPassword;
    private static final String PASSWORDHOLDER = "Will not be changed.";
    /**
     * returns the password holder, which if found, signals not to change the password.
     * @return String
     */
    public static String getPasswordHolder() {
        return PASSWORDHOLDER;
    }
    /**
     * Returns the first name entered in the Account form. Defaults to users current firstname
     * @return String
     */
    public String getFirstname() {
        return firstname;
    }
    /**
     * Sets the first name of the user
     * TODO write some validation in here.
     * @param pFirstname first name of the user
     */
    public void setFirstname(String pFirstname) {
        this.firstname = pFirstname;
    }
    /**
     * Return the last name set in the Account Form.
     * @return String
     */
    public String getLastname() {
        return lastname;
    }
    /**
     * Sets the last name in the Account Form.
     * @param pLastname the Last name of the user
     */
    public void setLastname(String pLastname) {
        this.lastname = pLastname;
    }
    /**
     * Returns the email entered by the user.
     * @return String
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the email entered by the user.
     * @param pEmail email address of the user
     */
    public void setEmail(String pEmail) {
        this.email = pEmail;
    }
    /**
     * Returns the confirmation email entered by the user.
     * @return String
     */
    public String getConfirmEmail() {
        return confirmEmail;
    }
    /**
     * Sets the confirmation email entered by the user.
     * @param pConfirmEmail Confirmation Email Address
     */
    public void setConfirmEmail(String pConfirmEmail) {
        this.confirmEmail = pConfirmEmail;
    }
    /**
     * Returns the oldPassword entered by the user- WARNING, PLAIN TEXT STILL!
     * @return String
     */
    public String getOldPassword() {
        return oldPassword;
    }
    /**
     * Sets the oldPassword entered by the user- WARNING, PLAIN TEXT STILL!
     * @param pOldPassword the original password provided by the user.
     */
    public void setOldPassword(String pOldPassword) {
        this.oldPassword = pOldPassword;
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
     * @param pPassword plain text password
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
     * @param pConfirmPassword plain text confirmation password
     */
    public void setConfirmPassword(String pConfirmPassword) {
        this.confirmPassword = pConfirmPassword;
    }
    /**
     * Compares the new passwords given, fails if they differ.
     * @return boolean
     */
    public boolean compareNewPasswords() {
        return this.password.equals(confirmPassword);
    }
    /**
     * Compares the emails given, fails if they differ.
     * @return boolean
     */
    public boolean compareEmails() {
        return this.email.equals(confirmEmail);
    }
    /**
     * This is used to display basic account information in the editAccountForm field.
     * @param account Account you wish to load
     */
    public void loadAccount(Account account) {
        email = account.getEmail();
        confirmEmail = account.getEmail();
        oldPassword = "";
        password = PASSWORDHOLDER;
        confirmPassword = PASSWORDHOLDER;
    }
}
