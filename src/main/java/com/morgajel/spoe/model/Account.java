package com.morgajel.spoe.model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

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
    ),
    /**
     * Returns an account matching a given username or email.
     */
    @NamedQuery(
            name = "findAccountByUsernameOrEmail",
            query = "from Account acc where acc.username = :username or acc.email = :email"
            //FIXME  need to limit to one or the other somehow...
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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private Long accountId;
    @NotNull
    @Column(name = "username")
    private String username;
    @NotNull
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @NotNull
    @Column(name = "password")
    private String password;
    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
    @NotNull
    @Column(name = "firstname")
    private String firstname;
    @NotNull
    @Column(name = "lastname")
    private String lastname;
    @DateTimeFormat
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;
    @DateTimeFormat
    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private Set<Role> roles;

    @OneToMany (fetch = FetchType.EAGER)
    @JoinColumn (name = "account_id")
    private Set<Snippet> snippets = new HashSet();

    public Set<Snippet> getSnippets() {
        return snippets;
    }

    public void setSnippets(Set<Snippet> pSnippets) {
        this.snippets = pSnippets;
    }
    /**
     * Adds a snippet to the current set of Snippet. if snippets is null, instantiates a new
     * HashSet and adds the snippet to it.
     * @param snippet a snippet to add to the account.
     **/
    public void addSnippet(Snippet snippet) {
        snippets.add(snippet);
        LOGGER.info("added snippet to " + username + ", check it out:" + snippets);
    }

    public static final String ALGORITHM = "SHA1";
    public static final String PASSWDCHARSET = "!0123456789abcdefghijklmnopqrstuvwxyz";
    private static final long serialVersionUID = -6987219647522500285L;
    private static final transient Logger LOGGER = Logger.getLogger(Account.class);

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
            LOGGER.error("Couldn't find " + ALGORITHM + " to hash the password. ");
            hexStr.append(Account.generatePassword(MAXLENGTH));
            //This should never ever happen, but needs to be caught.
            //I'd rather have an unusable password than a blank password.
        }
        LOGGER.trace("Created hash " + hexStr.toString() + " from " + text);
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
        Integer enable = this.enabled ? 1 : 0;
        String checksum = hashText(username + password + enable);
        LOGGER.info("create checksum: " + username + " + " + password + " + " + enable + " = " + checksum);
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
        lastModifiedDate = new Date();
        creationDate = new Date();
        this.roles = new HashSet<Role>();
    }

    public Long getAccountId() {
        return accountId;
    }
    public Set<Role> getRoles() {
        return roles;
    }

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
        LOGGER.info("added roll to " + username + ", check it out:" + roles);
    }

    public void setAccountId(Long pAccountId) {
        this.accountId = pAccountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String pEmail) {
        this.email = pEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String uname) {
        this.username = uname;
    }

    public String getPassword() {
        return this.password;
    }

    private void setPassword(String pPassword) {
        this.password = pPassword;

    }
    /**
     * Hashes the given string before setting it.
     * @param pword plaintext password
     **/
    public void setHashedPassword(String pword) {
        LOGGER.trace("H setting password: " + pword);
        this.setPassword(Account.hashText(pword));
        LOGGER.trace("H password field is now: " + this.password);
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enable) {
        this.enabled = enable;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String fname) {
        this.firstname = fname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lname) {
        this.lastname = lname;
    }
    /**
     * Gets this.creationDate, the date when the account was created.
     * @return Date creationDate
     **/
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
    public static String generatePassword(int pLength) {
        //TODO Do I still need this?
        int length = 25;
        if (pLength > MINLENGTH && pLength < MAXLENGTH) {
            length = pLength;
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
