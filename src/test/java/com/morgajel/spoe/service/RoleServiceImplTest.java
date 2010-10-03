package com.morgajel.spoe.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Before;

import com.morgajel.spoe.dao.RoleDao;
import com.morgajel.spoe.model.Role;

import static org.mockito.Mockito.*;
/**
 * RoleServiceImpl Test.
 * @author jmorgan
 *
 */
public class RoleServiceImplTest {

    private RoleDao mockRoleDao;
    private Role mockRole;
    private RoleServiceImpl roleService;
    private String rolename = "ROLE_REVIEWER";
    /**
     * Setup creates fresh mockups for each test.
     * @throws Exception generic exception
     */
    @Before
    public void setUp() throws Exception {
        roleService = new RoleServiceImpl();
        mockRoleDao = mock(RoleDao.class);
        mockRole = mock(Role.class);
        roleService.setRoleDao(mockRoleDao);
    }
    /**
     * Tests the ListRole functionality.
     */
    @Test
    public void testListRole() {
        List<Role> results = new ArrayList<Role>();
        results.add(new Role());
        results.add(new Role());
        when(mockRoleDao.listRoles()).thenReturn(results);
        assertEquals(results, roleService.listRoles());
    }
    /**
     * Tests loadAccountByName functionality.
     */
    @Test
    public void testLoadAccountByName() {
        when(mockRoleDao.loadByName(rolename)).thenReturn(mockRole);
        Role role = roleService.loadByName(rolename);
        assertEquals(role, mockRole);
        when(mockRoleDao.loadByName(rolename)).thenReturn(null);
        role = roleService.loadByName(rolename);
        assertNull(role);
    }
    /**
     * Tests the AddRole functionality.
     */
    @Test
    public void testAddRole() {
        roleService.addRole(mockRole);
        verify(mockRoleDao).saveRole(mockRole);
    }
}
