package br.com.littleme.url_shortener.auth.service;

import br.com.littleme.url_shortener.user.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret.key}")
    private String secretKeyString;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Instant now = Instant.now();
        // Token expira em 1 hora (em milissegundos)
        long expirationTime = 3600_000L;

        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .issuer("url-shortener-api")
                .subject(user.getEmail())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationTime)))
                .signWith(secretKey)
                .compact();
    }

    public String getSubject(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}