package com.kevinjanvier.multitenant.impl;

import com.kevinjanvier.multitenant.config.DataSourceConfig;
import com.kevinjanvier.multitenant.config.TenantDataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
    private static final String DEFAULT_TENANT_ID = "mutlitenant";
    @Autowired
    private DataSource defaultDS;

    @Autowired
    private ApplicationContext context;

    private Map<String, DataSource> map = new HashMap<>();

    boolean init = false;

    @PostConstruct
    public void load() {
        map.put(DEFAULT_TENANT_ID, defaultDS);
    }


    @Override
    protected DataSource selectAnyDataSource() {
        return map.get(DEFAULT_TENANT_ID);
    }

    @Override
    public Connection getAnyConnection() throws SQLException {

//        public Connection connect(String host, int port, String database, String user, String password, String schemaName) throws SQLException {
//            String url = "jdbc:postgresql://" + host + ":" + port + "/" + database+"?currentSchema="+schemaName;
//            log.info("Url Connection name {} ", url);
//            return DriverManager.getConnection(url, user, password);
//        }
//        getConnection();
        return defaultDS.getConnection();
    }

    public static void getConnection() {
        DataSourceConfig config = new DataSourceConfig();
//        config.setName(xtenant);
        config.setDriverClassName("org.postgresql.Driver");
        config.setUrl("jdbc:postgresql://localhost:5432/usercoding");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setInitialize(true);
        log.info("datasource is now {} ", config);
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        log.info("------selected data source ---- {}", tenantIdentifier);
        if (!init) {
            init = true;
            TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
            log.info("------------putll all tenant -----------II-- {} ", tenantDataSource.getAll());
            map.putAll(tenantDataSource.getAll());
        }
        return map.get(tenantIdentifier) != null ? map.get(tenantIdentifier) : map.get(DEFAULT_TENANT_ID);
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }
}