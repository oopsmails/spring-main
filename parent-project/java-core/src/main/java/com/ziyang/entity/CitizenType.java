package com.ziyang.entity;

public enum CitizenType {
	CAN,
    US;

    public String value() {
        return name();
    }

    public static CitizenType fromValue(String v) {
        return valueOf(v);
    }
}
