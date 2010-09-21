package com.morgajel.spoe.web;

public class SetPasswordForm {

	public String checksum;
	public String username;
	public String password;
	public String confirmPassword;
	/** 
	 * Constructor for SetPasswordForm. Currently does very little and may not even be needed. 
	 */
	public SetPasswordForm(){
	}
	/** 
	 * returns the checksum provided by a hidden field in the form 
	 */
	public String getChecksum() {
		return checksum;
	}	
	/** 
	 * Sets the checksum; used to prove the user received the email. 
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	/** 
	 * gets the username provided by the password form 
	 */
	public String getUsername() {
		return username;
	}
	/** 
	 * sets the username provided by the password form 
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	/** 
	 * Compares the passwords given, fails if they differ. 
	 */
	public boolean comparePasswords(){
		if (this.password.equals(confirmPassword)){
			return true;
		}else{
			return false;
		}
	};
	
}
