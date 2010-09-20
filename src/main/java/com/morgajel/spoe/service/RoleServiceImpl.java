package com.morgajel.spoe.service;

import java.util.List;

import com.morgajel.spoe.model.Role;
import com.morgajel.spoe.dao.RoleDao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("roleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;
	private transient static Logger logger = Logger.getLogger("com.morgajel.spoe.service.RoleService");

	//TODO unit test
	public RoleServiceImpl() {
	}
	//TODO unit test
	@Override
	public void setRoleDao(RoleDao roleDao){
		this.roleDao=roleDao;  
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addRole(Role role) {
		roleDao.saveRole(role);
	}	
	@Override
	public List<Role> listRoles() {
	    return roleDao.listRoles();
	}
	@Override
	public Role loadByName(String name) {
		logger.info("Loading role "+name);
		Role role=roleDao.loadByName(name);
		return role;
	}
}















