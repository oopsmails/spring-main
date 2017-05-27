package com.ziyang.designpattern.observer;

import java.util.Observable;
import java.util.Observer; /* this is Event Handler */

public class ResponseHandler implements Observer {
	private String resp;

	public void update(Observable obj, Object arg) {
		if (arg instanceof String) {
			resp = (String) arg;
			if("exit".equalsIgnoreCase(resp)) {
				System.out.println("\nExit ...");
				System.exit(0);
			}
			System.out.println("\nReceived Response: " + resp);
		}
	}
}
