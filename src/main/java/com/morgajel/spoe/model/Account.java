package com.morgajel.spoe.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

import com.morgajel.spoe.web.RegistrationForm;
/**
 * Named Queries for Account.
 */
@NamedQueries({
    /**
     * Returns an account matching a given username.
     */
    @NamedQuery(
        name = "findAccountByUsername",
        query = "from Account acc where acc.username = :username"
    ),
    /**
     * Returns an account matching a given email address.
     */
    @NamedQuery(
        name = "findAccountByEmail",
        query = "from Account acc where acc.email = :email"
    ),
    /**
     * Returns an account matching a given username and password.
     */
    @NamedQuery(
        name = "findAccountByUsernameAndPassword",
        query = "from Account acc where acc.username = :username and acc.password = sha1(:password)"
    ),
    /**
     * Returns an account matching a given username and checksum.
     * The checksum is calculated by concatenating the username password and whether or not the
     * account is enabled.
     */
    @NamedQuery(
            name = "findAccountByUsernameAndChecksum",
            query = "from Account acc where acc.username = :username and sha1(concat(acc.username,acc.password,acc.enabled)) = :checksum"
    )
})

/**
 * Models a users interaction with the rest of the system.
 */
@Entity
@Table(name = "account")
public class Account implements Serializable {
//FIXME: use pVariable format
    @NotNull
    private Long accountId;
    @NotNull
    @Size(min = 3, max = 25)
    private String username;
    @NotNull
    @Size(min = 5, max = 255)
    private String email;
    @NotNull
    @Size(min = 6, max = 255)
    private String password;
    @NotNull
    private Boolean enabled;
    @NotNull
    @Size(min = 2, max = 255)
    private String firstname;
    @NotNull
    @Size(min = 2, max = 255)
    private String lastname;
    @DateTimeFormat
    private Date lastModifiedDate;
    @DateTimeFormat
    private Date creationDate;

    public static final String ALGORITHM = "SHA1";
    public static final String PASSWDCHARSET = "!0123456789abcdefghijklmnopqrstuvwxyz";
    private static final long serialVersionUID = -6987219647522500285L;
    private static transient Logger logger = Logger.getLogger("com.morgajel.spoe.model.Account");

    /**
     * Takes a given string and hashes it with ALGORITHM.
     * Same as sha1() in mysql when using ALGORITHM=SHA1
     * @return String hashedText
     * @param text Text to hash
     **/
    public static String hashText(String text) {
        //TODO factor this out into a utility class
        StringBuilder hexStr = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.reset();
            byte[] buffer = text.getBytes();
            md.update(buffer);
            byte[] digest = md.digest();
            for (int i = 0; i < digest.length; i++) {
                hexStr.append(
                            Integer.toString(
                                    (digest[i] & 0xff) + 0x100, 16
                            ).substring(1));
            }
        } catch (NoSuchAlgorithmException ex) {
            logger.error("Couldn't find " + ALGORITHM + " to hash the password. ");
            hexStr.append(Account.generatePassword(MAXLENGTH));
            //This should never ever happen, but needs to be caught.
            //I'd rather have an unusable password than a blank password.
        }
        logger.trace("Created hash " + hexStr.toString() + " from " + text);
        return hexStr.toString();
    }

    /**
     * Create a checksum that can be used for activation purposes. Checksum consists
     * of username + password hash + enabled status hashed together.
     * @return String checksum
     **/
    public String activationChecksum() {
        //TODO can this be refactored with the namedQuery findAccountByUsernameAndChecksum?
        //converting enabled to int rather than string representation so NamedQuery using mysql int works.
        Integer enable = this.enabled?1:0;
        String checksum = hashText(username + password + enable);
        logger.info("create checksum: " + username + " + " + password + " + " + enable + " = " + checksum);
        return checksum;
    }

    /**
     * Takes a given string, hashes it, and compares it to the password; returns true if equal.
     * @return boolean verification
     * @param pPassword see if a given password matches the password field when hashed.
     **/
    public boolean verifyPassword(String pPassword) {
        //FIXME is this still needed?
        if (Account.hashText(pPassword).equals(this.password)) {
            return true;
        }
        return false;
    }

    /**
     * Constructor for Account class.
     * Disables and sets LastModifiedDate and CreationDate, creates a new Set of roles
     * and disables the account.
     **/
    public Account() {
        this.enabled = false;
        this.setLastModifiedDate(new Date());
        this.setCreationDate(new Date());
        this.roles = new HashSet<Role>();
    }
    /**
     * Returns this.accountId, which is the Primary Key for Accounts.
     * @return Long accountId
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    public Long getAccountId() {
        return accountId;
    }

    private Set<Role> roles;

    /**
     * Returns all of the roles currently assigned to a user.
     * @return Set<Role>
     **/
    @ManyToMany
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    public Set<Role> getRoles() {
        return roles;
    }
    /**
     * Sets the roles for a user.
     * @param pRoles A set of Roles to give to the account.
     *
     **/
    public void setRoles(Set<Role> pRoles) {
        this.roles = pRoles;
    }
    /**
     * Adds a role to the current set of Role. if roles is null, instantiates a new
     * HashSet and adds the role to it.
     * @param role a role to add to the account.
     **/
    public void addRole(Role role) {
        roles.add(role);
        logger.info("added roll to " + username + ", check it out:" + roles);
    }

    /**
     * Sets this.accountId, which is the Primary Key for Accounts.
     * @param pAccountId The ID for this account.
     **/
    public void setAccountId(Long pAccountId) {
        this.accountId = pAccountId;
    }
    /**
     * Returns this.email, which is required for password recovery and account creation.
     * @return email
     **/
    @Email
    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }
    /**
     * Sets this.email, which is required for password recovery and account creation.
     * @param pEmail Email to set for the account.
     **/
    public void setEmail(String pEmail) {
        this.email = pEmail;
    }
    /**
     * Returns this.username, which is required for login.
     * @return String
     **/
    @Column(name = "username")
    public String getUsername() {
        return username;
    }
    /**
     * Sets this.username, which is required for login.
     * @param uname username for account
     **/
    public void setUsername(String uname) {
        this.username = uname;
    }
    /**
     * Returns this.password, which is a hashed copy of the user's password.
     * @return String password
     **/
    @Column(name = "password")
    public String getPassword() {
        logger.debug("getting password:" + this.password);
        return this.password;
    }
    /**
     * Sets this.password directly without hashing.
     * @param passwordHash hash of user's password
     **/
    private void setPassword(String passwordHash) {
        logger.trace("setting password: " + passwordHash);
        this.password = passwordHash;
        logger.trace("password field is now: " + this.password);
    }
    /**
     * Hashes the given string before setting it.
     * @param pword plaintext password
     **/
    public void setHashedPassword(String pword) {
        logger.trace("H setting password: " + pword);
        this.setPassword(Account.hashText(pword));
        logger.trace("H password field is now: " + this.password);
    }
    /**
     * Gets this.enabled, which is required for login.
     * @return boolean enabled
     **/
    @Column(name = "enabled", nullable = false)
    public Boolean getEnabled() {
        return enabled;
    }
    /**
     * Sets this.enabled, which is required for login.
     * @param enable status of the account
     **/
    public void setEnabled(Boolean enable) {
        this.enabled = enable;
    }
    /**
     * Gets this.Firstname, which is rarely ever used except for addressing people.
     * @return String firstname
     **/
    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }
    /**
     * Sets this.Firstname, which is rarely ever used except for addressing people.
     * @param fname first name of user
     **/
    public void setFirstname(String fname) {
        this.firstname = fname;
    }
    /**
     * Gets this.Lastname, which is rarely ever used except for addressing people.
     * @return lastname
     **/
    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }
    /**
     * Sets this.Lastname, which is rarely ever used except for addressing people.
     * @param lname last name of account
     **/
    public void setLastname(String lname) {
        this.lastname = lname;
    }
    /**
     * Gets this.creationDate, the date when the account was created.
     * @return Date creationDate
     **/
    @Column(name = "creation_date")
    public Date getCreationDate() {
        return (Date) creationDate.clone();
    }

    /**
     * Sets this.creationDate, which should only be used once.
     * @param cDate date of creation of account
     **/
    public void setCreationDate(Date cDate) {
        //TODO need to enforce this as only happening once, perhaps final?
        this.creationDate = (Date) cDate.clone();
    }
    /**
     * Gets this.lastModifiedDate, which tells when the account was last modified.
     * @return Date lastModifiedDate
     **/
    @Column(name = "last_modified_date")
    public Date getLastModifiedDate() {
        return (Date) lastModifiedDate.clone();
    }
    /**
     * Sets this.lastModifiedDate, which tells when the account was last modified.
     * @param pLastModifiedDate date that code was last modified.
     **/
    public void setLastModifiedDate(Date pLastModifiedDate) {
        //TODO change this to timestamp in mysql and it will auto update
        //FIXME should be lastModifiedDate
        this.lastModifiedDate = (Date) pLastModifiedDate.clone();
    }
    /**
     * Import registration form to populate data.
     * @param registerForm registration form containing Account info
     */
    public void importRegistration(RegistrationForm registerForm) {
        firstname = registerForm.getFirstname();
        lastname = registerForm.getLastname();
        email = registerForm.getEmail();
        username = registerForm.getUsername();
    }
    /**
     * Overrides the default toString, which should tell you quite a bit about your account.
     * @return String
     **/
    @Override
    public String toString() {
        //FIXME: I need to update this. perhaps include roles
        return "Account "
                + "[ accountId=" + accountId
                + ", username=" + username
                + ", email=" + email
                + ", password="    + password
                + ", enabled=" + enabled
                + ", firstname=" + firstname
                + ", lastname=" + lastname
                + ", lastModifiedDate=" +    lastModifiedDate
                + ", creationDate=" + creationDate
                + ", PASSWDCHARSET=" + PASSWDCHARSET
                + ", ALGORITHM=" + ALGORITHM
                +  "]";
    }
    public static final int MAXLENGTH = 25;
    public static final int MINLENGTH = 6;
    /**
     * Generates a temporary password length characters long using PASSWDCHARSET.
     * @param length length of the password
     * @return String
     **/
    public static String generatePassword(int length) {
        //TODO Do I still need this?
        if (length < MINLENGTH || length > MAXLENGTH) {
            length = MAXLENGTH;
        }
        Random rand = new Random(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int pos = rand.nextInt(PASSWDCHARSET.length());
            sb.append(PASSWDCHARSET.charAt(pos));
        }
        return sb.toString();
    }
}
