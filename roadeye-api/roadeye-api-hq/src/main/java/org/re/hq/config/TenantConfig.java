package org.re.hq.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.re.hq.tenant.TenantIdProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TenantConfig {
    @Bean
    @ConditionalOnMissingBean(TenantIdProvider.class)
    public TenantIdProvider devTenantIdProvider() {
        // TODO: Remove this when multi-tenancy is implemented
        log.warn("No TenantIdProvider bean found. Using default tenant ID. It should not be used in production.");
        return () -> 234928403234L;
    }
}
