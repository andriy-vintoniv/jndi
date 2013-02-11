package com.epam.dao.impl;

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

import com.epam.dao.GenericDAO;
import com.epam.model.User;
import com.epam.service.ConnectionService;

public class UserDAO extends GenericDAO<User> {

	private ConnectionService connectionService;

	public UserDAO() {
		connectionService = new ConnectionService();
	}

	@Override
	public void create(User user, String baseContext) {
		DirContext dirContext = connectionService.connect();

		String entryDN = "cn=" + user.getName() + "," + baseContext;

		Attribute cn = new BasicAttribute("cn", user.getName());
		Attribute sn = new BasicAttribute("sn", user.getName());
		Attribute mail = new BasicAttribute("mail", user.getEmail());
		Attribute password = new BasicAttribute("userPassword",
				user.getPassword());
		Attribute oc = new BasicAttribute("objectClass");
		oc.add("top");
		oc.add("person");
		oc.add("organizationalPerson");
		oc.add("inetOrgPerson");

		try {
			BasicAttributes entry = new BasicAttributes();
			entry.put(cn);
			entry.put(sn);
			entry.put(mail);
			entry.put(password);
			entry.put(oc);

			dirContext.createSubcontext(entryDN, entry);
		} catch (NamingException e) {
			System.err.println("AddUser: error adding entry." + e);
		}
	}

	@Override
	public User read(String cn, String baseContext, String javaClassName) {
		User user = new User();

		DirContext dirContext = connectionService.connect();
		Attribute cnAttribute = new BasicAttribute("cn");
		Attribute userPassword = new BasicAttribute("userPassword");
		Attribute sn = new BasicAttribute("sn");
		Attribute email = new BasicAttribute("mail");

		cnAttribute.add(cn);

		// Instantiate an Attributes object and put search attributes in it
		Attributes attrs = new BasicAttributes(true);
		attrs.put(cnAttribute);

		NamingEnumeration<?> ne;
		try {
			ne = dirContext.search(baseContext, attrs);

			if (ne != null) {
				while (ne.hasMore()) {
					SearchResult sr = (SearchResult) ne.next();

					String entryRDN = sr.getName();

					String searchContext = entryRDN + "," + baseContext;

					SearchControls ctls = new SearchControls();
					ctls.setReturningObjFlag(true);
					ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

					String filter = "(|(javaClassName=" + javaClassName + "))";

					NamingEnumeration<SearchResult> ne1 = dirContext.search(
							searchContext, filter, ctls);

					if (ne != null) {
						while (ne1.hasMore()) {
							SearchResult sr1 = ne1.next();

							Object obj = sr1.getObject();

							if (obj != null) {
								System.out.println("User object found " + obj);

							}
						}
					}
				}
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connectionService.close(dirContext);

		return user;
	}

	@Override
	public void update(User user, String cn, String baseContext) {
		super.update(user, cn, baseContext);
	}

	@Override
	public List<User> readAll(String baseContext, String javaClassName) {
		List<User> users = super.readAll(baseContext, javaClassName);
		return users;
	}

}
