package br.com.littleme.url_shortener.link.dto;

import java.time.OffsetDateTime;
import java.util.UUID;


public record LinkResponse(
        UUID id,
        String fullUrl,
        String code,
        String description,
        OffsetDateTime createdAt
) {
}
