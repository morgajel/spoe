package com.morgajel.spoe.service;

import com.morgajel.spoe.model.Role;

import java.util.List;
import com.morgajel.spoe.dao.RoleDao;
/** 
 * Role Service for managing RoleDao 
 */
public interface RoleService {
	/** 
	 * Adds a Role object to the datastore. 
	 */
	public void addRole(Role role);
    /** 
	 * List all roles currently in the system. 
	 */
    public List<Role> listRoles();
    /** 
	 * Returns a Role object matching the given name.
	 */
    public Role loadByName(String name);
    /** 
	 * Saves details of a Role to the datastore.  
	 */
    public void setRoleDao(RoleDao roleDao);
}