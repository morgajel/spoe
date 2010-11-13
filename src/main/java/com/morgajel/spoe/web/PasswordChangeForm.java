package com.morgajel.spoe.web;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.morgajel.spoe.annotation.ValidPassword;


/**
 * This is used to capture and set the PasswordChangeForm fields.
 * It includes a list of all account parameters you'd like to possibly modify.
 */
public class PasswordChangeForm {



    @NotEmpty
    @ValidPassword
    private String currentPassword;
    @NotEmpty
    @ValidPassword
    private String newPassword;
    @NotEmpty
    @ValidPassword
    private String confirmPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }
    public void setCurrentPassword(@ValidPassword String pCurrentPassword) {
        this.currentPassword = pCurrentPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(@ValidPassword String pNewPassword) {
        this.newPassword = pNewPassword;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(@ValidPassword String pConfirmPassword) {
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
