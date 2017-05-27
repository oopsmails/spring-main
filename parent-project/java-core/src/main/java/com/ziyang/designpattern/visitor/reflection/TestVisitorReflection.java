package com.ziyang.designpattern.visitor.reflection;

public class TestVisitorReflection {
	public static void main(String[] args) {
		try {
			Circle c = new Circle();
			Square s = new Square();
			Beagle b = new Beagle();

			Object[] elements = new Object[] { b, c, s };

			// "traverse" the elements visiting each
			MyVisitor v = new MyVisitor();
			for (int i = 0; i < elements.length; ++i) {
				v.visit(elements[i]);
			}
		} catch (Exception x) {
			System.out.println(x);
		}
	}
}
