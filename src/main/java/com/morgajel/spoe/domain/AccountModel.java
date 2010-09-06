package com.morgajel.spoe.domain;

import java.io.Serializable;
import java.util.Date;

public class AccountModel implements Serializable{

	private static final long serialVersionUID = 742741595185040824L;
	
	private Integer accountId;
    private String email;
    private String username;
    private String password;
    private Boolean enabled;
    private String firstname;
    private String lastname;
    private Date lastAccessDate;
    private Date creationDate;
    
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		//TODO: Should this be settable? it should be auto-generated.
		// leaving it here for unit testing.
		this.accountId = accountId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
		return "AccountModel [accountId=" + accountId + ", creationDate="
				+ creationDate + ", email=" + email + ", enabled=" + enabled
				+ ", firstname=" + firstname + ", lastAccessDate="
				+ lastAccessDate + ", lastname=" + lastname + ", password="
				+ password + ", username=" + username + "]";
	}


    
    
}
