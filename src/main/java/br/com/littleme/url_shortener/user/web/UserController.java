package br.com.littleme.url_shortener.user.web;

import br.com.littleme.url_shortener.common.dto.PageResponse;
import br.com.littleme.url_shortener.user.domain.User;
import br.com.littleme.url_shortener.user.dto.*;
import br.com.littleme.url_shortener.user.mapper.UserMapper;
import br.com.littleme.url_shortener.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User Management", description = "Endpoints for user management")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @Operation(security = {})
    public ResponseEntity<UserResponse> create(@RequestBody UserCreateRequest request) {
        UserResponse response = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<AdminUserResponse>> findAll(
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(name = "includeDeleted", defaultValue = "false") boolean includeDeleted
    ) {
        PageResponse<AdminUserResponse> response = userService.findAll(pageable, includeDeleted);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Find a User by ID", description = "Returns the details of a specific user based on its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Access denied (user is not an admin or owner)")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AdminUserResponse> findById(@PathVariable UUID id) {
        var response = userService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminUserResponse> updateByAdmin(
            @PathVariable UUID id,
            @RequestBody @Valid UserAdminUpdateRequest request
    ) {
        AdminUserResponse response = userService.updateByAdmin(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/restore")
    public ResponseEntity<AdminUserResponse> restoreById(@PathVariable UUID id) {
        var response = userService.restoreById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyInfo(@AuthenticationPrincipal User currentUser) {
        UserResponse response = userMapper.toResponse(currentUser);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateSelf(
            @AuthenticationPrincipal User currentUser,
            @RequestBody @Valid UserSelfUpdateRequest request
    ) {
        var response = userService.updateSelf(currentUser, request);
        return ResponseEntity.ok(response);
    }
}
