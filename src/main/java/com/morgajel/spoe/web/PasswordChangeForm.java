package com.morgajel.spoe.web;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * This is used to capture and set the PasswordChangeForm fields.
 * It includes a list of all account parameters you'd like to possibly modify.
 */
public class PasswordChangeForm {



    @NotEmpty
    private String currentPassword;
    @NotEmpty
    @Min(value = 8)
    private String newPassword;
    @NotEmpty
    @Min(value = 8)
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
