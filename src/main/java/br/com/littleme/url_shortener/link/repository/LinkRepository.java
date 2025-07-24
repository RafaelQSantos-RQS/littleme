package br.com.littleme.url_shortener.link.repository;

import br.com.littleme.url_shortener.link.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkRepository extends JpaRepository<Link, UUID> {
    /**
     * Finds a link by its code.
     *
     * @param code the link code.
     * @return an optional of the link.
     */
    Optional<Link> findByCode(String code);
}
