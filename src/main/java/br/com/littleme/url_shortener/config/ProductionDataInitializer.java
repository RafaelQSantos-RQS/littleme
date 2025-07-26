package br.com.littleme.url_shortener.config;

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
@Profile("prod")
@RequiredArgsConstructor
public class ProductionDataInitializer implements CommandLineRunner {

    private final AdminUserService adminUserService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> Rodando inicializador de PRODUÇÃO...");
        adminUserService.initializeAdminUser();
    }
}