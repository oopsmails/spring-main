package com.ziyang.designpattern.visitor.normal;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class VisitorDemo {
	public static void main(String[] args) {
        CarElement car = new Car();
        car.accept(new CarElementPrintVisitor());
        car.accept(new CarElementDoVisitor());
        
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        System.out.println(compiler == null ? "tools.jar not presenting!!!" : "tools.jar is there.");
    }
}
