package com.mithunnirmal.merch.audit;

import com.mithunnirmal.merch.utils.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditConfig {

    @Bean (name = "auditorAware")
    public AuditorAware<String> getAuditorAware() {
        return new AuditorAwareImpl();
    }

}
