package br.com.littleme.url_shortener.link.mapper;

import br.com.littleme.url_shortener.link.domain.Link;
import br.com.littleme.url_shortener.link.dto.LinkCreateRequest;
import br.com.littleme.url_shortener.link.dto.LinkResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LinkMapper {

    /**
     * Converts a {@link LinkCreateRequest} to a {@link Link}.
     *
     * @param linkCreateRequest the request to convert
     * @return the converted {@link Link}
     */
    Link toLink(LinkCreateRequest linkCreateRequest);

    /**
     * Converts a {@link Link} to a {@link LinkResponse}.
     *
     * @param link the {@link Link} to convert
     * @return the converted {@link LinkResponse}
     */
    LinkResponse toResponse(Link link);
}
