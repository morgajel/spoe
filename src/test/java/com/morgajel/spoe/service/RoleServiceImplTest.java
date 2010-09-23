package com.morgajel.spoe.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Before;

import com.morgajel.spoe.dao.AccountDao;
import com.morgajel.spoe.dao.RoleDao;
import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.model.Role;

import static org.mockito.Mockito.*;

public class RoleServiceImplTest {

    private RoleDao mockRoleDao;
    private Role mockRole;
	private RoleServiceImpl roleService;
	private String rolename="ROLE_REVIEWER";
	@Before
	public void setUp() throws Exception {
		roleService = new RoleServiceImpl();
        mockRoleDao = mock(RoleDao.class);
        mockRole = mock(Role.class);
        roleService.setRoleDao(mockRoleDao);
	}

	@Test
	public void testListRole() {
		
		List<Role> results = new ArrayList<Role>();
		results.add(new Role());
		results.add(new Role());
		when(mockRoleDao.listRoles()).thenReturn(results);
		assertEquals(results,roleService.listRoles());
	}

	@Test
	public void testLoadAccountByName() {
		when(mockRoleDao.loadByName(rolename)).thenReturn(mockRole);
		Role role=roleService.loadByName(rolename);
		assertEquals(role, mockRole);
		when(mockRoleDao.loadByName(rolename)).thenReturn(null);
		role=roleService.loadByName(rolename);
		assertNull(role);
	}
	@Test
	public void testAddRole() {
		roleService.addRole(mockRole);
		verify(mockRoleDao).saveRole(mockRole);
	}	
	
}
