package com.morgajel.spoe.service;

import com.morgajel.spoe.model.Role;

import java.util.List;
import com.morgajel.spoe.dao.RoleDao;
/**
 * Role Service for managing RoleDao.
 */
public interface RoleService {
    /**
     * Adds a Role object to the datastore.
     * @param role role to add
     */
     void addRole(Role role);
    /**
     * List all roles currently in the system.
     * @return List<Role>
     */
    List<Role> listRoles();
    /**
     * Returns a Role object matching the given name.
     * @param name role name to search for
     * @return Role
     */
    Role loadByName(String name);
    /**
     * Saves details of a Role to the datastore.
     * @param roleDao sets the roleDao
     */
    void setRoleDao(RoleDao roleDao);
}
