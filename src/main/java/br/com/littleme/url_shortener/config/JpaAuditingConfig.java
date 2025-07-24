package br.com.littleme.url_shortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;

import java.time.OffsetDateTime;
import java.util.Optional;

@Configuration
public class JpaAuditingConfig {

    /**
     * A Spring Data {@link AuditorAware} that returns the current user identifier.
     *
     * @return A bean that provides the current user identifier.
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    /**
     * Provides a {@link DateTimeProvider} that returns the current date and time.
     *
     * @return A bean that provides the current date and time.
     */
    @Bean(name = "auditingDateTimeProvider")
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }
}
