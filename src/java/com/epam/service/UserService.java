package com.epam.service;

import com.epam.dao.impl.UserDAO;
import com.epam.model.User;

public class UserService {

	private UserDAO userDAO = new UserDAO();

	public void create(User user, String ou, String cn, String workContext) {
		this.userDAO.create(user, ou, cn, workContext);
	}

	public User read() {
		// TODO:
		return null;
	}

	public void update(User object) {
		// TODO:
	}

}
