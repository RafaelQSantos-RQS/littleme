package br.com.littleme.url_shortener.link.dto;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record LinkUpdateRequest(
        @URL(message = "URL inválida.")
        String fullUrl,

        @Size(min = 3, max = 255, message = "Descrição deve ter entre 3 e 255 caracteres.")
        String description
) {
}
