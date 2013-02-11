package com.epam.dao;

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

import com.epam.service.ConnectionService;

public abstract class GenericDAO<T> {

	private ConnectionService connectionService;

	public GenericDAO() {
		connectionService = new ConnectionService();
	}

	public void create(T object, String baseContext) {
		// DirContext dirContext = connectionService.connect();
		//
		// String context = "cn=" + cn + "," + baseContext;
		// if (cn != null) {
		//
		// System.out.println("storing entry ");
		// try {
		// dirContext.bind(context, object);
		// } catch (NamingException e) {
		// System.out.println("Storing  operation failed: " + e);
		// }
		// }
		// connectionService.close(dirContext);
	}

	public T read(String cn, String baseContext, String javaClassName) {
		DirContext dirContext = connectionService.connect();
		T object = null;
		Attribute cnAttribute = new BasicAttribute("cn");
		Attribute javaClassNames = new BasicAttribute("javaClassNames");

		cnAttribute.add(cn);
		javaClassNames.add(javaClassName);

		// Instantiate an Attributes object and put search attributes in it
		Attributes attrs = new BasicAttributes(true);
		attrs.put(cnAttribute);
		attrs.put(javaClassNames);

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
								object = (T) obj;
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

		return object;
	}

	public List<T> readAll(String groupsContext, String javaClassName) {
		DirContext dirContext = connectionService.connect();
		List<T> result = new ArrayList<T>();
		Attribute javaClassNames = new BasicAttribute("javaClassNames");

		javaClassNames.add(javaClassName);

		Attributes attrs = new BasicAttributes(true);

		NamingEnumeration<?> ne;
		try {
			ne = dirContext.search(groupsContext, attrs);

			if (ne != null) {
				while (ne.hasMore()) {
					SearchResult sr = (SearchResult) ne.next();

					String entryRDN = sr.getName();

					String searchContext = entryRDN + "," + groupsContext;

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
								result.add((T) obj);
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

		return result;

	}

	public void update(T object, String cn, String baseContext) {
		DirContext dirContext = connectionService.connect();

		String context = "cn=" + cn + "," + baseContext;
		try {
			dirContext.rebind(context, object);
		} catch (NamingException e) {
			System.out.println("Storing  operation failed: " + e);
		}
		connectionService.close(dirContext);
	}

}
