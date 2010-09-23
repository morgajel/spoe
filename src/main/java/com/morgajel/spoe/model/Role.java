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
	@NamedQuery(
			name = "findRoleByName",
			query = "from Role role where role.name = :name"
		)
})//username+passfield+account.getEnabled())

@Entity
@Table(name="role")
public class Role implements Serializable {
	
	@ManyToMany
    @JoinTable(name="account_role",
    		joinColumns=
    			@JoinColumn(name="role_id", referencedColumnName="role_id"),
            inverseJoinColumns=
                @JoinColumn(name="account_id", referencedColumnName="account_id")
    )

	/**
	 * returns all of the roles currently assigned to a user.
	 **/
    public Set<Account> getAccounts() {

    	return accounts; 
    }
	public void addAccount(Account account) {
		accounts.add(account);
		logger.info("added roll to "+account.getUsername()+", check it out:"+accounts);
	}
	
    /**
     * returns all of the accounts currently assigned to a user.
     **/
    public void setAccounts( Set<Account> accounts) {
		this.accounts=accounts;
	}
	

	public Set<Account> accounts;
	private static final long serialVersionUID = -2683827831742215212L;
	private transient static Logger logger = Logger.getLogger("com.morgajel.spoe.model.Role");
	public Role(){
		accounts=new HashSet<Account>();
	}

	@NotNull
	private Long roleId;

	@NotNull
	@Size(min = 1, max = 25)
	private String name;
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="role_id")
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

    @Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		logger.debug("printing toString");
		return "Role "
				+ "[ roleId=" + roleId 
				+ ", name=" + name
				+  "]";
	}
	
}








