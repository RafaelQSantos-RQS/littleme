package br.com.littleme.url_shortener.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    /**
     * Retrieves the current auditor based on Spring Security context information.
     * Will return <code>SYSTEM</code> if no auditor is set.
     *
     * @return an {@link Optional} {@link String} auditor.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("SYSTEM");
    }
}
