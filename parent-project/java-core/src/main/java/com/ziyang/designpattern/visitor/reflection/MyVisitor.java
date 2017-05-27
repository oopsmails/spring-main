package com.ziyang.designpattern.visitor.reflection;

import java.lang.reflect.Method;

public class MyVisitor {
	public void visit(Beagle x) {
		System.out.println("Beagle:[" + x + "]");
	}

	public void visit(Circle x) {
		System.out.println("Circle:[" + x + "]");
	}

	public void visit(Square x) {
		System.out.println("Square:[" + x + "]");
	}

	public void visit(Object e) throws Exception {
		Method m = getClass().getMethod("visit", new Class[] { e.getClass() });
		m.invoke(this, new Object[] { e });
	}

}
