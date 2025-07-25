package br.com.littleme.url_shortener.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserSelfUpdateRequest(
        @Size(min = 3, max = 255)
        String name,

        @Email
        String email,

        @Size(min = 6)
        String password
) {
}
