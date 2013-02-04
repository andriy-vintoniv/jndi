package com.epam.controller;

import com.epam.model.User;
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
		user.setName("Semen3");
		user.setPassword("password2");

		UserService userService = new UserService();

		userService.create(user);

		User user2 = userService.read(user.getName());

		System.out.println("Read user email: " + user2.getEmail());

		user.setEmail("sen_updated@ukr.net");
		userService.update(user);
		user2 = userService.read(user.getName());

		System.out.println("Updated user email: " + user2.getEmail());
	}
}
