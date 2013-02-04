package com.epam;

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

public class SearchForAliceByCN {

	public static void main(String[] args) {
		SearchForAliceByCN searchAlice = new SearchForAliceByCN();
	}

	public SearchForAliceByCN() {
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
			Attribute cn = new BasicAttribute("cn");
			Attribute objclass = new BasicAttribute("objectClass");

			// putting attribute values
			cn.add("SecondAlice_CN");
			objclass.add("person");

			// Instantiate an Attributes object and put search attributes in it
			Attributes attrs = new BasicAttributes(true);
			attrs.put(cn);
			attrs.put(objclass);

			// ------------------------------------------
			// Step5: Executing search
			// ------------------------------------------
			NamingEnumeration<?> ne = ctx.search(searchContext, attrs);

			if (ne != null) {
				// Step 6: Iterating through SearchResults
				while (ne.hasMore()) {
					// Step 7: Getting individual SearchResult object
					SearchResult sr = (SearchResult) ne.next();

					// Step 8:
					String entryRDN = sr.getName();

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
						}// for()
					}// if (srAttrs)
				}
			}// if (ne != null)

		} catch (Exception e) {
			System.out.println("Operation failed: " + e);
		}
	}
}