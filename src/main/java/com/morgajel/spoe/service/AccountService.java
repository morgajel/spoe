package com.morgajel.spoe.service;

import com.morgajel.spoe.model.Account;

import java.util.List;

import com.morgajel.spoe.annotation.ValidPassword;
import com.morgajel.spoe.annotation.ValidUsername;
import com.morgajel.spoe.dao.AccountDao;
/**
 * Account Service for managing AccountDao.
 */
public interface AccountService {
    /**
     * Adds an account object to the datastore.
     * @param account account to add
     */
    void addAccount(Account account);
    /**
     * Returns the AccountDao currently used.
     * @return AccountDao
     */
    AccountDao getAccountDao();
    /**
     * Returns an account object matching the given username.
     * @param username username to search for
     * @return Account
     */
    Account loadByUsername(@ValidUsername String username);
    /**
     * Returns an account object if the Username and checksum match.
     * @param username username to search for
     * @param checksum checksum to match
     * @return Account
     */
    Account loadByUsernameAndChecksum(@ValidUsername String username, String checksum);
    /**
     * Returns an account object if the Username or email match. username is given precedence.
     * @param username username to search for
     * @param email email to search for
     * @return Account
     */
    Account loadByUsernameOrEmail(@ValidUsername String username, String email);
    /**
     * Saves details of an account to the datastore.
     * @param account account to save
     */
    void saveAccount(Account account);
    /**
     * List all accounts currently in the system.
     * TODO Has the potential to be large, may need to paginate results.
     * @return List<Account>
     */
    List<Account> listAccounts();
    /**
     * Returns whether or not the users credentials check out.
     * @param username username to search for
     * @param password password to match
     * @return Account
     */
    boolean login(@ValidUsername String username, @ValidPassword String password);
    /**
     * Sets the AccountDao for this object so we can access the datastore.
     * @param accountDao the account Dao that will be used.
     */
    void setAccountDao(AccountDao accountDao);
}
