package com.oopsmails.spring.standalone.domain;

/**
 * Created by liu on 2017-09-28.
 */
public class ConfigProperties {
    private String propertyName;
    private String propertyValue;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
}
