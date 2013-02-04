package com.epam.service;

import com.epam.dao.impl.UserSetingsDAO;
import com.epam.model.User;
import com.epam.model.UserSettings;

public class UserSettingsService {
	private UserSetingsDAO userSetingsDAO = new UserSetingsDAO();

	public void create(UserSettings user, String ou, String cn,
			String workContext) {
		this.userSetingsDAO.create(user, ou, cn, workContext);
	}

	public User read() {
		// TODO:
		return null;
	}

	public void update(User object) {
		// TODO:
	}
}
