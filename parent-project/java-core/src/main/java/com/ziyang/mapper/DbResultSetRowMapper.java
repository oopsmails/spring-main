package com.ziyang.mapper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * Extending BeanPropertyRowMapper to support mapping hierarchical models
 * 
 * 
 * 
 * 
 * 
 */
public class DbResultSetRowMapper<T> extends BeanPropertyRowMapper<T> {
	public static final Predicate<ResultSet> ALL = t -> true;

	private ConversionService conversionService;

	private Map<String, PropertyDescriptor> mappedFields;
	private Map<String, DbResultSetRowMapper<?>> nestedMappers = new LinkedHashMap<>();

	Predicate<ResultSet> predicate;
	String[] groupColumns;

	// local state for one-to-many mapping
	ThreadLocal<CurrentGroupHolder<T>> currentGroupHolder = new ThreadLocal<CurrentGroupHolder<T>>() {
		@Override
		protected CurrentGroupHolder<T> initialValue() {
			return new CurrentGroupHolder<T>();
		}
	};

	public DbResultSetRowMapper(ConversionService cs) {
		this(null, cs, ALL);
	}

	public DbResultSetRowMapper(Class<T> mappedClass, ConversionService cs) {
		this(mappedClass, cs, ALL);
	}

	public DbResultSetRowMapper(Class<T> mappedClass, ConversionService cs, String... groupColumns) {
		this(mappedClass, cs, ALL, groupColumns);
	}

	public DbResultSetRowMapper(ConversionService cs, String... groupColumns) {
		this(null, cs, ALL, groupColumns);
	}

	public DbResultSetRowMapper(ConversionService cs, Predicate<ResultSet> predicate, String... groupColumns) {
		this(null, cs, predicate, groupColumns);
	}

	public DbResultSetRowMapper(Class<T> mappedClass, ConversionService cs, Predicate<ResultSet> predicate,
			String... groupColumns) {
		initialize(mappedClass);

		this.conversionService = cs;
		this.predicate = predicate;
		this.groupColumns = groupColumns;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void initialize(Class<T> mappedClass) {
		if (mappedClass == null) {
			ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
			Type type = parameterizedType.getActualTypeArguments()[0];
			Assert.isTrue(type instanceof Class, "Missing generic type");
			mappedClass = (Class<T>) type;
		}

		super.initialize(mappedClass);

		Field mff = ReflectionUtils.findField(BeanPropertyRowMapper.class, "mappedFields");
		ReflectionUtils.makeAccessible(mff);
		mappedFields = (Map<String, PropertyDescriptor>) ReflectionUtils.getField(mff, this);

		init();
	}

	protected void addFieldAlias(String field, String alias) {
		String key = field.toLowerCase();
		PropertyDescriptor pd = mappedFields.get(key);
		mappedFields.put(alias.toLowerCase(), pd);
	}

	protected void addMapper(String field, DbResultSetRowMapper<?> mapper) {
		nestedMappers.put(field, mapper);
	}

	protected void init() {
		// empty
	}

	@Override
	public T mapRow(ResultSet rs, int rowNumber) throws SQLException {
		if (!predicate.test(rs)) {
			return null;
		}

		T result;
		String[] currentColumns = getColumnValues(rs, groupColumns);

		CurrentGroupHolder<T> currentGroup = currentGroupHolder.get();
		if (isSameGroup(currentGroup.colums, currentColumns)) {
			result = currentGroup.result;
		} else {
			result = super.mapRow(rs, rowNumber);
			currentGroup.result = result;
			currentGroup.colums = currentColumns;
		}

		BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(result);
		for (Map.Entry<String, DbResultSetRowMapper<?>> e : nestedMappers.entrySet()) {
			PropertyDescriptor pd = mappedFields.get(e.getKey().toLowerCase());
			if (pd != null) {
				if (pd.getPropertyType().isAssignableFrom(List.class)) {
					@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>) bw.getPropertyValue(pd.getName());
					if (list == null) {
						list = new ArrayList<Object>();
						bw.setPropertyValue(pd.getName(), list);
					}
					Object row = e.getValue().mapRow(rs, rowNumber);
					list.add(row);
				} else {
					bw.setPropertyValue(pd.getName(), e.getValue().mapRow(rs, rowNumber));
				}

			}
		}

		return result;
	}

	public static boolean isSameGroup(String[] prev, String[] curr) {
		return prev == null || prev.length == 0 ? false : Arrays.equals(prev, curr);
	}

	public static String[] getColumnValues(ResultSet rs, String... columnNames) throws SQLException {
		if (columnNames == null || columnNames.length == 0) {
			return new String[0];
		}

		String[] values = new String[columnNames.length];
		for (int i = 0; i < columnNames.length; i++) {
			String name = columnNames[i];
			values[i] = rs.getString(name);
		}
		return values;
	}

	@Override
	protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
		return getResultSetValue(rs, index, pd.getPropertyType());
	}

	@Override
	protected final void initBeanWrapper(BeanWrapper bw) {
		((BeanWrapperImpl) bw).setConversionService(conversionService);
		initBeanWrapperImpl(bw);
	}

	protected void initBeanWrapperImpl(BeanWrapper bw) {
		// empty
	}

	/**
	 * COPIED FROM Spring framework in order to fix Sybase JDBC driver problem
	 * 
	 * Retrieve a JDBC column value from a ResultSet, using the specified value
	 * type.
	 * <p>
	 * Uses the specifically typed ResultSet accessor methods, falling back to
	 * {@link #getResultSetValue(java.sql.ResultSet, int)} for unknown types.
	 * <p>
	 * Note that the returned value may not be assignable to the specified
	 * required type, in case of an unknown type. Calling code needs to deal
	 * with this case appropriately, e.g. throwing a corresponding exception.
	 * 
	 * @param rs
	 *            is the ResultSet holding the data
	 * @param index
	 *            is the column index
	 * @param requiredType
	 *            the required value type (may be {@code null})
	 * @return the value object
	 * @throws SQLException
	 *             if thrown by the JDBC API
	 * 
	 * @see JdbcUtils#getResultSetValue(ResultSet, int, Class)
	 */
	// @UsesJava7  // guard optional use of JDBC 4.1 (safe with 1.6 due to getObjectWithTypeAvailable check)
	protected Object getResultSetValue(ResultSet rs, int index, Class<?> requiredType) throws SQLException {
		if (requiredType == null) {
			return getResultSetValue(rs, index);
		}

		Object value;

		// Explicitly extract typed value, as far as possible.
		if (String.class.equals(requiredType)) {
			return trimValue(rs.getString(index));
		} else if (boolean.class.equals(requiredType) || Boolean.class.equals(requiredType)) {
			value = rs.getBoolean(index);
		} else if (byte.class.equals(requiredType) || Byte.class.equals(requiredType)) {
			value = rs.getByte(index);
		} else if (short.class.equals(requiredType) || Short.class.equals(requiredType)) {
			value = rs.getShort(index);
		} else if (int.class.equals(requiredType) || Integer.class.equals(requiredType)) {
			value = rs.getInt(index);
		} else if (long.class.equals(requiredType) || Long.class.equals(requiredType)) {
			value = rs.getLong(index);
		} else if (float.class.equals(requiredType) || Float.class.equals(requiredType)) {
			value = rs.getFloat(index);
		} else if (double.class.equals(requiredType) || Double.class.equals(requiredType)
				|| Number.class.equals(requiredType)) {
			value = rs.getDouble(index);
		} else if (BigDecimal.class.equals(requiredType)) {
			return rs.getBigDecimal(index);
		} else if (java.sql.Date.class.equals(requiredType)) {
			return rs.getDate(index);
		} else if (java.sql.Time.class.equals(requiredType)) {
			return rs.getTime(index);
		} else if (java.sql.Timestamp.class.equals(requiredType) || java.util.Date.class.equals(requiredType)) {
			return rs.getTimestamp(index);
		} else if (byte[].class.equals(requiredType)) {
			return rs.getBytes(index);
		} else if (Blob.class.equals(requiredType)) {
			return rs.getBlob(index);
		} else if (Clob.class.equals(requiredType)) {
			return rs.getClob(index);
		} else {
			// The following doesn't work with Sybase JDBC driver
			//
			//	// Some unknown type desired -> rely on getObject.
			//	if (getObjectWithTypeAvailable) {
			//		try {
			//			return rs.getObject(index, requiredType);
			//		}
			//		catch (AbstractMethodError err) {
			//			logger.debug("JDBC driver does not implement JDBC 4.1 'getObject(int, Class)' method", err);
			//		}
			//		catch (SQLFeatureNotSupportedException ex) {
			//			logger.debug("JDBC driver does not support JDBC 4.1 'getObject(int, Class)' method", ex);
			//		}
			//		catch (SQLException ex) {
			//			logger.debug("JDBC driver has limited support for JDBC 4.1 'getObject(int, Class)' method", ex);
			//		}
			//	}

			// Fall back to getObject without type specification...
			return getResultSetValue(rs, index);
		}

		// Perform was-null check if necessary (for results that the JDBC driver returns as primitives).
		return rs.wasNull() ? null : value;
	}

	/**
	 * Retrieve a JDBC column value from a ResultSet, using the most appropriate
	 * value type. The returned value should be a detached value object, not
	 * having any ties to the active ResultSet: in particular, it should not be
	 * a Blob or Clob object but rather a byte array or String representation,
	 * respectively.
	 * <p>
	 * Uses the {@code getObject(index)} method, but includes additional "hacks"
	 * to get around Oracle 10g returning a non-standard object for its
	 * TIMESTAMP datatype and a {@code java.sql.Date} for DATE columns leaving
	 * out the time portion: These columns will explicitly be extracted as
	 * standard {@code java.sql.Timestamp} object.
	 * 
	 * @param rs
	 *            is the ResultSet holding the data
	 * @param index
	 *            is the column index
	 * @return the value object
	 * @throws SQLException
	 *             if thrown by the JDBC API
	 * @see java.sql.Blob
	 * @see java.sql.Clob
	 * @see java.sql.Timestamp
	 */
	protected Object getResultSetValue(ResultSet rs, int index) throws SQLException {
		Object obj = rs.getObject(index);

		String className = null;
		if (obj != null) {
			className = obj.getClass().getName();
		}

		if (obj instanceof Blob) {
			Blob blob = (Blob) obj;
			obj = blob.getBytes(1, (int) blob.length());
		} else if (obj instanceof Clob) {
			Clob clob = (Clob) obj;
			obj = clob.getSubString(1, (int) clob.length());
		} else if ("oracle.sql.TIMESTAMP".equals(className) || "oracle.sql.TIMESTAMPTZ".equals(className)) {
			obj = rs.getTimestamp(index);
		} else if (className != null && className.startsWith("oracle.sql.DATE")) {
			String metaDataClassName = rs.getMetaData().getColumnClassName(index);
			if ("java.sql.Timestamp".equals(metaDataClassName) || "oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
				obj = rs.getTimestamp(index);
			} else {
				obj = rs.getDate(index);
			}
		} else if (obj != null && obj instanceof java.sql.Date) {
			if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
				obj = rs.getTimestamp(index);
			}
		}
		return obj;
	}

	/**
	 * Trim String values
	 */
	protected String trimValue(String s) {
		return s == null ? null : s.trim();
	}

	public static abstract class RSPredicate implements Predicate<ResultSet> {
		@Override
		public final boolean test(ResultSet rs) {
			try {
				return rstest(rs);
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		}

		protected abstract boolean rstest(ResultSet rs) throws SQLException;
	}

	public static class ColumnPredicate extends RSPredicate {
		private final String columnName;
		private final String[] values;

		public ColumnPredicate(String columnName, String... values) {
			this.columnName = columnName;
			this.values = values;
		}

		@Override
		public boolean rstest(ResultSet rs) throws SQLException {
			return Functions.in(rs.getString(columnName), values);
		}
	}

	public void cleanState() {
		currentGroupHolder.remove();

		for (DbResultSetRowMapper<?> mapper : nestedMappers.values()) {
			mapper.cleanState();
		}
	}

	public static class CurrentGroupHolder<T> {
		T result;
		String[] colums;
	}

}
