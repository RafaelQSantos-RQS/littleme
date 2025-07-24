package br.com.littleme.url_shortener.link.web;

import br.com.littleme.url_shortener.common.dto.PageResponse;
import br.com.littleme.url_shortener.link.dto.LinkCreateRequest;
import br.com.littleme.url_shortener.link.dto.LinkResponse;
import br.com.littleme.url_shortener.link.dto.LinkUpdateRequest;
import br.com.littleme.url_shortener.link.service.LinkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @PostMapping
    public ResponseEntity<LinkResponse> createLink(@RequestBody @Valid LinkCreateRequest request) {
        LinkResponse response = linkService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<LinkResponse>> findAll(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        PageResponse<LinkResponse> response = this.linkService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LinkResponse> findById(@PathVariable UUID id) {
        LinkResponse response = this.linkService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LinkResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid LinkUpdateRequest request
    ) {
        LinkResponse response = this.linkService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        this.linkService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
