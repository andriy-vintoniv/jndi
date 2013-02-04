package com.epam.dao.impl;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

import com.epam.dao.GenericDAO;
import com.epam.model.User;
import com.epam.service.ConnectionService;
import com.epam.service.LDAPUtilService;

public class UserDAO implements GenericDAO<User> {

	private LDAPUtilService ldapUtilService;
	private ConnectionService connectionService;
	private DirContext dirContext;
	private String commonName;
	private Attributes attributes;

	public UserDAO() {
		ldapUtilService = new LDAPUtilService();
		connectionService = new ConnectionService();
		dirContext = connectionService.connect();
	}

	@Override
	public void create(User user, String ou, String cn, String workContext) {
		commonName = user.getName();
		if (ou != null && cn == null) {
			String[] objClassValues = { "top", "person" };
			ldapUtilService.addUserAttribute("ou", ou);

			if (commonName != null)
				ldapUtilService.addUserAttribute("cn", commonName);

			ldapUtilService.addUserAttributes("objectClass", objClassValues);

			this.attributes = ldapUtilService.getAttributes();

			System.out.println("Store working context: " + workContext);
			System.out.println("storing user entry ");
			try {
				dirContext.bind(workContext, null, attributes);
			} catch (NamingException e) {
				System.out.println("Storing  operation failed: " + e);
			}
		} else if (ou == null && cn != null) {
			ldapUtilService.addUserAttribute("cn", cn);
			this.attributes = ldapUtilService.getAttributes();
			try {
				dirContext.bind(workContext, user, attributes);
			} catch (NamingException e) {
				System.out.println("Storing  operation failed: " + e);
			}
		}
	}

	@Override
	public User read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(User object) {
		// TODO Auto-generated method stub

	}

}
