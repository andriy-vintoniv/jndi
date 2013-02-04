package com.epam.controller;

import com.epam.model.User;
import com.epam.service.JavaToLDAPService;
import com.epam.service.UserService;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		User user = new User();
		user.setId(1);
		user.setBirthday("25/09/1989");
		user.setEmail("sem.ukr.net");
		user.setName("Semen");
		user.setPassword("password2");

		UserService userService = new UserService();

		userService.create(user, "User", null, "ou=groups");

		JavaToLDAPService javaToLDAPService = new JavaToLDAPService();
		// javaToLDAPService.setPassword("secret");
		// javaToLDAPService.setUserID("Alice");

		// Setting a Java object.
		try {
			javaToLDAPService.setJavaObject(user);
		} catch (Exception e) {
			System.out.println("Not a serializable object");
			e.printStackTrace();
		}

		javaToLDAPService.setJavaObjectCN("preferences");
		javaToLDAPService.writeToServer();
		System.out.println("Entry stored in ApacheDS..");
	}
}
