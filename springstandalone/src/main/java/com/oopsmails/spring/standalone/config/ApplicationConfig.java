package com.oopsmails.spring.standalone.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan({"com.oopsmails.spring.standalone" //
})
@Import({SchedulerConfig.class})
public class ApplicationConfig {

    @Bean
    public DataSource sqlServerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
        dataSource.setUrl("jdbc:jtds:sqlserver://123.234.345.456:1433;DatabaseName=db_abc;");
        dataSource.setUsername("changeme");
        dataSource.setPassword("changeme");

        return dataSource;
    }

    @Bean
    public JdbcTemplate sqlServerJdbcTemplate() {
        return new JdbcTemplate(sqlServerDataSource());
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager sqlServerDataSourceTransactionManager = new DataSourceTransactionManager();
        sqlServerDataSourceTransactionManager.setDataSource(sqlServerDataSource());
        return sqlServerDataSourceTransactionManager;
    }


    @Bean(name = "crudDaoInsertOneSql")
    public String crudDaoInsertOneSql() {
        return "INSERT INTO table1 ( " +
                "clientId, accountNum, clientNum, "
                + "firstName, lastName, title, "
                + "type, createdTimestamp, lastUpdatedTimestamp) "
                + "VALUES (:clientId, :accountNum, :clientNum, "
                + ":firstName, :lastName, :title, "
                + ":type, :createdTimestamp, getDate())";
    }

    @Bean(name = "crudDaoSelectByIdSql")
    public String crudDaoSelectByIdSql() {
        return "SELECT " +
                "clientId, accountNum, clientNum, "
                + "firstName, lastName, title, "
                + "type, createdTimestamp, lastUpdatedTimestamp) "
                + " FROM dbo.table1 "
                + " WHERE id = :clientId";
    }

}
