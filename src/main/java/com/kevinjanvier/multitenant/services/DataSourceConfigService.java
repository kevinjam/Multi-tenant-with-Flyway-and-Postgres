package com.kevinjanvier.multitenant.services;

import com.kevinjanvier.multitenant.config.DataSourceConfig;
import com.kevinjanvier.multitenant.repository.DataSourceConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataSourceConfigService {

    private final DataSourceConfigRepository repository;
    private final TenantService tenantService;

    @Transactional
    public DataSourceConfig addDataSource(DataSourceConfig dataSourceConfig, String xtenant) {
        log.info("X tenant id {} ", xtenant);
        DataSourceConfig config = new DataSourceConfig();
        config.setName(xtenant);
        config.setDriverClassName("org.postgresql.Driver");
        config.setUrl("jdbc:postgresql://localhost:5432/usercoding?currentSchema="+xtenant+"&ApplicationName=usercoding");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setInitialize(true);
        log.info("datasource is now {} ", config);
        return repository.save(config);
    }

    public Connection connect(String host, int port, String database, String user, String password, String schemaName) throws SQLException {
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + database+"?currentSchema="+schemaName;
        log.info("Url Connection name {} ", url);
        return DriverManager.getConnection(url, user, password);
    }

    public void createSchema(Connection conn, String schemaName) {
        try {
            String sql = "CREATE SCHEMA IF NOT EXISTS "+schemaName;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException ex) {
            log.info("exception {}", ex);
            ex.printStackTrace();
        }

    }

    public void createTable(Connection connection, String schemaName, String tableName) {
        try {

            log.info("Start creating the table");
            Statement statement = connection.createStatement();
            String tableSql = "CREATE TABLE IF NOT EXISTS "+schemaName+".tbl_users(id bigint, fullName varchar(200),email varchar(200),phone varchar(200),tenantId varchar(200))";
            String sql2 = "CREATE SEQUENCE "+schemaName+".hibernate_sequence START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1";
            log.info("sql for create table \n {} ", tableSql);
            statement.executeUpdate(tableSql);
            log.info("sql for create sequence \n {} ", sql2);
            statement.executeUpdate(sql2);
            statement.close();
        } catch (SQLException ex) {
            log.info("SQL Exception {}", ex);
        }
    }
}
