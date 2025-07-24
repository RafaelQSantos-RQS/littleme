package br.com.littleme.url_shortener.auth.web;

import br.com.littleme.url_shortener.auth.dto.LoginRequest;
import br.com.littleme.url_shortener.auth.dto.LoginResponse;
import br.com.littleme.url_shortener.auth.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());

        var authentication = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken(authentication);

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
