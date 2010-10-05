package com.morgajel.spoe.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;



import java.util.HashSet;
import java.util.Set;

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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * Named Queries for retrieving Roles.
 **/
@NamedQueries({
    /**
     * Returns a role identified by it's name.
     **/
    @NamedQuery(
            name = "findRoleByName",
            query = "from Role role where role.name = :name"
        )
})

/**
 * Determines what access a user has within the framework.
 **/
@Entity
@Table(name = "role")
public class Role implements Serializable {
    private Set<Account> accounts;
    /**
     * Returns all of the roles currently assigned to a user.
     * @return Set<Account>
     **/
    @ManyToMany
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    )

    /**
     * Returns all of the accounts currently assigned to a user.
     **/
    public Set<Account> getAccounts() {
        return this.accounts;
    }
    /**
     * Sets a list of accounts that use the role.
     * @param pAccounts accounts to associate with the role
     **/
    public void setAccounts(Set<Account> pAccounts) {
        this.accounts = pAccounts;
    }

    @NotNull
    private Long roleId;
    @NotNull
    private String name;
    private static final long serialVersionUID = -2683827831742215212L;
    private static transient Logger logger = Logger.getLogger(Role.class);

    /**
     * Primary constructor for Role, sets an empty Set to accounts.
     **/
    public Role() {
        this.accounts = new HashSet<Account>();

    }
    /**
     * Adds an account to a role.
     * @param account account to associate with the role
     */
    public void addAccount(Account account) {
        accounts.add(account);
        logger.info("added roll to " + account.getUsername() + ", check it out:" + accounts);
    }


    /**
     * Returns the roleId of the Role instance.
     * @return Long
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    public Long getRoleId() {
        return roleId;
    }
    /**
     * Sets the roleId of the Role instance.
     * @param pRoleId sets the roleId
     **/
    public void setRoleId(Long pRoleId) {
        this.roleId = pRoleId;
    }
    /**
     * Returns the name of the Role instance.
     * @return String
     **/
    @Column(name = "name")
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the Role instance.
     * @param pName Set the Name of the role
     **/
    public void setName(String pName) {
        this.name = pName;
    }
    /**
     * Overrides the toString with pertinent information.
     * @return String
     **/
    @Override
    public String toString() {
        //TODO may want to include userlist here.
        logger.debug("printing toString");
        return "Role "
                + "[ roleId=" + roleId
                + ", name=" + name
                +  "]";
    }
}
