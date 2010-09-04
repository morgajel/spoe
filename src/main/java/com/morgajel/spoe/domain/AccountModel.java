
package com.morgajel.spoe.domain;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.morgajel.spoe.util.*;

/**
 * Map the columns of the account table.
 * <ul>
 * </ul>
 */
@Entity
@Table(name = "account")
@NamedQuery(name="AccountModel.selectAll", query="from com.morgajel.spoe.domain.AccountModel as account where 1 = 1", hints = { @QueryHint(name = "org.hibernate.comment", value = "enableDynamicOrderBySupport"), @QueryHint(name = "org.hibernate.cacheable", value = "true") })
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@FilterDef(name="myAccountFilter", defaultCondition = "account_id = :currentAccountId", parameters=@ParamDef( name="currentAccountId", type="org.hibernate.type.IntegerType" ) )
@Filter(name="myAccountFilter")
public  class AccountModel implements Comparable<AccountModel>, Serializable {
    public static final String DATE_FORMAT_KEY = "dateformat_default";
    static final private long serialVersionUID = 1L;
    transient static final private Log logger = LogFactory.getLog(AccountModel.class);

    // Raw Properties
    private Integer accountId;
    private String email;
    private String username;
    private String password;
    private Boolean enabled;
    private String firstname;
    private String lastname;
    private Date lastAccessDate;
    private Date creationDate;
    private Integer roleId;


    //-------------------------------
    // Primary Key Helper
    //-------------------------------    
    /**
     * Helper method to know whether the primary key is set or not,
     * @return true if the primary key is set, false otherwise
     */
    @Transient
    public boolean isPrimaryKeySet() {
        return isAccountIdSet();
    }

    /**
     * Helper method that returns the primary key.
     */
    @Transient
    public Integer getPrimaryKey() {
        return getAccountId();
    }

    /**
     * Default implementation returns hard coded granted authorities for this account (i.e. "ROLE_USER" and "ROLE_ADMIN").
     * TODO: You should override this method to provide your own custom authorities using your own logic.
     * Or you can follow Celerio Account Table convention. Please refer to Celerio Documentation.
     */
    @Transient
    public List<String> getRoleNames() {
        List<String> roleNames = new ArrayList<String>();
        roleNames.add("ROLE_ANONYMOUS");

        if ("user".equals(email)) {
            roleNames.add("ROLE_USER");
        } else if ("admin".equals(email)) {
            roleNames.add("ROLE_USER");
            roleNames.add("ROLE_ADMIN");
        }

        logger.warn("Returning hard coded role names. TODO: get the real role names");
        return roleNames;
    }

    //-----------------------------------------
    // Raw Getters & Setters
    //-----------------------------------------

    @Id
    @GeneratedValue
    @Column(name = "account_id", nullable = false, precision = 10)
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Transient
    public boolean isAccountIdSet() {
        return getAccountId() != null;
    }


    @NotEmpty
    @Length(max = 255)
    @Email
    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Transient
    public boolean isEmailSet() {
        return StringUtil.hasLength(getEmail());
    }


    @NotEmpty
    @Length(max = 32)
    @Column(nullable = false, unique = true, length = 32)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Transient
    public boolean isUsernameSet() {
        return StringUtil.hasLength(getUsername());
    }


    @NotEmpty
    @Length(max = 32)
    @Column(nullable = false, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public boolean isPasswordSet() {
        return StringUtil.hasLength(getPassword());
    }


    @Column(length = 0)
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Transient
    public boolean isEnabledSet() {
        return getEnabled() != null;
    }


    @Length(max = 255)
    @Column
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Transient
    public boolean isFirstnameSet() {
        return StringUtil.hasLength(getFirstname());
    }


    @Length(max = 255)
    @Column
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Transient
    public boolean isLastnameSet() {
        return StringUtil.hasLength(getLastname());
    }


    @Column(name = "last_access_date", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastAccessDate() {
        return lastAccessDate != null ? (Date) lastAccessDate.clone() : null;
    }

    public void setLastAccessDate(Date lastAccessDate) {
        this.lastAccessDate = lastAccessDate != null ? (Date) lastAccessDate.clone() : null;
    }

    @Transient
    public boolean isLastAccessDateSet() {
        return getLastAccessDate() != null;
    }

    @NotNull
    @Column(name = "creation_date", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate != null ? (Date) creationDate.clone() : null;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate != null ? (Date) creationDate.clone() : null;
    }

    @Transient
    public boolean isCreationDateSet() {
        return getCreationDate() != null;
    }


    @NotNull
    @Column(name = "role_id", nullable = false, precision = 10)
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Transient
    public boolean isRoleIdSet() {
        return getRoleId() != null;
    }




    //-----------------------------------------
    // equals and hashCode
    //-----------------------------------------

    // The first time equals or hashCode is called, 
    // we check if the primary key is present or not.
    // If yes: we use it in equals/hashCode
    // If no: we use a VMID during the entire life of this
    // instance even if later on this instance is assigned 
    // a primary key.    

    @Override
    public boolean equals(Object account) {
        if (this == account) {
            return true;
        }

        if (!(account instanceof AccountModel)) {
            return false;
        }

        AccountModel other = (AccountModel) account;
        return _getUid().equals(other._getUid());
    }

    @Override
    public int hashCode() {
        return _getUid().hashCode();
    }

    private Object _uid; 
    private Object _getUid() {
        if (_uid == null) {
            if (isPrimaryKeySet()) {
                _uid = getPrimaryKey();
            } else {
                _uid = new java.rmi.dgc.VMID();
                logger.warn("DEVELOPER: if you encounter this message you should take the time to carefuly review equals/hashCode for: " + getClass().getCanonicalName());                
            }
        }
        return _uid;
    }
    
    /**
     * Construct a readable string representation for this AccountModel instance.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        if (isAccountIdSet()) {
            ret.append("account.account_id=[").append(getAccountId()).append("]\n");    
        }
        if (isEmailSet()) {
            ret.append("account.email=[").append(getEmail()).append("]\n");    
        }
        if (isUsernameSet()) {
            ret.append("account.username=[").append(getUsername()).append("]\n");    
        }
        if (isPasswordSet()) {
            ret.append("account.password=[").append(getPassword()).append("]\n");    
        }
        if (isEnabledSet()) {
            ret.append("account.enabled=[").append(getEnabled()).append("]\n");    
        }
        if (isFirstnameSet()) {
            ret.append("account.firstname=[").append(getFirstname()).append("]\n");    
        }
        if (isLastnameSet()) {
            ret.append("account.lastname=[").append(getLastname()).append("]\n");    
        }
        if (isLastAccessDateSet()) {
            ret.append("account.last_access_date=[").append(getLastAccessDate()).append("]\n");
        }
        if (isCreationDateSet()) {
            ret.append("account.creation_date=[").append(getCreationDate()).append("]\n");
        }
        if (isRoleIdSet()) {
            ret.append("account.role_id=[").append(getRoleId()).append("]\n");    
        }
        return ret.toString();
    }

    /**
     * A simple unique one-line String representation of this AccountModel instance.
     * Can be used in a select html form tag.
     */
    @Transient
    public String getDisplayString() {
        if (isEmailSet()) {
            return getEmail();
        }
        if (isUsernameSet()) {
            return getUsername();
        }

        return null;
    }

    /**
     * Compare this instance to the passed instance
     */
    public int compareTo(AccountModel other) {
        if (other == null)  {
            throw new RuntimeException("Tried to compare to a null object");
        }
        
        if (this == other) {
            return 0;
        }

        int compareTo = hashCode() - other.hashCode();
        if (compareTo > 0) {
            return 1;
        } else if (compareTo < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    public void initDefaultValues() {
        setEnabled(true);
        setRoleId(2);
    }
}
