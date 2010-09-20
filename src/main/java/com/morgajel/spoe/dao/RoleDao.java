package com.morgajel.spoe.dao;

import com.morgajel.spoe.model.Role;
import java.util.List;

public interface RoleDao {

	  // To Save the role detail
	  public void saveRole ( Role role );
	  
	  // To get list of all Roles
	  public List<Role> listRoles();
	  
	  public Role loadByName(String name);
	  public Role loadByID(int roleId);
	  
	
}
