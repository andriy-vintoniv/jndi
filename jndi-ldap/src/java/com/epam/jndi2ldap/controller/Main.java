package com.epam.jndi2ldap.controller;

import java.util.ArrayList;
import java.util.List;

import com.epam.jndi2ldap.model.Tenant;
import com.epam.jndi2ldap.model.User;
import com.epam.jndi2ldap.service.TenantService;
import com.epam.jndi2ldap.service.UserService;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		User user = new User();
		user.setId(1);
		user.setBirthday("25/09/1989");
		user.setEmail("sem.ukr.net");
		user.setName("Test");
		user.setPassword("password2");

		User user1 = new User();
		user1.setId(1);
		user1.setBirthday("25/09/1989");
		user1.setEmail("sem.ukr.net");
		user1.setName("Test1");
		user1.setPassword("password2");

		User user2 = new User();
		user2.setId(1);
		user2.setBirthday("25/09/1989");
		user2.setEmail("sem.ukr.net");
		user2.setName("Test2");
		user2.setPassword("password2");

		User user3 = new User();
		user3.setId(1);
		user3.setBirthday("25/09/1989");
		user3.setEmail("sem.ukr.net");
		user3.setName("Test3");
		user3.setPassword("password2");

		List<User> users = new ArrayList<User>();
		users.add(user);
		users.add(user1);
		users.add(user2);
		users.add(user3);

		Tenant tenant = new Tenant();
		tenant.setId(1);
		tenant.setName("Test Tenant");
		tenant.setUsers(users);

		TenantService tenantService = new TenantService();
		tenantService.create(tenant);

		Tenant tenant2 = tenantService.read(tenant.getName());

		System.out.println();

		UserService userService = new UserService();

		User userRead = userService.read(tenant.getName(), user.getName());

	}
}
