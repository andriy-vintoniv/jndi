package com.epam;

import java.rmi.MarshalledObject;
import java.util.Properties;

import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class StoreAlicePreferencesInMarshalledForm {

	public static void main(String[] args) {
		StoreAlicePreferencesInMarshalledForm storeAlicePref = new StoreAlicePreferencesInMarshalledForm();
	}

	public StoreAlicePreferencesInMarshalledForm() {
		try {

			// ------------------------------------------
			// Step1: Setting up JNDI properties for ApacheDS
			// ------------------------------------------
			Properties properties = new Properties();
			properties.load(StoreAlicePreferences.class
					.getResourceAsStream("/ApacheDS.properties"));
			properties
					.setProperty("java.naming.security.credentials", "secret");

			// ------------------------------------------
			// Step2: Fetching a DirContext object
			// ------------------------------------------
			DirContext ctx = new InitialDirContext(properties);

			// ---------------------------------------------
			// Step3: Instantiate a Java Object
			// ---------------------------------------------
			MessagingPreferences preferences = new MessagingPreferences();
			MarshalledObject<MessagingPreferences> mObj = new MarshalledObject<MessagingPreferences>(
					preferences);

			// --------------------------------------------
			// Step4: Binding Preferences object for Alice entry
			// --------------------------------------------
			String bindContext = "cn=marshalledPreferences,ou=alice,ou=users";
			ctx.bind(bindContext, mObj);

			System.out
					.println("Alice marshalled preferences successfully stored in directory server");
		} catch (Exception e) {
			System.out.println("Operation failed: " + e);
		}

	}
}