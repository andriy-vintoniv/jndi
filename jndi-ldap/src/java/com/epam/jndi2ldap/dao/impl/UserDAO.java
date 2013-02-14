package com.epam.jndi2ldap.dao.impl;

import java.util.ArrayList;
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

import com.epam.jndi2ldap.model.User;
import com.epam.jndi2ldap.service.ConnectionService;

public class UserDAO {

	private ConnectionService connectionService;
	private List<String> attributesList;

	public UserDAO() {
		connectionService = new ConnectionService();

		attributesList = new ArrayList<String>();
		attributesList.add("cn");
		attributesList.add("sn");
		attributesList.add("mail");
		attributesList.add("userpassword");
		attributesList.add("uid");
		// attributesList.add("birthday");
	}

	public void create(User user, String baseContext) {
		DirContext dirContext = connectionService.connect();

		String entryDN = "cn=" + user.getName() + "," + baseContext;

		try {
			Attribute cn = new BasicAttribute("cn", user.getName());
			Attribute sn = new BasicAttribute("sn", user.getName());
			Attribute mail = new BasicAttribute("mail", user.getEmail());
			Attribute password;
			password = new BasicAttribute("userPassword", user.getPassword());
			Attribute id = new BasicAttribute("uid", user.getId().toString());
			// Attribute birthday = new BasicAttribute("birthday",
			// user.getBirthday());
			Attribute oc = new BasicAttribute("objectClass");
			oc.add("top");
			oc.add("person");
			oc.add("organizationalPerson");
			oc.add("inetOrgPerson");

			BasicAttributes entry = new BasicAttributes();
			entry.put(cn);
			entry.put(sn);
			entry.put(mail);
			entry.put(password);
			entry.put(id);
			entry.put(oc);
			// entry.put(birthday);

			dirContext.createSubcontext(entryDN, entry);
		} catch (NamingException e) {
			System.err.println("AddUser: error adding entry." + e);
		}
	}

	public User read(String tenantName, String userName) {
		User user = new User();
		String searchBase = "cn=" + tenantName + ","
				+ ConnectionService.BASE_CONTEXT;
		String searchFilter = "cn=" + userName;

		DirContext ctx = connectionService.connect();
		try {

			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);

			NamingEnumeration<?> results = ctx.search(searchBase, searchFilter,
					constraints);

			while (results != null && results.hasMore()) {
				SearchResult sr = (SearchResult) results.next();
				String dn = sr.getName() + "," + searchBase;
				System.out.println("Distinguished Name is " + dn);

				Attributes ar = ctx.getAttributes(dn, attributesList
						.toArray(new String[attributesList.size()]));

				if (ar == null) {
					System.out.println("Entry " + dn);
					System.out
							.println(" has none of the specified attributes\n");
				} else {

					user.setName(ar.get(attributesList.get(0)).getAll()
							.nextElement().toString());
					user.setEmail(ar.get(attributesList.get(2)).getAll()
							.nextElement().toString());
					user.setPassword(ar.get(attributesList.get(3)).getAll()
							.nextElement().toString());
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		connectionService.close(ctx);

		return user;
	}

	public List<User> readAll(String tenantName) {
		List<User> users = new ArrayList<User>();

		String searchBase = "cn=" + tenantName + ","
				+ ConnectionService.BASE_CONTEXT;
		String searchFilter = "objectClass=person";

		DirContext ctx = connectionService.connect();
		try {

			System.out.println("Context Sucessfully Initialized");

			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);

			NamingEnumeration<?> results = ctx.search(searchBase, searchFilter,
					constraints);

			while (results != null && results.hasMore()) {
				SearchResult sr = (SearchResult) results.next();
				String dn = sr.getName() + "," + searchBase;
				System.out.println("Distinguished Name is " + dn);

				Attributes ar = ctx.getAttributes(dn, attributesList
						.toArray(new String[attributesList.size()]));
				User user = new User();
				if (ar == null) {
					System.out.println("Entry " + dn);
					System.out
							.println(" has none of the specified attributes\n");
				} else {
					user.setName(ar.get(attributesList.get(0)).getAll()
							.nextElement().toString());
					user.setEmail(ar.get(attributesList.get(2)).getAll()
							.nextElement().toString());
					user.setPassword(ar.get(attributesList.get(3)).getAll()
							.nextElement().toString());

					users.add(user);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		connectionService.close(ctx);

		return users;
	}

}
