package com.epam.dao.impl;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;

import com.epam.dao.GenericDAO;
import com.epam.model.Tenant;
import com.epam.model.User;
import com.epam.service.ConnectionService;
import com.epam.service.UserService;

public class TenantDAO extends GenericDAO<Tenant> {
	private ConnectionService connectionService;
	private UserService userService;

	public TenantDAO() {
		connectionService = new ConnectionService();
		userService = new UserService();
	}

	@Override
	public Tenant read(String cn, String baseContext, String javaClassName) {
		Tenant tenant = super.read(cn, baseContext, javaClassName);
		return tenant;
	}

	@Override
	public void update(Tenant tenant, String cn, String baseContext) {
		super.update(tenant, cn, baseContext);
	}

	@Override
	public void create(Tenant tenant, String baseContext) {
		DirContext dirContext = connectionService.connect();

		String entryDN = "cn=" + tenant.getName() + "," + baseContext;

		Attribute cn = new BasicAttribute("cn", tenant.getName());
		Attribute sn = new BasicAttribute("sn", tenant.getName());

		List<User> users = tenant.getUsers();

		Attribute oc = new BasicAttribute("objectClass");
		oc.add("top");
		oc.add("person");
		oc.add("organizationalPerson");
		oc.add("inetOrgPerson");

		try {
			BasicAttributes entry = new BasicAttributes();
			entry.put(cn);
			entry.put(sn);
			entry.put(oc);

			dirContext.createSubcontext(entryDN, entry);

			for (User user : users) {
				userService.create(user, entryDN);
			}
		} catch (NamingException e) {
			System.err.println("AddTenant: error adding entry." + e);
		}

	}

	@Override
	public List<Tenant> readAll(String groupsContext, String javaClassName) {
		return super.readAll(groupsContext, javaClassName);
	}
}
