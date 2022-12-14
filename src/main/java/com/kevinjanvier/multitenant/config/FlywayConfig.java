package com.kevinjanvier.multitenant.config;

import com.kevinjanvier.multitenant.repository.DataSourceConfigRepository;
import com.kevinjanvier.multitenant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Slf4j
public class FlywayConfig {


    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .locations("db/migration/public")
                .baselineOnMigrate(true)
                .installedBy("kevin Janvier")
                .dataSource(dataSource)
                .schemas("public")
                .load();
        flyway.migrate();

        return flyway;
    }

    @Bean
    CommandLineRunner commandLineRunner(DataSourceConfigRepository repository, DataSource dataSource) {
        return args -> {
            repository.findAll().forEach(user -> {
                String tenant = user.getName();
                log.info("tenant ------ {} ", tenant);
                Flyway flyway = Flyway.configure()
                        .locations("db/migration/tenants")
                        .dataSource(dataSource)
                        .schemas(tenant)
                        .load();
                log.info("response arg {} ===== {} ", args);
                flyway.migrate();
            });
        };
    }
}
