package com.epam.rmi2rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ScriptEvaluator extends Remote {

	/**
	 * Invokes a specific script function
	 * 
	 * @param script
	 * @param temperature
	 * @param degree
	 * @return
	 * @throws RemoteException
	 */
	Double convertTemperature(String script, Integer temperature, String degree)
			throws RemoteException;

	/**
	 * Invokes a script method on a script object
	 * 
	 * @param script
	 * @param word
	 * @param text
	 * @return
	 * @throws RemoteException
	 */
	Integer findWord(String script, String word, String text)
			throws RemoteException;
}
