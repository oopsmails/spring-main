package com.ziyang.common.service;

import org.springframework.stereotype.Service;
//import javax.ejb.Stateless;

import com.ziyang.common.type.LogLevelType;

@Service(value="TestService")
//@Stateless(mappedName="TestService") //cannot be used because no web container
public class TestServiceImpl implements TestService {

	@Override
	public LogLevelType determineLogLevelType(int logLevel) {
		return LogLevelType.getEnum(logLevel);
	}

}
