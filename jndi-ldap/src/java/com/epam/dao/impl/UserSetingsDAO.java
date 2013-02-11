package com.epam.dao.impl;

import java.util.List;

import com.epam.dao.GenericDAO;
import com.epam.model.UserSettings;

public class UserSetingsDAO extends GenericDAO<UserSettings> {
	@Override
	public void create(UserSettings userSetings, String baseContext) {
		super.create(userSetings, baseContext);
	}

	@Override
	public UserSettings read(String cn, String baseContext, String javaClassName) {
		UserSettings userSetings = super.read(cn, baseContext, javaClassName);
		return userSetings;
	}

	@Override
	public void update(UserSettings userSetings, String cn, String baseContext) {
		super.update(userSetings, cn, baseContext);
	}

	@Override
	public List<UserSettings> readAll(String baseContext, String javaClassName) {
		List<UserSettings> userSetings = super.readAll(baseContext,
				javaClassName);
		return userSetings;
	}

}
