package com.epam.jndi2rmi.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
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
			ScriptEvaluator hello = (ScriptEvaluator) dirContext
					.lookup("Hello");

			String script = getText("resources/temperature-converter.js");

			Double temperature = hello.convertTemperature(script, 20, "C");
			System.out.println(temperature);

			String word = "text";
			Integer index = hello.findWord(script, word, script);

			System.out.println("Index of word '" + word + "' in text is "
					+ index);

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static String getText(String fileName) {
		BufferedReader br = null;
		String script = "";
		try {
			br = new BufferedReader(new FileReader(fileName));
			StringBuilder sb = new StringBuilder();
			String line;
			line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			script = sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return script;
	}
}
