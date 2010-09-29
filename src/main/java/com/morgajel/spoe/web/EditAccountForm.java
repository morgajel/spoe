package com.morgajel.spoe.web;

import com.morgajel.spoe.model.Account;

/**
 * This is used to capture and set the EditAccountForm fields.
 * It includes a list of all account parameters you'd like to possibly modify.
 */
public class EditAccountForm {

	//
	public String firstname;
	public String lastname;

	public String email;
	public String confirmEmail;
	public String oldPassword;
	public String password;
	public String confirmPassword;
	public final String passwordHolder="Will not be changed.";
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * Returns the email entered by the user
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Sets the email entered by the user.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Returns the confirmation email entered by the user.
	 */
	public String getConfirmEmail() {
		return confirmEmail;
	}
	/**
	 * Sets the confirmation email entered by the user.
	 */
	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}
	/**
	 * Returns the oldPassword entered by the user- WARNING, PLAIN TEXT STILL!
	 */
	public String getOldPassword() {
		return oldPassword;
	}
	/**
	 * Sets the oldPassword entered by the user- WARNING, PLAIN TEXT STILL!
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	/**
	 * Returns the password entered by the user- WARNING, PLAIN TEXT STILL!
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets the password entered by the user- WARNING, PLAIN TEXT STILL!
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Returns the confirmation password entered by the user- WARNING, PLAIN TEXT STILL!
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	/**
	 * Sets the confirmation password entered by the user- WARNING, PLAIN TEXT STILL!
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	/**
	 * Compares the new passwords given, fails if they differ.
	 */
	public boolean compareNewPasswords(){
		if (this.password.equals(confirmPassword)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Compares the emails given, fails if they differ.
	 */
	public boolean compareEmails(){
		if (this.email.equals(confirmEmail)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * This is used to display basic account information in the editAccountForm field.
	 */
	public void loadAccount(Account account){
		email=account.getEmail();
		confirmEmail=account.getEmail();
		oldPassword="";
		password=passwordHolder;
		confirmPassword=passwordHolder;
	}
}