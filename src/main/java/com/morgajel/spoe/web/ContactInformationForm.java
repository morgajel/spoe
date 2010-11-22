/**
 * 
 */
package com.morgajel.spoe.web;

import org.hibernate.validator.constraints.Email;

import com.morgajel.spoe.model.Account;

/**
 * @author jmorgan
 *
 */
public class ContactInformationForm {
    @Email
    private String email;
    @Email
    private String confirmEmail;
    private Account.IMProtocol primaryIM;
    private Account.IMProtocol secondaryIM;
    private String primaryIMName;
    private String secondaryIMName;
    /**
     * Loads info from an account.
     * @param account load an account.
     */
    public void loadFromAccount(Account account) {
        this.email = account.getEmail();
        this.confirmEmail = account.getEmail();
        this.primaryIM = account.getPrimaryIM();
        this.primaryIMName = account.getPrimaryIMName();
        this.secondaryIM = account.getSecondaryIM();
        this.secondaryIMName = account.getSecondaryIMName();
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String pEmail) {
        this.email = pEmail;
    }
    public String getConfirmEmail() {
        return confirmEmail;
    }
    public void setConfirmEmail(String pConfirmEmail) {
        this.confirmEmail = pConfirmEmail;
    }
    public Account.IMProtocol getPrimaryIM() {
        return primaryIM;
    }
    public void setPrimaryIM(Account.IMProtocol pPrimaryIM) {
        this.primaryIM = pPrimaryIM;
    }
    public Account.IMProtocol getSecondaryIM() {
        return secondaryIM;
    }
    public void setSecondaryIM(Account.IMProtocol pSecondaryIM) {
        this.secondaryIM = pSecondaryIM;
    }
    public String getPrimaryIMName() {
        return primaryIMName;
    }
    public void setPrimaryIMName(String pPrimaryIMName) {
        this.primaryIMName = pPrimaryIMName;
    }
    public String getSecondaryIMName() {
        return secondaryIMName;
    }
    public void setSecondaryIMName(String pSecondaryIMName) {
        this.secondaryIMName = pSecondaryIMName;
    }
    /**
     * Confirm passwords match.
     * @return boolean
     */
    public boolean confirmEmail() {
        return email.equals(confirmEmail);
    }
}
