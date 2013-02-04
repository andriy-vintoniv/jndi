package com.epam;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class StoreBobPreferences {

	public static void main(String[] args) {
		StoreBobPreferences storeBobPref = new StoreBobPreferences();
	}

	public StoreBobPreferences() {
		try {

			// ------------------------------------------
			// Step1: Setting up JNDI properties for ApacheDS
			// ------------------------------------------
			Properties properties = new Properties();
			properties.load(StoreAlicePreferences.class
					.getResourceAsStream("/ApacheDS.properties"));
			properties
					.setProperty("java.naming.security.credentials", "secret");

			// ----------------------------------------------
			// Step2: Fetching a DirContext object
			// ----------------------------------------------
			DirContext ctx = new InitialDirContext(properties);

			// ----------------------------------------------
			// Step3A: Instantiate a Java Object
			// ----------------------------------------------
			MessagingPreferences preferences = new MessagingPreferences();

			// ----------------------------------------------
			// Step3B: Instantiate BasicAttribute object
			// ----------------------------------------------
			Attribute objclass = new BasicAttribute("objectClass");

			// ----------------------------------------------
			// Step3C: Supply value of attribute
			// ----------------------------------------------
			objclass.add("person");

			// ----------------------------------------------
			// Step3D: Put the attribute in attribute collection
			// ----------------------------------------------
			Attributes attrs = new BasicAttributes(true);
			attrs.put(objclass);

			// ----------------------------------------------
			// Step4: Store the Java object in ApacheDS
			// ----------------------------------------------
			String bindContext = "ou=Bob,ou=users";
			ctx.bind(bindContext, preferences, attrs);

			System.out.println("Storing operation successed");
		} catch (Exception e) {
			System.out.println("Operation failed: " + e);
		}
	}
}