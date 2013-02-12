package com.epam.service;

import java.util.List;

import com.epam.dao.impl.UserDAO;
import com.epam.model.User;

public class UserService {

	private UserDAO userDAO = new UserDAO();

	public void create(User user, String tenantDN) {
		this.userDAO.create(user, tenantDN);
	}

	public User read(String tenantDN, String userName) {
		User user = this.userDAO.read(tenantDN, userName);
		return user;
	}

	public void update(User user) {
	}

	public List<User> readAll(String tenantName) {
		List<User> users = this.userDAO.readAll(tenantName);
		return users;
	}
}
