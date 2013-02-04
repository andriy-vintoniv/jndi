package com.epam;

import java.util.Properties;

import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import com.epam.model.User;

public class StoreAlicePreferences {

	public StoreAlicePreferences() {
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

			// ------------------------------------------
			// Step3: Instantiate a Java object
			// ------------------------------------------
			User user = new User();
			user.setId(1);
			user.setBirthday("25/09/1989");
			user.setEmail("av.ukr.net");
			user.setName("Andriy");
			user.setPassword("password1");

			// ------------------------------------------
			// Step4: Store the Java object in ApacheDS
			// ------------------------------------------
			String bindContext = "cn=Andriy,cn=Tenant,ou=groups";
			ctx.bind(bindContext, user);
		} catch (Exception e) {
			System.out.println("Operation failed: " + e);
		}
	}

	public static void main(String[] args) {
		StoreAlicePreferences storeAlicePref = new StoreAlicePreferences();
	}

}
