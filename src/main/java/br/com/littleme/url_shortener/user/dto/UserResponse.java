package br.com.littleme.url_shortener.user.dto;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email
) {
}
