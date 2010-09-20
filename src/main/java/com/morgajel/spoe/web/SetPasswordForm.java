package com.morgajel.spoe.web;

public class SetPasswordForm {

	public String checksum;
	public String username;
	public String password;
	public String confirmPassword;
	public SetPasswordForm(){
	}
	public String getChecksum() {
		return checksum;
	}
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	public String getUsername() {
		return username;
	}
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
	public boolean comparePasswords(){
		if (this.password.equals(confirmPassword)){
			return true;
		}else{
			return false;
		}
	};
	
}
