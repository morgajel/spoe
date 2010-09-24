package com.morgajel.spoe.dao;
import org.apache.log4j.Logger;

import java.util.List;
import com.morgajel.spoe.model.Role;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the RoleDao interface, used to save and load roles from the datasource.
 */
@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {

	@Autowired
	public SessionFactory sessionFactory;
	private transient static Logger logger = Logger.getLogger("com.morgajel.spoe.dao.RoleDao");
	
	/**
	 * Sets the Session Factory used to get the currentSession. 
	 */
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
	
	/**
	 * returns a query based on a querystring and username, since that appears to be the 
	 * common denominator among load methods. 
	 */
	private Query loadQueryByName(String queryString,String name){
		Session session = sessionFactory.getCurrentSession();
		return session.getNamedQuery(queryString).setString("name", name);
	}
	
	/**
	 * Saves a given role to the datasource. 
	 */
	@Override
	public void saveRole(Role role) {

		sessionFactory.getCurrentSession().saveOrUpdate(role);
	}
	
	/**
	 * Returns a List of all roles from the datasource with no qualifications. 
	 */
	@Override
	public List<Role> listRoles() {
	    return (List<Role>) sessionFactory.getCurrentSession().createCriteria(Role.class).list();
	}

	/**
	 * returns a role matching a given name. Will return null if none is found. 
	 */
	@Override
	public Role loadByName(String name) {
		logger.debug("attempting to load role by "+name );
		Role role= (Role) loadQueryByName("findRoleByName", name).list().get(0);
		logger.debug("++++ loaded role "+role);
		return role;
	}
}
