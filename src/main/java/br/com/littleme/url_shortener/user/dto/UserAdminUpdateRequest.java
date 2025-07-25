package br.com.littleme.url_shortener.user.dto;

import br.com.littleme.url_shortener.user.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserAdminUpdateRequest(
        @Size(min = 3, max = 255)
        String name,

        @Email
        String email,

        Role role
) {
}
