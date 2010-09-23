package com.morgajel.spoe.dao;
import org.apache.log4j.Logger;

import java.util.List;
import com.morgajel.spoe.model.Role;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {

	@Autowired
	public SessionFactory sessionFactory;
	private transient static Logger logger = Logger.getLogger("com.morgajel.spoe.dao.RoleDao");

	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
		
	}

	@Override
	public void saveRole(Role role) {

		sessionFactory.getCurrentSession().saveOrUpdate(role);
	}	
	@Override
	public List<Role> listRoles() {
	    return (List<Role>) sessionFactory.getCurrentSession().createCriteria(Role.class).list();

	}

	// TODO implement this somehow.
	@Override
	public Role loadByName(String name) {
		//logger.debug("attempting to load role by "+name );
		Role role= (Role) sessionFactory.getCurrentSession().getNamedQuery("findRoleByName").setString("name", name).list().get(0);
		logger.debug("++++ loaded role "+role);
		return role;
		
	}

}
