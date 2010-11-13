package com.morgajel.spoe.dao;

import com.morgajel.spoe.annotation.ValidPassword;
import com.morgajel.spoe.annotation.ValidUsername;
import com.morgajel.spoe.model.Account;
import java.util.List;
/**
 * Hibernate object interface used for storing and loading Accounts.
 */
public interface AccountDao {

    /**
     * Save a provided account to the datasource.
     * @param account account to save
     */
    void saveAccount(Account account);

    /**
     * List all accounts in the datasource.
     * @return List<Account>
     */
    List<Account> listAccounts();

    /**
     * Return an account with a given username, or else null.
     * @param username username to load
     * @return Account
     */
    Account loadByUsername(@ValidUsername String username);

    /**
     * Return an account with a given username and password, or else null.
     * @param username username to return
     * @param password password used to verify account
     * @return Account
     */
    Account loadByUsernameAndPassword(@ValidUsername String username, String password);

    /**
     * Return an account with a given username and checksum, or else null.
     * @param username username of the account to load
     * @param checksum checkum of the account to load
     * @return Account
     */
    Account loadByUsernameAndChecksum(@ValidUsername String username, @ValidPassword String checksum);
    /**
     * Return an account with a given username or email, or else null.
     * @param username username of the account to load
     * @param email email of the account to load
     * @return Account
     */
    Account loadByUsernameOrEmail(@ValidUsername String username, String email);
}
