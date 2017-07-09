package com.ziyang.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import com.ziyang.mapper.DbResultSetRowMapper.CurrentGroupHolder;

/**
 * A custom {@link RowMapperResultSetExtractor} used to support grouping
 * {@link KycRowMapper}
 * 
 */
public class DbResultSetRowMapperExtractor<T> extends RowMapperResultSetExtractor<T> {

	private DbResultSetRowMapper<T> dbResultSetRowMapper;

	public DbResultSetRowMapperExtractor(DbResultSetRowMapper<T> DbResultSetRowMapper) {
		super(DbResultSetRowMapper);
		this.dbResultSetRowMapper = DbResultSetRowMapper;
	}

	@Override
	public List<T> extractData(ResultSet rs) throws SQLException {
		try {
			List<T> results = new ArrayList<T>();
			int rowNum = 0;
			while (rs.next()) {
				String[] currentColumns = DbResultSetRowMapper.getColumnValues(rs, dbResultSetRowMapper.groupColumns);
				CurrentGroupHolder<T> currentGroup = dbResultSetRowMapper.currentGroupHolder.get();
				if (DbResultSetRowMapper.isSameGroup(currentGroup.colums, currentColumns)) {
					dbResultSetRowMapper.mapRow(rs, rowNum++);
				} else {
					results.add(dbResultSetRowMapper.mapRow(rs, rowNum++));
				}
			}
			return results;

		} finally {
			dbResultSetRowMapper.cleanState();
		}
	}

}
