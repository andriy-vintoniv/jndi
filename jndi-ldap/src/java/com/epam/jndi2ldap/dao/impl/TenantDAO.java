package com.epam.jndi2ldap.dao.impl;

import java.util.Enumeration;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.epam.jndi2ldap.model.Tenant;
import com.epam.jndi2ldap.model.User;
import com.epam.jndi2ldap.service.ConnectionService;
import com.epam.jndi2ldap.service.UserService;

public class TenantDAO {

	private ConnectionService connectionService;
	private UserService userService;

	public TenantDAO() {
		connectionService = new ConnectionService();
		userService = new UserService();
	}

	public Tenant read(String name) {
		Tenant tenant = new Tenant();

		String searchBase = ConnectionService.BASE_CONTEXT;
		String searchFilter = "cn=" + name;
		String attributes[] = { "cn", "sn" };

		DirContext ctx = connectionService.connect();
		try {

			System.out.println("Context Sucessfully Initialized");

			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);

			NamingEnumeration<?> results = ctx.search(searchBase, searchFilter,
					constraints);

			while (results != null && results.hasMore()) {
				SearchResult sr = (SearchResult) results.next();
				String dn = sr.getName() + "," + searchBase;
				System.out.println("Distinguished Name is " + dn);

				Attributes ar = ctx.getAttributes(dn, attributes);

				if (ar == null) {
					System.out.println("Entry " + dn);
					System.out
							.println(" has none of the specified attributes\n");
				} else {
					tenant.setName(ar.get(attributes[0]).getAll().nextElement()
							.toString());
					tenant.setUsers(userService.readAll(tenant.getName()));
					for (int i = 0; i < attributes.length; i++) {
						Attribute attr = ar.get(attributes[i]);
						System.out.println(attributes[i] + ":");

						for (Enumeration<?> vals = attr.getAll(); vals
								.hasMoreElements();) {
							System.out.println("\t" + vals.nextElement());
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		connectionService.close(ctx);

		return tenant;
	}

	public void update(Tenant tenant, String cn, String baseContext) {
	}

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

	public List<Tenant> readAll(String groupsContext, String javaClassName) {
		return null;
	}
}
