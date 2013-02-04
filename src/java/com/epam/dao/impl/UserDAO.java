package com.epam.dao.impl;

import com.epam.dao.GenericDAO;
import com.epam.model.User;

public class UserDAO extends GenericDAO<User> {

	public UserDAO() {
	}

	@Override
	public void create(User user, String cn, String baseContext) {
		super.create(user, cn, baseContext);
	}

	@Override
	public User read(String cn, String baseContext, String javaClassName) {
		User user = super.read(cn, baseContext, javaClassName);
		return user;
	}

	@Override
	public void update(User user, String cn, String baseContext) {
		super.update(user, cn, baseContext);
	}

}
