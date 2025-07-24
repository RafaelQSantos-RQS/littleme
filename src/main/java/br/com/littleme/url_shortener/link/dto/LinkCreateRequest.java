package br.com.littleme.url_shortener.link.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record LinkCreateRequest(
        @NotBlank(message = "Full URL is required, cannot be empty")
        @URL(message = "Full URL must be a valid URL")
        String fullUrl,

        @NotBlank(message = "Description is required, cannot be empty")
        @Size(min = 3, max = 255, message = "Description must be between 3 and 255 characters")
        String description
) {
}
