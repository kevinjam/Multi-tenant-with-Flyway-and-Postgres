package com.kevinjanvier.multitenant.services;

import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class TenantService {
    private DataSource dataSource;

    public TenantService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initDatabase(String schema){
        Flyway flyway = Flyway.configure()
                .locations("db/migration/tenants")
                .installedBy("Kevin Janvier")
                .dataSource(dataSource)
                .schemas(schema)
                .load();
        flyway.migrate();
    }
}
