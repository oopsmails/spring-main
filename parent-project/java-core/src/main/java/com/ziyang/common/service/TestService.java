package com.ziyang.common.service;

import com.ziyang.common.type.LogLevelType;

public interface TestService {
	public LogLevelType determineLogLevelType(int logLevel);
}
