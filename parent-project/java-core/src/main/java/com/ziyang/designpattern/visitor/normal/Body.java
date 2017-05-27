package com.ziyang.designpattern.visitor.normal;

public class Body implements CarElement {
    public void accept(CarElementVisitor visitor) {
        visitor.visit(this);
    }
}
