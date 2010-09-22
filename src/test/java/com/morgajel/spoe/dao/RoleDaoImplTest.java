package com.morgajel.spoe.dao;


import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Role;

import static org.mockito.Mockito.*;

public class RoleDaoImplTest {
	private RoleDaoImpl roleDao;
	private SessionFactory mockSessionFactory;
	private Role mockRole;
	@Before
	public void setUp() throws Exception {
        mockSessionFactory = mock(SessionFactory.class,RETURNS_DEEP_STUBS);
        mockRole=mock(Role.class);
        roleDao = new RoleDaoImpl();
        roleDao.setSessionFactory(mockSessionFactory);
	}

	@After
	public void tearDown() throws Exception {
		roleDao=null;
		mockSessionFactory=null;
		mockRole=null;
	}

	@Test
	public void testSetSessionFactory(){
		roleDao.setSessionFactory(mockSessionFactory);
		assertEquals(mockSessionFactory,roleDao.sessionFactory);
	}

	@Test
	public void testSaveRole(){
		roleDao.saveRole(mockRole);
		verify(mockSessionFactory.getCurrentSession());
	}	


//		@Override
//		public List<Role> listRoles() {
//		    return (List<Role>) sessionFactory.getCurrentSession().createCriteria(Role.class).list();
	//
//		}
	//
	//// TODO implement this somehow.
//		@Override
//		public Role loadByID(int roleId) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		// TODO implement this somehow.
//		@Override
//		public Role loadByName(String name) {
//			//logger.debug("attempting to load role by "+name );
//			Session session=sessionFactory.getCurrentSession();
//			//logger.debug("snagged current session "+session );
//			Query nq=session.getNamedQuery("findRoleByName");
//			//logger.debug("snagged current query "+nq );
//			List roleList= nq.setString("name", name).list();
//			//logger.debug("snagged roleList "+roleList );
//			Role role= (Role)roleList.get(0);
//			logger.info("++++ loaded role "+role);
//			return role;
//			
//		}

	
}
