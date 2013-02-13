package com.epam.jndi2rmi.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import com.epam.rmi2rmi.server.ScriptEvaluator;

public class Client {
	public static void main(String[] args) {

		Hashtable<String, Object> env = new Hashtable<String, Object>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.rmi.registry.RegistryContextFactory");
		env.put(Context.PROVIDER_URL, "rmi://localhost:1099");

		try {
			DirContext dirContext = new InitialDirContext(env);
			ScriptEvaluator scriptEvaluator = (ScriptEvaluator) dirContext
					.lookup("ScriptEvaluator");

			ClientUtill clientUtill = new ClientUtill();
			clientUtill.testStub(scriptEvaluator);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
