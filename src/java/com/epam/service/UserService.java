package com.epam.service;

import com.epam.dao.impl.UserDAO;
import com.epam.model.User;

public class UserService {
	private final static String BASE_CONTEXT = "cn=User,ou=groups";

	private UserDAO userDAO = new UserDAO();

	public void create(User user) {
		this.userDAO.create(user, user.getName(), BASE_CONTEXT);
	}

	public User read(String cn) {
		User user = this.userDAO.read(cn, BASE_CONTEXT, User.class.getName());
		return user;
	}

	public void update(User user) {
		this.userDAO.update(user, user.getName(), BASE_CONTEXT);
	}

}
