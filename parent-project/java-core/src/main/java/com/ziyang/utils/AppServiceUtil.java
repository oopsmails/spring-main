package com.ziyang.utils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ziyang.entity.CitizenType;

public class AppServiceUtil {
	private static final Logger logger = LoggerFactory.getLogger(AppServiceUtil.class);

	public static final ThreadLocal<SimpleDateFormat> DATE_FORMAT_YYYY_MM_DD = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	public static final Map<String, String> SAMPLE_MAP;

	static {
		Map<String, String> aMap = new HashMap<>();
		aMap.put("1", "AAA");
		aMap.put("2", "BBB");

		SAMPLE_MAP = Collections.unmodifiableMap(aMap);
	}

	public static final Set<String> SAMPLE_SET = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(//
			"ddd", //
			"aaa" //

	)));

	public static <T extends Enum<T>> String findEnumString(Class<T> type, T t) {
		return findEnumString(type, t, null);
	}

	public static <T extends Enum<T>> String findEnumString(Class<T> type, T t, String defaultValue) {
		Assert.isNotNull(type, "type is null");
		if (t == null) {
			return defaultValue;
		}

		return t.name();
	}

	public static <T extends Enum<T>> T findEnumValue(Class<T> type, String name) {
		return findEnumValue(type, name, null);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>> T findEnumValue(Class<T> type, String name, T defaultValue) {
		if (name == null)
			return defaultValue;
		try {
			// NOTE: have to call fromValue() to handle correctly Enums generated from our schema
			String v = name.trim();
			try {
				return (T) type.getMethod("fromValue", String.class).invoke(null, v);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// fallback to normal enum processing
				return Enum.valueOf(type, v);
			}
		} catch (IllegalArgumentException e) {
			logger.warn("Invalid value [{}] for type {}", name, type.getName());
			return defaultValue;
		}
	}

	public static CitizenType getCitizenType(final String typeStr) {
		return findEnumValue(CitizenType.class, typeStr, CitizenType.CAN);
	}

	/**
	 * This method returns a String which is a hash of the passed in input
	 * String.
	 * 
	 * @param input
	 * @return String hash String of the input
	 * @throws NoSuchAlgorithmException
	 */
	public static String makeSHA1Hash(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.reset();
		md.update(input.getBytes(StandardCharsets.UTF_8));
		return encodeHexString(md.digest());
	}

	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	private static String encodeHexString(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	/**
	 * Calculate age.
	 * 
	 * @param dob
	 * @return
	 */
	public static int calculateAge(Date dob) {
		if (dob == null) {
			return -1;
		}

		LocalDate today = LocalDate.now();
		LocalDate birthday = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return (int) ChronoUnit.YEARS.between(birthday, today);
	}

	/**
	 * returning true if the passed in two strings are identical, false,
	 * otherwise.
	 * 
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public static boolean compareIfSameString(String oldValue, String newValue) {
		if (isReallyEmptyOrNull(oldValue) && isReallyEmptyOrNull(newValue)) {
			return true;
		}

		if (isReallyEmptyOrNull(oldValue) || isReallyEmptyOrNull(newValue)) {
			return false;
		}

		return Objects.equals(oldValue, newValue);
	}

	public static boolean isReallyEmptyOrNull(String value) {
		return value == null || "".equals(value.trim());
	}

	/**
	 * Comparing two Enum values.
	 * 
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public static <E extends Enum<E>> boolean compareIfSameEnum(E oldValue, E newValue) {
		return Objects.equals(oldValue, newValue);
	}

	/**
	 * Comparing Date values. Note,
	 * 
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public static boolean compareIfSameDateOnly(Date oldValue, Date newValue) {
		return (compareSameDateOnly(oldValue, newValue) == 0);
	}

	public static int compareSameDateOnly(Date oldValue, Date newValue) {
		if (oldValue == null && newValue == null) {
			return 0;
		} else if (oldValue == null && newValue != null) {
			return -1;
		} else if (oldValue != null && newValue == null) {
			return 1;
		}
		return zeroTimeDate(newValue).compareTo(zeroTimeDate(oldValue));
	}

	public static int compareIfEquals(Object oldValue, Object newValue) {
		if (oldValue == null && newValue == null) {
			return 0;
		} else if (oldValue == null && newValue != null) {
			return -1;
		} else if (oldValue != null && newValue == null) {
			return 1;
		}
		return Objects.equals(oldValue, newValue) ? 0 : 1;
	}

	public static int compareToInteger(Integer oldValue, Integer newValue) {
		if (oldValue == null && newValue == null) {
			return 0;
		} else if (oldValue == null && newValue != null) {
			return -1;
		} else if (oldValue != null && newValue == null) {
			return 1;
		}
		return oldValue.intValue() == newValue.intValue() ? 0 : 1;
	}

	public static int compareToBigDecimal(BigDecimal oldValue, BigDecimal newValue) {
		if (oldValue == null && newValue == null) {
			return 0;
		} else if (oldValue == null && newValue != null) {
			return -1;
		} else if (oldValue != null && newValue == null) {
			return 1;
		}
		return oldValue.compareTo(newValue);
	}

	public static String getObjectString(Object object) {
		if (object == null) {
			return null;
		}

		return object.toString();
	}

	/**
	 * Returning a date only Date object (with hh:mm:ss.SSS as 00:00:00.000).
	 * 
	 * @param date
	 * @return
	 */
	public static Date zeroTimeDate(final Date date) {
		Date result = date;
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(result);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		result = calendar.getTime();

		return result;
	}

}
