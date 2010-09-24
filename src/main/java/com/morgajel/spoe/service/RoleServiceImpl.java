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
 * Default Role Service Implementation used for managing RoleDao
 */
@Service("roleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;
	private transient static Logger logger = Logger.getLogger("com.morgajel.spoe.service.RoleService");

	/** 
	 * Set RoleDao for accessing the datasource. 
	 */
	@Override
	public void setRoleDao(RoleDao roleDao){
		this.roleDao=roleDao;  
	}
	/** 
	 * Add a given Role to the datasource. 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addRole(Role role) {
		roleDao.saveRole(role);
	}
	/** 
	 * Returns a list of services with no qualifications. 
	 */
	@Override
	public List<Role> listRoles() {
	    return roleDao.listRoles();
	}
	/** 
	 * Load a Role from the datasource matching a given name. Will return null if none is found. 
	 */
	@Override
	public Role loadByName(String name) {
		logger.info("Loading role "+name);
		Role role=roleDao.loadByName(name);
		return role;
	}
}
