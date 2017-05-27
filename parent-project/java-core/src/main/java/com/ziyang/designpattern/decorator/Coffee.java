package com.ziyang.designpattern.decorator;

//The Coffee Interface defines the functionality of Coffee implemented by decorator
public interface Coffee {
	public double getCost(); // returns the cost of the coffee

	public String getIngredients(); // returns the ingredients of the coffee
}
