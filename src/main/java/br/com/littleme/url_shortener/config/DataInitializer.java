package br.com.littleme.url_shortener.config;

import br.com.littleme.url_shortener.link.domain.Link;
import br.com.littleme.url_shortener.link.repository.LinkRepository;
import br.com.littleme.url_shortener.user.domain.Role;
import br.com.littleme.url_shortener.user.domain.User;
import br.com.littleme.url_shortener.user.repository.UserRepository;
import br.com.littleme.url_shortener.user.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final LinkRepository linkRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminUserService adminUserService;

    @Value("${admin.user.email}")
    private String adminEmail;

    @Value("${admin.user.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        // --- Creates Admin User ---
        adminUserService.initializeAdminUser();

        // --- Creates Default User ---
        User regularUser = null;
        if (userRepository.findByEmail("user@email.com").isEmpty()) {
            regularUser = new User();
            regularUser.setName("Regular User");
            regularUser.setEmail("user@email.com");
            regularUser.setPassword(passwordEncoder.encode("password123"));
            regularUser.setRole(Role.ROLE_USER);
            regularUser = userRepository.save(regularUser); // Saves and gets the managed instance
            System.out.println(">>> Default user 'user@email.com' created successfully!");
        } else {
            regularUser = userRepository.findByEmail("user@email.com").get();
            System.out.println(">>> Default user already exists.");
        }

        // --- Creates Example Links for Default User ---
        if (linkRepository.count() == 0) {
            System.out.println(">>> Creating example links...");
            Link link1 = new Link();
            link1.setFullUrl("https://spring.io/");
            link1.setDescription("Official Spring Framework website");
            link1.setCode("springio");
            link1.setCreatedBy(regularUser);
            link1.setUpdatedBy(regularUser);
            linkRepository.save(link1);

            Link link2 = new Link();
            link2.setFullUrl("https://www.docker.com/");
            link2.setDescription("Official Docker website");
            link2.setCode("docker");
            link2.setCreatedBy(regularUser);
            link2.setUpdatedBy(regularUser);
            linkRepository.save(link2);
        }
    }
}