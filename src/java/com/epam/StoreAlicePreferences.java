package com.epam;

import java.util.Properties;

import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class StoreAlicePreferences {

	public StoreAlicePreferences() {
		try {
			// ------------------------------------------
			// Step1: Setting up JNDI properties for ApacheDS
			// ------------------------------------------
			System.out.println("fasdfasd");
			Properties properties = new Properties();
			properties.load(StoreAlicePreferences.class
					.getResourceAsStream("/ApacheDS.properties"));
			properties
					.setProperty("java.naming.security.credentials", "secret");

			// ------------------------------------------
			// Step2: Fetching a DirContext object
			// ------------------------------------------
			DirContext ctx = new InitialDirContext(properties);

			// ------------------------------------------
			// Step3: Instantiate a Java object
			// ------------------------------------------
			MessagingPreferences preferences = new MessagingPreferences();

			// ------------------------------------------
			// Step4: Store the Java object in ApacheDS
			// ------------------------------------------
			String bindContext = "cn=preferences,ou=alice,ou=users";
			ctx.bind(bindContext, preferences);
		} catch (Exception e) {
			System.out.println("Operation failed: " + e);
		}
	}

	public static void main(String[] args) {
		StoreAlicePreferences storeAlicePref = new StoreAlicePreferences();
	}

}
