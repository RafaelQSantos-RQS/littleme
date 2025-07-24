package br.com.littleme.url_shortener.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank @Size(min = 3, max = 255, message = "The name must be between 3 and 255 characters.")
        String name,

        @NotBlank @Email
        String email,

        @NotBlank @Size(min = 6, message = "The password must be at least 6 characters.")
        String password
) {
}
