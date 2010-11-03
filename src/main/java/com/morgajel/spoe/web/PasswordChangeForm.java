package com.morgajel.spoe.web;


/**
 * This is used to capture and set the PasswordChangeForm fields.
 * It includes a list of all account parameters you'd like to possibly modify.
 */
public class PasswordChangeForm {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }
    public void setCurrentPassword(String pCurrentPassword) {
        this.currentPassword = pCurrentPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String pNewPassword) {
        this.newPassword = pNewPassword;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String pConfirmPassword) {
        this.confirmPassword = pConfirmPassword;
    }

    /**
     * Compares the new passwords given, fails if they differ.
     * @return boolean
     */
    public boolean compareNewPasswords() {
        return this.newPassword.equals(confirmPassword);
    }
}
