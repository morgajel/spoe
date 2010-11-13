package com.morgajel.spoe.service;

import java.util.List;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.annotation.ValidUsername;
import com.morgajel.spoe.dao.AccountDao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * Default Account Service Implementation used for managing AccountDao.
 */
@Service("accountService")
@Transactional(propagation = Propagation.SUPPORTS)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;
    private static final transient Logger LOGGER = Logger.getLogger(AccountService.class);

    /**
     * Set accountDao object for the datastore.
     * @param pAccountDao account Dao ti set
     */
    @Override
    public void setAccountDao(AccountDao pAccountDao) {
        this.accountDao = pAccountDao;
    }
    /**
     * Gets accountDao object for the datastore.
     * @return AccountDao
     */
    @Override
    public AccountDao getAccountDao() {
        return this.accountDao;
    }
    /**
     * Adds an account object to the datastore.
     * NOTE: serves same purpose as save account currently.
     * @param pAccount account to add
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addAccount(Account pAccount) {
        //TODO move role addition to here.
        LOGGER.debug("adding account " + pAccount.getUsername());
        accountDao.saveAccount(pAccount);
        LOGGER.debug("added account " + pAccount.getUsername());
    }
    /**
     * Saves an account object to the datastore.
     * @param pAccount account to save
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveAccount(Account pAccount) {
        LOGGER.debug("saving account " + pAccount.getUsername());
        accountDao.saveAccount(pAccount);
        LOGGER.debug("saved account " + pAccount.getUsername());
    }
    /**
     * List all accounts found in the datastore.
     * @return List<Account>
     */
    @Override
    public List<Account> listAccounts() {
        return accountDao.listAccounts();
    }
    /**
     * Load account matching a given username. Will return null if none is found.
     * @param username username of the account to load
     * @return Account
     */
    @Override
    public Account loadByUsername(@ValidUsername String username) {
        return accountDao.loadByUsername(username);
    }
    /**
     * Load account matching a given username and checksum. Will return null if none is found.
     * @param username username to search for
     * @param checksum checksum to match against
     * @return Account
     */
    @Override
    public Account loadByUsernameAndChecksum(@ValidUsername String username, String checksum) {
        LOGGER.debug("attempting to load user by " + username + " and " + checksum);
        return accountDao.loadByUsernameAndChecksum(username, checksum);
    }
    /**
     * Load account matching a given username or email. Will return null if none is found.
     * @param username username to search for
     * @param email email to search for
     * @return Account
     */
    @Override
    public Account loadByUsernameOrEmail(@ValidUsername String username, String email) {
        LOGGER.debug("attempting to load user by " + username + " or " + email);
        return accountDao.loadByUsernameOrEmail(username, email);
    }
    /**
     * Load account matching a given username and password. Will return null if none is found.
     * @param username username to search for
     * @param password password to match against
     * @return Account
     */
    @Override
    public boolean login(@ValidUsername String username, String password) {
        boolean valid = false;
        Account results = accountDao.loadByUsernameAndPassword(username, password);
        if (results != null) {
            valid = true;
        }
        return valid;
    }
}
