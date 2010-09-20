package com.morgajel.spoe.dao;
import org.apache.log4j.Logger;
import java.util.Date;
import java.util.List;

import com.morgajel.spoe.model.Role;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private SessionFactory sessionFactory;
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
	public Role loadByID(int roleId) {
		// TODO Auto-generated method stub
		return null;
	}
	// TODO implement this somehow.
	@Override
	public Role loadByName(String name) {
		//logger.debug("attempting to load role by "+name );
		Session session=sessionFactory.getCurrentSession();
		//logger.debug("snagged current session "+session );
		Query nq=session.getNamedQuery("findRoleByName");
		//logger.debug("snagged current query "+nq );
		List roleList= nq.setString("name", name).list();
		//logger.debug("snagged roleList "+roleList );
		Role role= (Role)roleList.get(0);
		logger.info("++++ loaded role "+role);
		return role;
		
	}

}
