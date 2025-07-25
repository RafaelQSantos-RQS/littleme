package br.com.littleme.url_shortener.config;

import br.com.littleme.url_shortener.user.domain.Role;
import br.com.littleme.url_shortener.user.domain.User;
import br.com.littleme.url_shortener.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.user.email}")
    private String adminEmail;

    @Value("${admin.user.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User adminUser = new User();
            adminUser.setName("Admin User");
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setRole(Role.ROLE_ADMIN);

            userRepository.save(adminUser);
            System.out.println(">>> Usuário admin padrão criado com sucesso!");
        } else {
            System.out.println(">>> Usuário admin já existe. Nenhuma ação necessária.");
        }
    }
}
