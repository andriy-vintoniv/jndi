package com.epam.jndi2ldap.service;

import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class ConnectionService {

	public final static String BASE_CONTEXT = "cn=Tenant,ou=groups";

	private Properties properties = new Properties();

	public ConnectionService() {
		init();
	}

	private void init() {

		try {
			properties.load(ConnectionService.class
					.getResourceAsStream("/ApacheDS.properties"));
			properties
					.setProperty("java.naming.security.credentials", "secret");

		} catch (Exception e) {
			System.out.println("Operation failed: " + e);
		}
	}

	public DirContext connect() {
		DirContext dirContext = null;

		try {
			// Fetch the directory context.
			dirContext = new InitialDirContext(properties);
			System.out.println(">>> Connected to ApacheDS.");
		} catch (NamingException e) {
			System.out
					.println("Getting initial context operation failed: " + e);
		}
		return dirContext;
	}

	public void close(DirContext dirContext) {
		try {
			// Close the directory context.
			dirContext.close();
			System.out.println(">>> Closing connection to ApacheDS.");
		} catch (NamingException e) {
			System.out
					.println("Closing initial context operation failed: " + e);
		}
	}

}
