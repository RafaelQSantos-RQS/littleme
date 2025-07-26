package br.com.littleme.url_shortener.user.service;

import br.com.littleme.url_shortener.user.domain.Role;
import br.com.littleme.url_shortener.user.domain.User;
import br.com.littleme.url_shortener.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.user.email}")
    private String adminEmail;

    @Value("${admin.user.password}")
    private String adminPassword;

    public void initializeAdminUser() {
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User adminUser = new User();
            adminUser.setName("Admin User");
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setRole(Role.ROLE_ADMIN);

            userRepository.save(adminUser);
            System.out.println(">>> Default admin user initialized successfully!");
        } else {
            System.out.println(">>> Admin user already exists.");
        }
    }
}