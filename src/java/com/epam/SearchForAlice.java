package com.epam;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchResult;

public class SearchForAlice {

	public static void main(String[] args) {
		SearchForAlice searchAlice = new SearchForAlice();
	}

	public SearchForAlice() {
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
			// Step3: Setting search context
			// ---------------------------------------------
			String searchContext = "ou=users";

			// --------------------------------------------
			// Step4: Creating search attributes for Alice
			// --------------------------------------------
			Attribute ou = new BasicAttribute("ou");
			Attribute objclass = new BasicAttribute("objectClass");

			// adding attribute values
			ou.add("Alice");
			objclass.add("person");

			// Instantiate Attributes object and put search attributes in it.
			Attributes attrs = new BasicAttributes(true);
			attrs.put(ou);
			attrs.put(objclass);

			// ------------------------------------------
			// Step5: Exeucting search
			// ------------------------------------------
			NamingEnumeration<?> ne = ctx.search(searchContext, attrs);

			if (ne != null) {
				// Step 6: Iterating through SearchResults
				while (ne.hasMore()) {
					// Step 7: Getting individual SearchResult object
					SearchResult sr = (SearchResult) ne.next();

					// Step 8:
					String entryRDN = sr.getName();
					System.out
							.println("RDN of the Searched entry: " + entryRDN);

					// Step 9:
					Attributes srAttrs = sr.getAttributes();

					if (srAttrs != null) {
						// Step 10:
						for (Enumeration e = attrs.getAll(); e
								.hasMoreElements();) {
							Attribute attr = (Attribute) e.nextElement();

							// Step 11:
							String attrID = attr.getID();
							System.out.println("Attribute Name: " + attrID);
							System.out.println("Attribute Value(s):");

							NamingEnumeration e1 = attr.getAll();
							while (e1.hasMore())
								System.out.println("\t\t" + e1.nextElement());
						}
					}
				}
			}// if (ne != null)

		} catch (Exception e) {
			System.out.println("Operation failed: " + e);
		}
	}
}
