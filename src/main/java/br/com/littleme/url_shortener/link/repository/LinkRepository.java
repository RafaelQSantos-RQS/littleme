package br.com.littleme.url_shortener.link.repository;

import br.com.littleme.url_shortener.link.domain.Link;
import br.com.littleme.url_shortener.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT l.* FROM links l",
            countQuery = "SELECT count(*) FROM links",
            nativeQuery = true)
    Page<Link> findAllIncludingDeleted(Pageable pageable);

    @Query(value = "SELECT l.* FROM links l WHERE l.id = :id", nativeQuery = true)
    Optional<Link> findByIdIncludingDeleted(@Param("id") UUID id);

    Page<Link> findAllByCreatedBy(User user, Pageable pageable);
}
