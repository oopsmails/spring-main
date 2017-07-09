package com.ziyang.common;

import java.io.Serializable;

public class AppError implements Serializable, Cloneable {

	private final static long serialVersionUID = 1L;
	protected String errorCode;
	protected String errorMsg;
	protected String errorLevel;
	protected String errorInfo;
	protected String initObj;
	protected String propertyName;
	protected String propertyValue;
	protected String messageTemplate;
	protected String path;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String value) {
		this.errorCode = value;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String value) {
		this.errorMsg = value;
	}

	public String getErrorLevel() {
		return errorLevel;
	}

	public void setErrorLevel(String value) {
		this.errorLevel = value;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String value) {
		this.errorInfo = value;
	}

	public String getInitObj() {
		return initObj;
	}

	public void setInitObj(String value) {
		this.initObj = value;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String value) {
		this.propertyName = value;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String value) {
		this.propertyValue = value;
	}

	public String getMessageTemplate() {
		return messageTemplate;
	}

	public void setMessageTemplate(String value) {
		this.messageTemplate = value;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String value) {
		this.path = value;
	}

	public Object createNewInstance() {
		return new AppError();
	}

	public interface Group {

	}

}
