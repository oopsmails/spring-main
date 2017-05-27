package com.ziyang.designpattern.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  zliu
 */
public class Loan implements Subject {
	private List<Observer> observers = new ArrayList<Observer>();
	/**
	 * @uml.property  name="type"
	 */
	private String type;
	/**
	 * @uml.property  name="interest"
	 */
	private float interest;
	/**
	 * @uml.property  name="bank"
	 */
	private String bank;

	public Loan(String type, float interest, String bank) {
		this.type = type;
		this.interest = interest;
		this.bank = bank;
	}

	/**
	 * @return
	 * @uml.property  name="interest"
	 */
	public float getInterest() {
		return interest;
	}

	/**
	 * @param interest
	 * @uml.property  name="interest"
	 */
	public void setInterest(float interest) {
		this.interest = interest;
		notifyObservers();
	}

	/**
	 * @return
	 * @uml.property  name="bank"
	 */
	public String getBank() {
		return this.bank;
	}

	/**
	 * @return
	 * @uml.property  name="type"
	 */
	public String getType() {
		return this.type;
	}

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);

	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);

	}

	@Override
	public void notifyObservers() {
		for (Observer ob : observers) {
			System.out.println("Notifying Observers on change in Loan interest rate");
			ob.update(this.interest);
		}
	}
}
