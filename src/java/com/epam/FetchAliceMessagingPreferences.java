package com.epam;

import java.util.Properties;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class FetchAliceMessagingPreferences {

	public static void main(String[] args) {
		FetchAliceMessagingPreferences searchAlicePreferences = new FetchAliceMessagingPreferences();
	}

	public FetchAliceMessagingPreferences() {
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
			String searchContext = "cn=Tenant,ou=groups";

			// --------------------------------------------
			// Step4: Creating search attributes for Alice
			// --------------------------------------------
			Attribute cn = new BasicAttribute("cn");
			Attribute javaClassNames = new BasicAttribute("javaClassNames");

			// putting attribute values
			cn.add("Andriy");
			javaClassNames.add("com.epam.model.User");

			// Instantiate an Attributes object and put search attributes in it
			Attributes attrs = new BasicAttributes(true);
			attrs.put(cn);
			attrs.put(javaClassNames);

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
					Object alice = sr.getObject();

					// ---------------------------------------------
					// Step9: Setting a new search context
					// ---------------------------------------------
					searchContext = entryRDN + "," + searchContext;

					// ---------------------------------------------
					// Step10: Creating search controls
					// ---------------------------------------------
					SearchControls ctls = new SearchControls();
					ctls.setReturningObjFlag(true);
					ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

					// ---------------------------------------------
					// Step11: Creating filter
					// ---------------------------------------------
					String filter = "(|(javaClassName=MessagingPreferences))";

					// ------------------------------------------
					// Step12: Exeucting search
					// ------------------------------------------
					NamingEnumeration<SearchResult> ne1 = ctx.search(
							searchContext, filter, ctls);

					if (ne != null) {
						// Step13: Iterating through SearchResults
						while (ne1.hasMore()) {
							// Step14: Getting individual SearchResult object
							SearchResult sr1 = ne1.next();

							// Step15: Getting preferences object
							Object obj = sr1.getObject();

							if (obj != null) {
								if (obj instanceof MessagingPreferences) {
									MessagingPreferences pref = (MessagingPreferences) obj;
									System.out
											.println("MessagingPreference object found "
													+ obj);
								}
								// else if (obj instanceof ShippingPreferences)
								// {
								// ShippingPreferences pref =
								// (ShippingPreferences) obj;
								// }
							}
						}// while(
					}// if
				}// while
			}// if (ne != null)
		} catch (Exception e) {
			System.out.println("Operation failed: " + e);
		}
	}
}
