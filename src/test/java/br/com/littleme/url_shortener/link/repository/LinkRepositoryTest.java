package br.com.littleme.url_shortener.link.repository;

import br.com.littleme.url_shortener.config.JpaAuditingConfig;
import br.com.littleme.url_shortener.link.domain.Link;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaAuditingConfig.class)
class LinkRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private LinkRepository linkRepository;

    @Test
    void shouldSaveAndFindLinkById() {
        var newLink = new Link();
        newLink.setFullUrl("https://spring.io");
        newLink.setCode("springio");
        newLink.setDescription("Site oficial do Spring");

        var savedLink = linkRepository.save(newLink);

        var foundLinkOptional = linkRepository.findById(savedLink.getId());

        assertThat(foundLinkOptional).isPresent();
        var foundLink = foundLinkOptional.get();

        assertThat(foundLink.getId()).isEqualTo(savedLink.getId());
        assertThat(foundLink.getFullUrl()).isEqualTo("https://spring.io");
        assertThat(foundLink.getCode()).isEqualTo("springio");
        assertThat(foundLink.getCreatedAt()).isNotNull();
        assertThat(foundLink.getCreatedBy()).isEqualTo("SYSTEM");
    }
}
