package br.com.littleme.url_shortener.link.repository;

import br.com.littleme.url_shortener.link.domain.Link;
import br.com.littleme.url_shortener.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkRepository extends JpaRepository<Link, UUID> {

    Optional<Link> findByCode(String code);

    Page<Link> findAllByCreatedBy(User user, Pageable pageable);

    @Query(value = "SELECT l.* FROM links l",
            countQuery = "SELECT count(*) FROM links",
            nativeQuery = true)
    Page<Link> findAllIncludingDeleted(Pageable pageable);

    @Query(value = "SELECT l.* FROM links l WHERE l.id = :id", nativeQuery = true)
    Optional<Link> findByIdIncludingDeleted(@Param("id") UUID id);

    @Override
    @Query("SELECT l FROM links l JOIN FETCH l.createdBy JOIN FETCH l.updatedBy WHERE l.id = :id")
    @NonNull
    Optional<Link> findById(@Param("id") @NonNull UUID id);

    @Override
    @Query(value = "SELECT l FROM links l JOIN FETCH l.createdBy JOIN FETCH l.updatedBy",
            countQuery = "SELECT count(l) FROM links l")
    @NonNull
    Page<Link> findAll(@NonNull Pageable pageable);
}