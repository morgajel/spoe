package com.morgajel.spoe.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Random;
//import org.hibernate.validator.constraints.impl.EmailValidator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.Email;
import javax.validation.Valid;

@NamedQueries({
	@NamedQuery(
		name = "findAccountByUsername",
		query = "from Account acc where acc.username = :username"
	),
	@NamedQuery(
		name = "findAccountByEmail",
		query = "from Account acc where acc.email = :email"
	),
	@NamedQuery(
		name = "findAccountByUsernameAndPassword",
		query = "from Account acc where acc.username = :username and acc.password = sha1(:password)"
	),
	@NamedQuery(
			name = "findAccountByUsernameAndChecksum",
			query = "from Account acc where acc.username = :username and sha1(concat(acc.username,acc.password,acc.enabled)) = :checksum" 
	)
})


@Entity
@Table(name="account")
public class Account implements Serializable {
    @ManyToMany
    @JoinTable(name="account_role",
            joinColumns=
                @JoinColumn(name="account_id", referencedColumnName="account_id"),
            inverseJoinColumns=
                @JoinColumn(name="role_id", referencedColumnName="role_id")
)
    public Set<Role> getRoles() { return roles; }
	public void setRoles( Set<Role> roles) {
		this.roles=roles;
	}
	public void addRole(Role role) {
		
		logger.info("trying to add "+role+"to roles "+roles);
		if (this.roles==null){
			this.roles=new HashSet<Role>();
		}
		this.roles.add(role);
		logger.info("added roll to "+username+", check it out:"+roles);
	}
	public Set<Role> roles;
	public static final String ALGORITHM = "SHA1";
	public static final String PASSWDCHARSET = "!0123456789abcdefghijklmnopqrstuvwxyz";
	private static final long serialVersionUID = -6987219647522500285L;
	private transient static Logger logger = Logger.getLogger("com.morgajel.spoe.model.Account");
    
	public static String hashText(String text) {
		//TODO factor this out into a utility class
		StringBuilder hexStr= new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			md.reset();
			byte[] buffer = text.getBytes();
			md.update(buffer);
			byte[] digest = md.digest();
			for (int i = 0; i < digest.length; i++) {
				hexStr.append( Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 ));
			}
		}catch(NoSuchAlgorithmException ex){
			logger.error("couldn't find "+ALGORITHM+" to hash the password. ");
			ex.printStackTrace();
			//Not really sure what to return here.
		}
		logger.trace("Created hash "+hexStr.toString()+" from "+text);
		return hexStr.toString();
	}
	public boolean verifyPassword(String password){
		if (Account.hashText(password).equals(this.password)){
			return true;
		}
		return false;
	}

	@NotNull
	private Long accountId;

	@NotNull
	@Size(min = 1, max = 25)
	private String username;

	@NotNull
	@Size(min = 5, max = 255)
	private String email;

	@NotNull
	@Size(min=6, max=255)
	private String password;
	
	@NotNull
	private Boolean enabled;
	
	@NotNull
	@Size(min=5, max=255)
	private String firstname;
	@NotNull
	@Size(min=5, max=255)
	private String lastname;
	@DateTimeFormat
	private Date lastAccessDate;
	@DateTimeFormat
	private Date creationDate;

    public Account() {
    	this.enabled=false;
	    this.setLastAccessDate(new Date());
	    this.setCreationDate(new Date());
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="account_id")
	public Long getAccountId() {
		return accountId;
	}
	
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

    @Email
    @Column(name="email", length=255, nullable=false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
    @Column(name="username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    @Column(name="password")
	public String getPassword() {
    	//FIXME: should I ever be able to "get" the password?
    	logger.debug("getting password:"+this.password);
		return this.password;
	}

	private void setPassword(String password) {
		logger.debug("setting password: "+password);
		this.password = password;
		logger.debug("password field is now: "+this.password);
	}
	public void setHashedPassword(String password) {
		logger.debug("H setting password: "+password);
		setPassword(Account.hashText(password));
		logger.debug("H password field is now: "+this.password);
	}

    @Column(name="enabled", nullable=false )
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
    @Column(name="firstname")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
    @Column(name="lastname")
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
    @Column(name="creation_date")
	public Date getCreationDate() {
		if (creationDate == null) {
			return null;
		}
		return (Date) creationDate.clone();
	}

    
	public void setCreationDate(Date creationDate) {
		this.creationDate = (Date) creationDate.clone();
	}
    @Column(name="last_access_date")
	public Date getLastAccessDate() {
		if (lastAccessDate == null) {
			return null;
		}
		return (Date) lastAccessDate.clone();
	}

	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = (Date) lastAccessDate.clone();
	}

	@Override
	public String toString() {
		//FIXME: I need to update this.
		return "Account "
				+ "[ accountId=" + accountId 
				+ ", username=" + username
				+ ", email=" + email
				+ ", password="	+ password
				+ ", enabled=" + enabled
				+ ", firstname=" + firstname
				+ ", lastname=" + lastname
				+ ", lastAccessDate=" +	lastAccessDate
				+ ", creationDate=" + creationDate
				+ ", PASSWDCHARSET=" + PASSWDCHARSET
				+ ", ALGORITHM=" + ALGORITHM
				+  "]";
	}
	
	public static String generatePassword(int length) {
        Random rand = new Random(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int pos = rand.nextInt(PASSWDCHARSET.length());
            sb.append(PASSWDCHARSET.charAt(pos));
        }
        return sb.toString();
	}

}
