package com.ziyang.mapper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * SpEL expressions, helper functions
 * 
 */
public abstract class Functions {

	/**
	 * Decode function similar to SQL DECODE:
	 * 
	 * #decode(expr, value1, result1, ..., valueN, resultN [, default])
	 */
	@SuppressWarnings("unchecked")
	public static <T> T decode(Object source, Object... args) {
		if (source != null) {
			Object val = source;
			if (source instanceof Enum<?>) {
				val = ((Enum<?>) source).name();
			}

			if (args[0] instanceof Integer) {
				if (source instanceof String) {
					val = Integer.valueOf((String) source);
				} else if (source instanceof Number) {
					val = Integer.valueOf(((Number) source).intValue());
				}
			}
			for (int i = 0; i < args.length - 1; i += 2) {
				if (val.equals(args[i])) {
					return (T) args[i + 1];
				}
			}
		}
		return (T) ((args.length % 2) == 0 ? null : args[args.length - 1]);
	}

	/**
	 * Decode function for value ranges:
	 * 
	 * <pre>
	 *  10 = Under $25,000
	 *  11 = $25,000 - $50,000
	 *	12 = $51,000 - $74,000
	 *	13 = $75,000 - $99,000
	 *	14 = $100,000 - $149,000
	 *	15 = $150,000 - $199,000
	 *	16 = $200,000 - $299,000
	 *	17 = $300,000 - $399,000
	 *	18 = $400,000 - $499,000
	 *	19 = $500,000 - $599,000
	 *	20 = $600,000 - $699,000
	 *	21 = $700,000 - $799,000
	 *	22 = $800,000 - $899,000
	 *	23 = $900,000 - $999,000
	 *	24 = Over $999,999
	 * </pre>
	 * 
	 * #decodeRange(expr, 25000, 10, 50000, 11, ..., 999000, 23, 24)
	 * #decodeRange(expr, range1, value1, range2, value2, ..., rangeN, valueN,
	 * valueOver)
	 */
	@SuppressWarnings("unchecked")
	public static <T> T decodeRange(Number source, Number... args) {
		if (source == null) {
			return null;
		}

		int val = source.intValue();

		for (int i = 0; i < args.length - 1; i += 2) {
			if (val < args[i].intValue()) {
				return (T) args[i + 1];
			}
		}
		return (T) args[args.length - 1];
	}

	public static final String DELETE = "-";

	/**
	 * A decode function for using SpEL inline map syntax:
	 * 
	 * #decodeMap(source, { a:'AA', b:'BB'}, 'CC')
	 * 
	 * @see https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/expressions.html#expressions-inline-maps
	 */
	@SuppressWarnings("unchecked")
	public static <T> T decodeMap(Object source, Map<Object, T> args, T def) {
		if (source != null) {
			if (DELETE.equals(source)) {
				return (T) "";
			}

			Object key = args.keySet().iterator().next();

			Object src = source;
			if (key instanceof Integer) {
				if (source instanceof String) {
					src = Integer.valueOf((String) source);
				} else if (source instanceof Number) {
					src = Integer.valueOf(((Number) source).intValue());
				}
			} else if (key instanceof String) {
				if (source instanceof Number) {
					src = ((Number) source).toString();
				} else if (source instanceof Enum<?>) {
					src = ((Enum<?>) source).name();
				}
			}
			T value = args.get(src);
			return value == null ? def : value;
		}
		return def;
	}

	/**
	 * Creates new LinkedHashMap from provided key/value pairs:
	 * 
	 * #map(key1, value1, ..., keyN, valueN)
	 */
	public static Map<Object, Object> map(Object... keyVal) {
		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
		for (int i = 0; i < keyVal.length; i += 2) {
			map.put(keyVal[i], keyVal[i + 1]);
		}
		return map;
	}

	/**
	 * Returns max len characters from the left
	 */
	public static String left(String s, int len) {
		return s == null || s.length() < len ? s : s.substring(0, len);
	}

	/**
	 * Returns max len characters from the right
	 */
	public static String right(String s, int len) {
		return s == null || s.length() < len ? s : s.substring(s.length() - len);
	}

	public static boolean in(String value, String... values) {
		if (value != null) {
			value = value.trim();
			for (String v : values) {
				if (v.equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
}
