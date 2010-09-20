package com.morgajel.spoe.service;

import com.morgajel.spoe.model.Role;

import java.util.List;
import com.morgajel.spoe.dao.RoleDao;

public interface RoleService {
	public void addRole(Role role);
    public List<Role> listRoles();
    public Role loadByName(String name);
    //public Role loadByID(int roleId);
    
    public void setRoleDao(RoleDao roleDao);
    

}