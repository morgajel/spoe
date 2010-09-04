
package com.morgajel.spoe.manager;

//import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morgajel.spoe.dao.support.GenericDao;
import com.morgajel.spoe.manager.support.GenericEntityServiceImpl;
import com.morgajel.spoe.domain.AccountModel;
import com.morgajel.spoe.dao.AccountDAO;

@Service("accountManager")
public class AccountManagerImpl extends GenericEntityServiceImpl<AccountModel, Integer> implements AccountManager  {

    @SuppressWarnings("unused")
    final private Log logger = LogFactory.getLog(AccountManagerImpl.class);

    private AccountDAO accountDAO;

    @Autowired
    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    
    public GenericDao<AccountModel> getDao() {
        return this.accountDAO;
    }

    public AccountModel getNew() {
        return new AccountModel();
    }


    //-------------------------------------------------------------
    //  Get and Delete methods (primary key or unique constraints)
    //-------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public AccountModel get(Integer accountId) {
        AccountModel account = new AccountModel();
        account.setAccountId(accountId);
        return get(account);
    }

    @Override
    public AccountModel get(AccountModel model) {
        if (model == null) {
            return null;
        }
    
        if (model.isPrimaryKeySet()) {
            return super.get(model);
        }

        if (model.isEmailSet()) {
            AccountModel result = getByEmail(model.getEmail());
            if (result != null) {
                return result;
            }
        }

        if (model.isUsernameSet()) {
            AccountModel result = getByUsername(model.getUsername());
            if (result != null) {
                return result;
            }
        }

        return null;    
    }

    /**
     * {@inheritDoc}
     */
    public AccountModel getByEmail(String _email) {
        AccountModel account = new AccountModel();
        account.setEmail(_email);
        return findUniqueOrNone(account);
    }

    /**
     * {@inheritDoc}
     */
    public void deleteByEmail(String email) {
        delete(getByEmail(email));
    }

    /**
     * {@inheritDoc}
     */
    public AccountModel getByUsername(String _username) {
        AccountModel account = new AccountModel();
        account.setUsername(_username);
        return findUniqueOrNone(account);
    }

    /**
     * {@inheritDoc}
     */
    public void deleteByUsername(String username) {
        delete(getByUsername(username));
    }
}
