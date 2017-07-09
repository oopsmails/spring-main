package com.ziyang.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.context.MessageSource;

public class AppException extends RuntimeException {
	private static final long serialVersionUID = 7363635669820979876L;

	protected String requestToken = "";

	protected String initObjectClass;

	protected List<AppError> errors = new ArrayList<>();

	public AppException() {
		super();
	}

	public AppException(String message) {
		super(message);
	}

	public AppException(Throwable e) {
		super(e);
	}

	public AppException(String message, Throwable e) {
		super(message, e == null ? null : e.getCause() == null ? e : e.getCause());
	}

	public AppException(String message, Throwable e, List<AppError> errors) {
		super(message, e == null ? null : e.getCause() == null ? e : e.getCause());
		this.errors = errors;
	}

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	public String getInitObjectClass() {
		return initObjectClass;
	}

	public void setInitObjectClass(String initObjectClass) {
		this.initObjectClass = initObjectClass;
	}

	public List<AppError> getErrors() {
		return errors;
	}

	public void setErrors(List<AppError> errors) {
		this.errors = errors;
	}

	public void buildErrorMessage(MessageSource MessageSource) {
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
