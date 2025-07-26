package br.com.littleme.url_shortener.link.web;

import br.com.littleme.url_shortener.link.domain.Link;
import br.com.littleme.url_shortener.link.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final LinkService linkService;

    @GetMapping("/{code:[a-zA-Z0-9]{8}}")
    @Operation(security = {})
    public ResponseEntity<Void> redirect(@PathVariable String code) {

        Link link = linkService.findByCode(code);
        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(link.getFullUrl()))
                .build();
    }
}
