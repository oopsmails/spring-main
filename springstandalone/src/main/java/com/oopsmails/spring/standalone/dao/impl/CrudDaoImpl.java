package com.oopsmails.spring.standalone.dao.impl;

import com.oopsmails.spring.standalone.dao.CrudDao;
import com.oopsmails.spring.standalone.domain.ClientExample;
import com.oopsmails.spring.standalone.domain.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017-09-28.
 */
@Repository
public class CrudDaoImpl implements CrudDao {

    private static final Logger logger = LoggerFactory.getLogger(CrudDaoImpl.class);

    @Autowired
    private DataSource sqlServerDataSource;

    @Autowired
    @Qualifier("crudDaoInsertOneSql")
    private String insertOneSql;

    @Autowired
    @Qualifier("crudDaoSelectByIdSql")
    private String selectByIdSql;


    @Override
    public List<ConfigProperties> getAllConfigProperties() {
        List<ConfigProperties> result = new ArrayList<>();
        return result;
    }

    @Override
    @Transactional
    public void insertTable1(List<ClientExample> clientAccountDefunctSecurityList) {
        logger.debug("========================== test logger: in src ==========================");
        if (clientAccountDefunctSecurityList.isEmpty()) {
            return;
        }
        NamedParameterJdbcTemplate sqlServerJdbcTemplate = new NamedParameterJdbcTemplate(sqlServerDataSource);
        List<SqlParameterSource> sqlParameterSourceList = new ArrayList<>();
        clientAccountDefunctSecurityList.forEach((ClientExample clientExample) ->
                sqlParameterSourceList.add(mapToSqlParameterSource(clientExample))
        );

        sqlServerJdbcTemplate.batchUpdate(insertOneSql, sqlParameterSourceList.toArray(new SqlParameterSource[0]));
    }

    @Override
    public ClientExample getClientExampleById(String id) {
        NamedParameterJdbcTemplate sqlServerJdbcTemplate = new NamedParameterJdbcTemplate(sqlServerDataSource);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        return sqlServerJdbcTemplate.queryForObject(selectByIdSql, parameters, new BeanPropertyRowMapper<>(ClientExample.class));
    }


    private MapSqlParameterSource mapToSqlParameterSource(ClientExample clientExample) {
        MapSqlParameterSource result = new MapSqlParameterSource();

        result.addValue("clientId", clientExample.getClientId());
        result.addValue("accountNum", clientExample.getAccountNum());
        result.addValue("clientNum", clientExample.getClientNum());
        result.addValue("firstName", clientExample.getFirstName());
        result.addValue("lastName", clientExample.getLastName());
        result.addValue("title", clientExample.getTitle());
        result.addValue("type", clientExample.getType());
        result.addValue("createdTimestamp",
                clientExample.getCreatedTimestamp() == null ?
                        Timestamp.valueOf(LocalDateTime.now()) :
                        Timestamp.valueOf(clientExample.getCreatedTimestamp()),
                Types.TIMESTAMP);
        result.addValue(
                "lastUpdatedTimestamp",
                clientExample.getLastUpdatedTimestamp() == null ?
                        Timestamp.valueOf(LocalDateTime.now()) :
                        Timestamp.valueOf(clientExample.getLastUpdatedTimestamp()),
                Types.TIMESTAMP);

        return result;
    }

    public DataSource getSqlServerDataSource() {
        return sqlServerDataSource;
    }

    public void setSqlServerDataSource(DataSource sqlServerDataSource) {
        this.sqlServerDataSource = sqlServerDataSource;
    }

    public String getInsertOneSql() {
        return insertOneSql;
    }

    public void setInsertOneSql(String insertOneSql) {
        this.insertOneSql = insertOneSql;
    }

    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    public void setSelectByIdSql(String selectByIdSql) {
        this.selectByIdSql = selectByIdSql;
    }
}
