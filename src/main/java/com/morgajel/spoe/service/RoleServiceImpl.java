package com.morgajel.spoe.service;

import java.util.List;

import com.morgajel.spoe.model.Role;
import com.morgajel.spoe.dao.RoleDao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default Role Service Implementation used for managing RoleDao.
 */
@Service("roleService")
@Transactional(propagation = Propagation.SUPPORTS)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;
    private static transient Logger logger = Logger.getLogger(RoleServiceImpl.class);

    /**
     * Set RoleDao for accessing the datasource.
     * @param pRoleDao the roleDao you wish to set
     */
    @Override
    public void setRoleDao(RoleDao pRoleDao) {
        this.roleDao = pRoleDao;
    }
    /**
     * Add a given Role to the datasource.
     * @param pRole the role you wish to add
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addRole(Role pRole) {
        roleDao.saveRole(pRole);
    }
    /**
     * Returns a list of services with no qualifications.
     * @return List<Role>
     */
    @Override
    public List<Role> listRoles() {
        return roleDao.listRoles();
    }
    /**
     * Load a Role from the datasource matching a given name. Will return null if none is found.
     * @param pName the name you wish to search by
     * @return Role
     */
    @Override
    public Role loadByName(String pName) {
        logger.info("Loading role " + pName);
        Role role = roleDao.loadByName(pName);
        return role;
    }
}
