package com.morgajel.spoe.dao;


import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.morgajel.spoe.model.Role;

import static org.mockito.Mockito.*;
/**
 * Tests to ensure RoleDaoImpl behaves.
 * @author jmorgan
 *
 */
public class RoleDaoImplTest {
    private RoleDaoImpl roleDao;
    private SessionFactory mockSessionFactory;
    private Role mockRole;
    private String rolename = "ROLE_REVIEWER";
    /**
     * Setup builds fresh mockups for each test.
     * @throws Exception generic exception
     */
    @Before
    public void setUp() throws Exception {
        mockSessionFactory = mock(SessionFactory.class, RETURNS_DEEP_STUBS);
        mockRole = mock(Role.class);
        roleDao = new RoleDaoImpl();
        roleDao.setSessionFactory(mockSessionFactory);
    }
    /**
     * Tear down destroys all objects after a test.
     * @throws Exception generic exception
     */
    @After
    public void tearDown() throws Exception {
        roleDao = null;
        reset(mockSessionFactory);
        reset(mockRole);
    }
    /**
     * Test setting the session factory.
     */
    @Test
    public void testSetSessionFactory() {
        roleDao.setSessionFactory(mockSessionFactory);
        assertEquals(mockSessionFactory, roleDao.getSessionFactory());
    }
    /**
     * Tests the saveRole functionality.
     */
    @Test
    public void testSaveRole() {
        roleDao.saveRole(mockRole);
        verify(mockSessionFactory).getCurrentSession();
    }
    /**
     * Test the ListRoles functionality.
     */
    @Test
    public void testListRoles() {
        List<Role> mockRoleList = mock(List.class);
        when(mockSessionFactory.getCurrentSession().createCriteria(Role.class).list()).thenReturn(mockRoleList);
        List<Role> roles = roleDao.listRoles();
        assertEquals(mockRoleList, roles);
    }
    /**
     * Tests loading a role by it's name.
     */
    @Test
    public void testLoadByName() {
        when(mockSessionFactory.getCurrentSession().getNamedQuery("findRoleByName").setString("name", rolename).list().get(0)).thenReturn(mockRole);
        Role role = roleDao.loadByName(rolename);
        assertEquals(mockRole, role);
    }
}
