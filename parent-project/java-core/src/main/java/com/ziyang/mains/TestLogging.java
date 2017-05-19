package com.ziyang.mains;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestLogging {
	private static final Logger logger = LoggerFactory.getLogger(TestLogging.class);

	ApplicationContext xmlContext;

	private void init() {
		xmlContext = new ClassPathXmlApplicationContext("appContext-java-core-test.xml");
	}

	public static void main(String[] args) throws Exception {
		logger.info("###############################################################################");
		logger.info("logging started: " + Calendar.getInstance().getTime());
		logger.info("###############################################################################");

		try {
			TestLogging testLogging = new TestLogging();
			logger.debug("logging debugging info: init() starting .......... ");
			testLogging.init();
			logger.debug("logging debugging info: init() done!");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			logger.error(e.getMessage(), e);
			Throwable cause = e.getCause();
			while (cause != null) {
				System.out.println(cause.getMessage());
				logger.error(cause.getMessage(), cause);
				cause = cause.getCause();
			}
			System.exit(1);
		}

	}
}
