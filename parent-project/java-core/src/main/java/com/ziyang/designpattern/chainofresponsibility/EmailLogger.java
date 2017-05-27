package com.ziyang.designpattern.chainofresponsibility;

public class EmailLogger extends Logger {

	public EmailLogger(int mask) {
		this.mask = mask;
	}

	protected void writeMessage(String msg) {
		System.out.println("Sending via email: " + msg);
	}
}
