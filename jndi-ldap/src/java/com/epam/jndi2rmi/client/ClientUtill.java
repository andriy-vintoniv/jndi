package com.epam.jndi2rmi.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;

import com.epam.rmi2rmi.server.ScriptEvaluator;

public class ClientUtill {

	public void testStub(ScriptEvaluator scriptEvaluator) {
		String temperatureScript = getText("resources/temperature-converter.js");
		String findWordScript = getText("resources/word-finder.js");

		try {
			Double temperature;
			temperature = scriptEvaluator.convertTemperature(temperatureScript,
					20, "C");
			System.out.println(temperature);

			String word = "text";
			Integer index = scriptEvaluator.findWord(findWordScript, word,
					findWordScript);

			System.out.println("Index of word '" + word + "' in text is "
					+ index);
		} catch (RemoteException e) {
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
