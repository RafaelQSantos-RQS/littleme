package br.com.littleme.url_shortener.link.service;

import br.com.littleme.url_shortener.common.dto.PageResponse;
import br.com.littleme.url_shortener.common.exception.LinkNotFoundException;
import br.com.littleme.url_shortener.link.domain.Link;
import br.com.littleme.url_shortener.link.dto.LinkCreateRequest;
import br.com.littleme.url_shortener.link.dto.LinkResponse;
import br.com.littleme.url_shortener.link.dto.LinkUpdateRequest;
import br.com.littleme.url_shortener.link.mapper.LinkMapper;
import br.com.littleme.url_shortener.link.repository.LinkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;
    private final LinkMapper linkMapper;

    /**
     * Creates a new link given a {@link LinkCreateRequest}. The link's code is
     * randomly generated and has a length of 8 characters. The link is then saved
     * in the database and a {@link LinkResponse} is returned.
     *
     * @param request the request with the full URL and description of the link
     * @return the response with the shortened URL
     */
    public LinkResponse create(LinkCreateRequest request) {
        Link link = linkMapper.toLink(request);

        String randomCode = UUID.randomUUID().toString().substring(0, 8);
        link.setCode(randomCode);

        Link savedLink = linkRepository.save(link);
        return linkMapper.toResponse(savedLink);
    }

    /**
     * Finds a link by its code.
     *
     * @param code the code to search for
     * @return the link with the given code
     * @throws LinkNotFoundException if no link with the given code was found
     */
    public Link findByCode(String code) {
        return linkRepository.findByCode(code)
                .orElseThrow(() -> new LinkNotFoundException("Link with code " + code + " not found!!"));
    }

    /**
     * Retrieves a paginated list of all links.
     *
     * @param pageable the pagination information
     * @return a PageResponse containing a list of LinkResponse, along
     * with pagination details such as the current page number, page size,
     * total elements, and total pages
     */
    public PageResponse<LinkResponse> findAll(Pageable pageable) {
        Page<Link> linkPage = linkRepository.findAll(pageable);

        List<LinkResponse> linkResponses = linkPage.getContent().stream()
                .map(linkMapper::toResponse)
                .toList();

        return new PageResponse<>(
                linkResponses,
                linkPage.getNumber() + 1,
                linkPage.getSize(),
                linkPage.getTotalElements(),
                linkPage.getTotalPages()
        );
    }

    /**
     * Retrieves a link by its ID.
     *
     * @param uuid the ID to search for
     * @return the link with the given ID
     * @throws LinkNotFoundException if no link with the given ID was found
     */
    public LinkResponse findById(UUID uuid) {
        return linkRepository.findById(uuid)
                .map(linkMapper::toResponse)
                .orElseThrow(() -> new LinkNotFoundException("Link with id " + uuid + " not found!!"));
    }

    /**
     * Updates an existing link with the provided full URL and description.
     *
     * @param id the ID of the link to be updated
     * @param request the request containing the new full URL and description
     * @return the updated link as a {@link LinkResponse}
     * @throws LinkNotFoundException if no link with the given ID is found
     */
    @Transactional
    public LinkResponse update(UUID id, LinkUpdateRequest request) {

        Link existingLink = this.linkRepository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException("Link with id " + id + " not found!!"));

        existingLink.setFullUrl(request.fullUrl());
        existingLink.setDescription(request.description());

        Link updatedLink = this.linkRepository.save(existingLink);

        return linkMapper.toResponse(updatedLink);
    }

    /**
     * Deletes a link by its ID.
     *
     * @param id the ID of the link to be deleted
     * @throws LinkNotFoundException if no link with the given ID is found
     */
    @Transactional
    public void deleteById(UUID id) {
        if (!this.linkRepository.existsById(id)) {
            throw new LinkNotFoundException("Link com o ID '" + id + "' não encontrado para exclusão.");
        }

        this.linkRepository.deleteById(id);
    }

}
