package com.epam.rmi2rmi.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.epam.jndi2rmi.client.ClientUtill;
import com.epam.rmi2rmi.server.ScriptEvaluator;

public class Client {
	private Client() {
	}

	public static void main(String[] args) {

		String host = (args.length < 1) ? null : args[0];
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			ScriptEvaluator scriptEvaluator = (ScriptEvaluator) registry
					.lookup("ScriptEvaluator");

			ClientUtill clientUtill = new ClientUtill();
			clientUtill.testStub(scriptEvaluator);

		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}