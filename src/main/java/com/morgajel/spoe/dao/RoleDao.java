package com.morgajel.spoe.dao;

import com.morgajel.spoe.model.Role;
import java.util.List;
/**
 * Hibernate object interface used for storing and loading Roles.
 */
public interface RoleDao {

    /**
     * Save a provided role to the datasource.
     * @param role role to save
     */
    void saveRole(Role role);

    /**
     * List all roles in the datasource.
     * @return List<Role>
     */
    List<Role> listRoles();

    /**
     * Return an role with a given name, or else null.
     * @param name role name to load
     * @return Role
     */
    Role loadByName(String name);

}
