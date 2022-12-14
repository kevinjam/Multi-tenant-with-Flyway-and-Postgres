package com.kevinjanvier.multitenant.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    static final String defaultTenant ="public";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String t =  TenantContext.getCurrentTenant();
        log.info("TenantSchemaResolver ---- {}", t);
        if(t!=null){
            log.info("tenant schema is not null {} ",t);
            return t;
        } else {
            log.info("return default tenant schema {} ", defaultTenant);
            return defaultTenant;
        }
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
