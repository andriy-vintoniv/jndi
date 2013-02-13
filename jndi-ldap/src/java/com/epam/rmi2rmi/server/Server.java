package com.epam.rmi2rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Server implements ScriptEvaluator {
	public Server() {
	}

	public String sayHello() {
		return "Hello, world!";
	}

	public static void main(String args[]) {

		try {
			Server obj = new Server();
			ScriptEvaluator stub = (ScriptEvaluator) UnicastRemoteObject
					.exportObject(obj, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("Hello", stub);

			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public Double convertTemperature(String script, Integer temperature,
			String degree) throws RemoteException {
		Double result = null;
		ScriptEngine engine = getEngine("JavaScript");

		try {
			engine.eval(script);

			Invocable inv = (Invocable) engine;

			result = (Double) inv
					.invokeFunction("convert", temperature, degree);
		} catch (NoSuchMethodException e) {
			System.err
					.println("Method with given name or matching argument types cannot be found.");
			e.printStackTrace();
		} catch (ScriptException e) {
			System.err
					.println("An error occurrs during invocation of the method.");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Integer findWord(String script, String word, String text)
			throws RemoteException {
		Integer index = null;
		ScriptEngine engine = getEngine("JavaScript");

		try {
			engine.eval(script);

			Invocable invoker = (Invocable) engine;

			Object object = engine.get("object");

			index = (Integer) invoker.invokeMethod(object, word, text);

		} catch (ScriptException e) {
			System.err
					.println("An error occurrs during invocation of the method.");
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.err
					.println("Method with given name or matching argument types cannot be found.");
			e.printStackTrace();
		}
		return index;
	}

	private ScriptEngine getEngine(String name) {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName(name);
		return engine;
	}

}
