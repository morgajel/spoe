
package com.morgajel.spoe.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.morgajel.spoe.dao.support.SearchTemplate;
import com.morgajel.spoe.domain.AccountModel;
import com.morgajel.spoe.manager.AccountManager;



@Service
public class AccountDetailsServiceImpl implements UserDetailsService {

    static final private Log logger = LogFactory.getLog(AccountDetailsServiceImpl.class);

    private AccountManager accountManager;

	@Autowired
    public AccountDetailsServiceImpl(AccountManager accountManager) {
        this.accountManager = accountManager;
    }
    

    /**
     * Retrieve an account depending on its login this method is not case sensitive.<br>
     * use <code>obtainAccount</code> to match the login to either email, login or whatever is your login logic
     *
     * @param login the account login
     * @return a Spring Security userdetails object that matches the login
     * @see #obtainAccount(String)
     * @throws UsernameNotFoundException when the user could not be found
     * @throws DataAccessException when an error occurred while retrieving the account
     */
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {
        if (login == null || login.trim().length() == 0) {
            throw new UsernameNotFoundException("Empty login");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Security verification for user '" + login + "'");
        }

        AccountModel account = obtainAccount(login);

        if (account == null) {
            if (logger.isInfoEnabled()) {
                logger.info("Account " + login + " could not be found");
            }
            throw new UsernameNotFoundException("account " + login + " could not be found");
        }

        GrantedAuthority[] grantedAuthorities = obtainGrantedAuthorities(login);

        if (grantedAuthorities == null) {
            grantedAuthorities = SpringSecurityContext.convertToGrantedAuthorityArray(account.getRoleNames());            
        }

        String password = obtainPassword(login);

        if (password == null) {
            password = account.getPassword();
        }

        boolean enabled = account.getEnabled(); 
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true; 
        boolean accountNonLocked = true;

        return new org.springframework.security.core.userdetails.User(login, password, enabled, accountNonExpired, 
                credentialsNonExpired, accountNonLocked, grantedAuthorities);
    }

    /**
     * Return the account depending on the login provided by spring security.
     * @return the account if found
     */
    protected AccountModel obtainAccount(String login) {
        AccountModel account = new AccountModel();
        account.setEmail(login);

        SearchTemplate searchTemplate = new SearchTemplate();
        searchTemplate.setCaseSensitive(false);
        
        return accountManager.findUniqueOrNone(account, searchTemplate);
    }

    /**
     * Returns null. Subclass may override it to provide their own granted authorities.
     */
    protected GrantedAuthority[] obtainGrantedAuthorities(String username) {
        return null;
    }

    /**
     * Returns null. Subclass may override it to provide their own password.
     */
    protected String obtainPassword(String username) {
        return null;
    }

}
