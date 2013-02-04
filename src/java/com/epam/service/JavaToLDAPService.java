package com.epam.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.epam.MessagingPreferences;
import com.epam.StoreAlicePreferences;

public class JavaToLDAPService {

	protected String commonName = null;
	protected String surName = null;
	protected String userID = null;
	protected String javaObjectCN = null;
	protected String userName = null;
	protected String password = null;
	protected String initialContext = null;

	private String workingContext = null;

	protected DirContext dirContext = null;
	protected Properties properties = null;
	protected Object javaObject = null;
	protected Attributes attributes = null;

	public JavaToLDAPService() {
		properties = new Properties();
		attributes = new BasicAttributes(true);

		workingContext = "ou=users";
		initialContext = workingContext;

		try {
			properties.load(StoreAlicePreferences.class
					.getResourceAsStream("/ApacheDS.properties"));
			properties
					.setProperty("java.naming.security.credentials", "secret");

		} catch (Exception e) {
			System.out.println("Operation failed: " + e);
		}
	}// LDAPJavaObjects

	public void setUserName(String userName) {
		properties.setProperty("java.naming.security.principal", userName);
		this.userName = userName;
	}

	public void setPassword(String password) {
		properties.setProperty("java.naming.security.credentials", password);
		this.password = password;
	}

	public void connect() {
		try {
			// Fetch the directory context.
			dirContext = new InitialDirContext(properties);
			System.out.println(">>> Connected to ApacheDS.");
		} catch (NamingException e) {
			System.out
					.println("Getting initial context operation failed: " + e);
		}
	}

	public void close() {
		try {
			// Close the directory context.
			dirContext.close();
			System.out.println(">>> Closing connection to ApacheDS.");
		} catch (NamingException e) {
			System.out
					.println("Closing initial context operation failed: " + e);
		}
	}

	public void setJavaObject(Object obj)
			throws java.io.NotSerializableException {
		if (obj instanceof java.io.Serializable)
			javaObject = obj;
	}

	public boolean isObjectPresent(String ou, String cn, String workContext) {
		NamingEnumeration<?> ne = search(ou, cn, workContext);
		if (ne != null)
			return true;
		else
			return false;

	}// isObjectPresent

	public NamingEnumeration<?> search(String user, String object,
			String workContext) {

		NamingEnumeration<?> ne = null;
		String filter = new String();
		SearchControls ctls = new SearchControls();
		ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		try {
			if (user != null && object == null) {
				addUserAttribute("ou", user);
				addUserAttribute("objectClass", "person");

				System.out.println(">> Searching " + user + " in workContext: "
						+ workContext);
				ne = dirContext.search(workContext, attributes);

				if (ne != null && ne.hasMore())
					return ne;
				else
					return null;

			} else if (user == null && object != null) {
				filter = "(cn=" + object + ")";
				ctls.setReturningObjFlag(true);

				System.out.println(">> Searching " + object
						+ " in workContext: " + workContext);
				ne = dirContext.search(workContext, filter, ctls);

				if (ne != null && ne.hasMore())
					return ne;
				else
					return null;
			}
		} catch (NamingException e) {
			System.out.println("Search object operation failed: " + e);
		}
		return null;
	}// search

	public void update(String ou, String cn, String workContext) {
		if (ou != null && cn == null) {
			String[] objClassValues = { "top", "person" };
			addUserAttribute("ou", ou);

			if (commonName != null)
				addUserAttribute("cn", commonName);

			addUserAttributes("objectClass", objClassValues);

			try {
				dirContext.rebind(workContext, null, attributes);
			} catch (NamingException e) {
				System.out.println("Storing  operation failed: " + e);
			}
		} else if (ou == null && cn != null) {
			addUserAttribute("cn", cn);

			try {
				dirContext.rebind(workContext, javaObject, attributes);
			} catch (NamingException e) {
				System.out.println("Updating  operation failed: " + e);
			}
		}
	}// update

	public void store(String ou, String cn, String workContext) {

		if (ou != null && cn == null) {
			String[] objClassValues = { "top", "person" };
			addUserAttribute("ou", ou);

			if (commonName != null)
				addUserAttribute("cn", commonName);

			addUserAttributes("objectClass", objClassValues);

			System.out.println("Store working context: " + workContext);
			System.out.println("storing user entry ");
			try {
				dirContext.bind(workContext, null, attributes);
			} catch (NamingException e) {
				System.out.println("Storing  operation failed: " + e);
			}
		} else if (ou == null && cn != null) {
			addUserAttribute("cn", cn);

			try {
				dirContext.bind(workContext, javaObject, attributes);
			} catch (NamingException e) {
				System.out.println("Storing  operation failed: " + e);
			}
		}
	}// store

	public void addUserAttribute(String name, String value) {
		Attribute attr = new BasicAttribute(name);
		attr.add(value);
		attributes.put(attr);
	}// addUserAttribute

	public void addUserAttributes(String name, String[] value) {
		Attribute attr = new BasicAttribute(name);

		if (value != null) {
			for (int i = 0; i < value.length; i++)
				attr.add(value[i]);
		}
		attributes.put(attr);
	}// addUserAttribute

	public void writeToServer() {
		connect();

		if (userID == null && commonName != null)
			userID = commonName;

		// Check if the cn of the Java object is set.
		// If not, use commonName as object cn.
		if (javaObject != null) {
			if (javaObjectCN == null && commonName != null)
				javaObjectCN = commonName;
		}

		System.out.println(">> Writting to ApacheDS.");
		if (!(isObjectPresent(userID, null, initialContext))) {
			System.out.println(">> User entry is not present.");
			workingContext = "ou=" + userID + "," + initialContext;

			System.out.println(">>  Storing User entry.");
			store(userID, null, workingContext);
			workingContext = "cn=" + javaObjectCN + ",ou=" + userID + ","
					+ initialContext;

			System.out.println(">> Storing Object under user entry");
			store(null, javaObjectCN, workingContext);
		} else if (isObjectPresent(null, javaObjectCN, "ou=" + userID + ","
				+ initialContext)) {
			System.out.println(">> User entry is present.");
			workingContext = "cn=" + javaObjectCN + ",ou=" + userID + ","
					+ initialContext;

			System.out.println(">> Updating Java object under user entry.");
			update(null, javaObjectCN, workingContext);
		} else {
			workingContext = "cn=" + javaObjectCN + ",ou=" + userID + ","
					+ initialContext;

			System.out.println(">> Storing Java object under user entry.");
			store(null, javaObjectCN, workingContext);
		}

		close();
	}// writeToServer()

	public void searchFromServer(String user, String object) {
		connect();
		NamingEnumeration<?> ne = search(user, object, initialContext);

		if (ne != null)
			processSearchResult(ne);
		else
			System.out.println("searchFromServer() failed..");

		close();
	}// searchFromServer

	private void processSearchResult(NamingEnumeration<?> ne) {
		try {
			if (ne != null) {
				while (ne.hasMore()) {
					SearchResult sr = (SearchResult) ne.next();
					Object obj = sr.getObject();

					if (obj != null) {
						javaObject = obj;
						javaObjectCN = sr.getName();
					} else {
						userID = sr.getName();
						processAttributes(sr.getAttributes());
					}

					System.out.println("sr.getName() " + sr.getName());
				}// while
			}// if (ne != null)
		} catch (javax.naming.NamingException e) {
			e.printStackTrace();
		}
	}// processSearchResult

	private void processAttributes(Attributes attrs) {
		try {
			for (Enumeration<?> e = attrs.getAll(); e.hasMoreElements();) {
				Attribute attr = (Attribute) e.nextElement();
				if (attr.getID().equals("cn"))
					commonName = (String) attr.get();
				else if (attr.getID().equals("sn"))
					surName = (String) attr.get();
			}
		} catch (javax.naming.NamingException ne) {
			ne.printStackTrace();
		}
	}// processAttributes

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserID() {
		return userID;
	}

	public void setJavaObjectCN(String objectCN) {
		this.javaObjectCN = objectCN;
	}

	public String getJavaObjectCN() {
		return javaObjectCN;
	}

	public void setCommonName(String cn) {
		commonName = cn;
	}

	public void setSurName(String sn) {
		surName = sn;
	}

	public String getCommonName() {
		return commonName;
	}

	public String getSurName() {
		return surName;
	}

	public static void main(String[] args) {
		JavaToLDAPService javaLdapClient = new JavaToLDAPService();
		javaLdapClient.setPassword("secret");
		javaLdapClient.setUserID("Alice");

		// Instantiating a Java object.
		MessagingPreferences msgPreferences = new MessagingPreferences();

		// Setting a Java object.
		try {
			javaLdapClient.setJavaObject(msgPreferences);
		} catch (Exception e) {
			System.out.println("Not a serializable object");
			e.printStackTrace();
		}

		javaLdapClient.setJavaObjectCN("preferences");
		javaLdapClient.writeToServer();
		System.out.println("Entry stored in ApacheDS..");
	}// main

}