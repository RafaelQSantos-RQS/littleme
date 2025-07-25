package br.com.littleme.url_shortener.user.repository;

import br.com.littleme.url_shortener.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u.* FROM users u",
            countQuery = "SELECT count(*) FROM users",
            nativeQuery = true)
    Page<User> findAllIncludingDeleted(Pageable pageable);

    @Query(value = "SELECT u.* FROM users u WHERE u.id = :id", nativeQuery = true)
    Optional<User> findByIdIncludingDeleted(@Param("id") UUID id);
}
