package com.ziyang.utils;

import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General purpose assertion utility. 
 * Throws properly formatted IllegalStateException and logs on ERROR level upon assertion failure
 */
public final class Assert {
    private static final Logger logger = LoggerFactory.getLogger(Assert.class);

    private Assert() {
    }

    public static void isNotNull(Object obj, String message, Object... params) throws IllegalStateException {
        if (obj == null) {
            fail(message, params);
        }
    }

    public static void isTrue(boolean condition, String message, Object... params) throws IllegalStateException {
        if (!condition) {
            fail(message, params);
        }
    }

    public static void isFalse(boolean condition, String message, Object... params) throws IllegalStateException {
        if (condition) {
            fail(message, params);
        }
    }

    public static <T> T fail(String format, Object... params) throws IllegalStateException {
        try {
            fail(String.format(format, params));
        } 
        catch (IllegalFormatException e) {
            logger.warn("Invalid format string", e);
            fail(format + " " + Arrays.toString(params));
        }
        return null;
    }

    public static <T> T fail(String message) throws IllegalStateException {
        throw exception(message);
    }

    public static IllegalStateException exception(String message) {
        IllegalStateException assertionFailedError = new IllegalStateException(message);
        StackTraceElement[] stackTrace = assertionFailedError.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            StackTraceElement el = stackTrace[i];
            if (!Assert.class.getName().equals(el.getClassName())) {
                List<StackTraceElement> trace = Arrays.asList(stackTrace).subList(i, stackTrace.length);
                assertionFailedError.setStackTrace(trace.toArray(new StackTraceElement[trace.size()]));
                logger.error("ASSERTION FAILED in {}({}:{}) - {}", el.getClassName(), el.getFileName(), el.getLineNumber(), message);
                break;
            }
        }
        return assertionFailedError;
    }

}