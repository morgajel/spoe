package com.morgajel.spoe.dao;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.morgajel.spoe.controller.AccountController;
import com.morgajel.spoe.model.Account;
import com.morgajel.spoe.dao.AccountDaoImpl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


public class AccountDaoImplTest {
	private AccountDaoImpl accountdao;
	
	@Before
	public void setUp() throws Exception {
		this.accountdao = new AccountDaoImpl(); 
	}

	@After
	public void tearDown() throws Exception {
		this.accountdao = null;
	}
	
	@Test
	public void testSaveAccount() {
		Account account=new Account();
		//this.accountdao.saveAccount(account);
		//TODO need to test this
	}
	@Test
	public void testFindAccountByUsername(){
		Account account=new Account();
		String username="realusername";
		//account=this.accountdao.findAccountByUsername(username);
		//TODO need to test this
	}
	@Test
	public void testListAccounts(){
		//List<Account> accounts= accountdao.listAccounts();
		//TODO need to test this
	}
}
