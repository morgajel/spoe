package com.morgajel.spoe.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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

@NamedQueries({
	/**
	 * Returns a role identified by it's name
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
@Table(name="role")
public class Role implements Serializable {
	
	@ManyToMany
    @JoinTable(name="account_role",
	        joinColumns=@JoinColumn(name="role_id"),
	        inverseJoinColumns=@JoinColumn(name="account_id")
    )
	/**
	 * Returns all of the roles currently assigned to a user.
	 **/
    public Set<Account> getAccounts() {
    	return accounts; 
    }
	
    public Set<Account> accounts;
	@NotNull
	private Long roleId;
	@NotNull
	private String name;
	private static final long serialVersionUID = -2683827831742215212L;
	private transient static Logger logger = Logger.getLogger("com.morgajel.spoe.model.Role");

	/**
	 * Primary constructor for Role, sets an empty Set to accounts.  
	 **/
	public Role(){
		accounts=new HashSet<Account>();
	}

	public void addAccount(Account account) {
		accounts.add(account);
		logger.info("added roll to "+account.getUsername()+", check it out:"+accounts);
	}
	
    /**
     * Returns all of the accounts currently assigned to a user.
     **/
    public void setAccounts( Set<Account> accounts) {
		this.accounts=accounts;
	}

    /**
     * Returns the roleId of the Role instance.
     **/
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="role_id")
	public Long getRoleId() {
		return roleId;
	}
	/**
     * Sets the roleId of the Role instance.
     **/
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	/**
     * Returns the name of the Role instance.
     **/
    @Column(name="name")
	public String getName() {
		return name;
	}
    /**
     * Sets the name of the Role instance.
     **/
	public void setName(String name) {
		this.name = name;
	}
	/**
     * Overrides the toString with pertinent information.
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