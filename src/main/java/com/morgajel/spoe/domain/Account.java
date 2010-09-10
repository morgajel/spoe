package com.morgajel.spoe.domain;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;
//import org.hibernate.validator.constraints.impl.EmailValidator;


public class Account {

    private Logger  logger = Logger.getLogger("com.morgajel.spoe.domain.Account");
	private Long accountId;

    @NotNull
    @Size(min=1, max=25)
    private String username;

    // TODO test this
	private static final AtomicLong idSequence = new AtomicLong();

    @NotNull
    @Size(min=5, max=255)
    private String email;
    @NotNull
    @Size(min=5, max=255)
    private String confirmemail;
//    @NotNull
//    @Size(min=6, max=255)
    private String password;
//    @NotNull
//    @Size(min=6, max=255)
    private String confirmpassword;
    private Boolean enabled;
//    @NotNull
//    @Size(min=5, max=255)
    private String firstname;
//    @NotNull
//    @Size(min=5, max=255)
    private String lastname;
//    @DateTimeFormat
    private Date lastAccessDate;
//    @DateTimeFormat
    private Date creationDate;

    public Long assignId() {
        this.accountId = idSequence.incrementAndGet();
        return this.accountId;
    }

    

	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		//TODO: Should this be settable? it should be auto-generated.
		// leaving it here for unit testing.
		this.accountId = accountId;
	}
	public boolean compareEmail(){
		if (logger.isDebugEnabled()){
			logger.debug(" check to see if "+ this.confirmemail+" is equal to "+ this.email);
		}
		if (this.confirmemail.equals(this.email) ){
			return true;
		}
		return false;
		
	} 

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getConfirmEmail() {
		return confirmemail;
	}
	public void setConfirmEmail(String confirmemail) {
		this.confirmemail = confirmemail;
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
		return confirmpassword;
	}
	public void setConfirmPassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
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
	public Date getCreationDate() {
		if (creationDate == null ){
			return null;
		}
		return (Date) creationDate.clone();
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate=(Date) creationDate.clone();
	}	
	public Date getLastAccessDate() {
		if (lastAccessDate == null ){
			return null;
		}
		return (Date) lastAccessDate.clone();
	}
	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate= (Date) lastAccessDate.clone();
	}
	
	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", creationDate="
				+ creationDate + ", email=" + email + ", enabled=" + enabled
				+ ", firstname=" + firstname + ", lastAccessDate="
				+ lastAccessDate + ", lastname=" + lastname + ", password="
				+ password + ", username=" + username + "]";
	}
    
}
