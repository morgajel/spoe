package com.morgajel.spoe.dao;
import org.apache.log4j.Logger;
import java.util.Date;
import java.util.List;

import com.morgajel.spoe.annotation.ValidPassword;
import com.morgajel.spoe.annotation.ValidUsername;
import com.morgajel.spoe.model.Account;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the AccountDao interface, used to save and load accounts from the datasource.
 */
@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private SessionFactory sessionFactory;
    private static final transient Logger LOGGER = Logger.getLogger(AccountDao.class);

    /**
     * Sets the Session Factory used to get the currentSession.
     * @param pSessionFactory factory to set
     */
    public void setSessionFactory(SessionFactory pSessionFactory) {
        this.sessionFactory = pSessionFactory;

    }
    /**
     * returns a query based on a querystring and username, since that appears to be the
     * common denominator among load methods.
     * @param queryString name of Named Query to call
     * @param username username of user to search for
     * @return Query query
     */
    private Query loadQueryByUsername(String queryString, @ValidUsername String username) {
        Session session = sessionFactory.getCurrentSession();
        return session.getNamedQuery(queryString).setString("username", username);
    }
    /**
     * Saves a given account to the datasource.
     * @param account account to save
     */
    @Override
    public void saveAccount(Account account) {
        LOGGER.info("setting last modified date before saving for " + account.getUsername());
        account.setLastModifiedDate(new Date());
        sessionFactory.getCurrentSession().saveOrUpdate(account);
        LOGGER.info("account appears saved");
    }
    /**
     * Returns a List of all accounts from the datasource with no qualifications.
     * @return List<Account>
     */
    @Override
    public List<Account> listAccounts() {
        LOGGER.debug("attempting to list accounts");
        return (List<Account>) sessionFactory.getCurrentSession().createCriteria(Account.class).list();
    }

    /**
     * Returns an account matching a given username. will return null if none is found.
     * @param username username of account to load
     * @return Account
     */
    @Override
    public Account loadByUsername(@ValidUsername String username) {
        LOGGER.debug("attempting to load user by " + username + " and password");
        List<Account> acclist = loadQueryByUsername("findAccountByUsername", username).list();
        if (acclist.size() > 0) {
            LOGGER.info("Loaded account " + acclist.get(0));
            return (Account) acclist.get(0);
        } else {
            return null;
        }
    }
    /**
     * returns an account matching a given username and password. Will return null if none is found.
     * @param username username of account to load
     * @param password ensure account matches this password
     * @return Account
     */
    @Override
    public Account loadByUsernameAndPassword(@ValidUsername String username, @ValidPassword String password) {
        LOGGER.debug("attempting to load user by " + username + " and password");
        List acclist = loadQueryByUsername("findAccountByUsernameAndPassword", username).setString("password", password).list();
        if (acclist.size() > 0) {
            LOGGER.info("Loaded account " + acclist.get(0));
            return (Account) acclist.get(0);
        } else {
            return null;
        }
    }
    /**
     * returns an account matching a given username and checksum. Will return null if none is found.
     *  @param username username of account to load
     *  @param checksum check the checksum if there is no password.
     *  @return Account
     */
    @Override
    public Account loadByUsernameAndChecksum(@ValidUsername String username, String checksum) {
        //This is a lot of text to return a simple account
        LOGGER.debug("attempting to load user by " + username + " and " + checksum);
        List<Account> acclist = loadQueryByUsername("findAccountByUsernameAndChecksum", username).setString("checksum", checksum).list();
        if (acclist.size() > 0) {
            LOGGER.info("Loaded account " + acclist.get(0));
            return (Account) acclist.get(0);
        } else {
            return null;
        }
    }
    /**
     * returns an account matching a given username or email. Will return null if none is found.
     *  @param username username of account to load
     *  @param email email of account to load
     *  @return Account
     */
    @Override
    public Account loadByUsernameOrEmail(@ValidUsername String username, String email) {
        //This is a lot of text to return a simple account
        LOGGER.debug("attempting to load user by " + username + " or " + email);
        List<Account> acclist = loadQueryByUsername("findAccountByUsernameOrEmail", username).setString("email", email).list();
        if (acclist.size() > 0) {
            LOGGER.info("Loaded account " + acclist.get(0));
            return (Account) acclist.get(0);
        } else {
            return null;
        }
    }
}
