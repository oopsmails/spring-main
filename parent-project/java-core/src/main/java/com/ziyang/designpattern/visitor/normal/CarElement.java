package com.ziyang.designpattern.visitor.normal;

public interface CarElement {
	void accept(CarElementVisitor visitor); // CarElements have to provide accept().
}
