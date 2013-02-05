package com.epam.controller;

import java.util.ArrayList;
import java.util.List;

import com.epam.model.PropertyName;
import com.epam.model.Tenant;
import com.epam.model.User;
import com.epam.model.UserSettings;
import com.epam.service.TenantService;
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

		// UserSettings userSettingsName = new UserSettings();
		// userSettingsName.setUserId(user.getId());
		// userSettingsName.setPropertyName(PropertyName.NAME);
		// userSettingsName.setPropertyValue("Newee");
		// userSettingsName.setRecordId(1);
		//
		// UserSettings userSettingsBirth = new UserSettings();
		// userSettingsBirth.setUserId(user.getId());
		// userSettingsBirth.setRecordId(1);
		// userSettingsBirth.setPropertyName(PropertyName.BIRTHDAY);
		// userSettingsBirth.setPropertyValue("25/09/1989");
		//
		// UserSettings userSettingsEmail = new UserSettings();
		// userSettingsEmail.setUserId(user.getId());
		// userSettingsEmail.setRecordId(1);
		// userSettingsEmail.setPropertyName(PropertyName.EMAIL);
		// userSettingsEmail.setPropertyValue("test@ukr.net");

		// UserService userService = new UserService();

		// userService.create(user);

		// User user2 = userService.read(user.getName());

		// System.out.println("Read user email: " + user2.getEmail());

		// user.setEmail("sen_updated@ukr.net");
		// userService.update(user);
		// user2 = userService.read(user.getName());

		// System.out.println("Updated user email: " + user2.getEmail());

		// List<User> users = userService.readAll();
		//
		// for (User user3 : users) {
		// System.out.println(user3.getName());
		// }
	}
}
