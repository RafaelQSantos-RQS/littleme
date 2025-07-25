package br.com.littleme.url_shortener.user.service;

import br.com.littleme.url_shortener.common.dto.PageResponse;
import br.com.littleme.url_shortener.common.exception.EmailAlreadyExistsException;
import br.com.littleme.url_shortener.common.exception.SelfDeletionException;
import br.com.littleme.url_shortener.common.exception.UserNotFoundException;
import br.com.littleme.url_shortener.user.domain.Role;
import br.com.littleme.url_shortener.user.domain.User;
import br.com.littleme.url_shortener.user.dto.*;
import br.com.littleme.url_shortener.user.mapper.UserMapper;
import br.com.littleme.url_shortener.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResponse create(UserCreateRequest request) {
        var newUser = userMapper.toUser(request);

        var hashedPassword = passwordEncoder.encode(request.password());
        newUser.setPassword(hashedPassword);

        newUser.setRole(Role.ROLE_USER);

        var savedUser = userRepository.save(newUser);

        return userMapper.toResponse(savedUser);
    }

    public PageResponse<AdminUserResponse> findAll(Pageable pageable, boolean includeDeleted) {
        Page<User> userPage;
        if (includeDeleted) {
            userPage = this.userRepository.findAllIncludingDeleted(pageable);
        } else {
            userPage = this.userRepository.findAll(pageable);
        }

        List<AdminUserResponse> userResponses = userPage.getContent().stream()
                .map(userMapper::toAdminResponse)
                .toList();

        return new PageResponse<>(
                userResponses,
                userPage.getNumber() + 1,
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages()
        );
    }

    public AdminUserResponse findById(UUID id) {
        return this.userRepository.findById(id)
                .map(userMapper::toAdminResponse)
                .orElseThrow(() -> new UserNotFoundException("User with ID '" + id + "' not found."));
    }

    @Transactional
    public UserResponse updateSelf(User currentUser, UserSelfUpdateRequest request) {
        if (request.name() != null && !request.name().isBlank()) {
            currentUser.setName(request.name());
        }
        if (request.email() != null && !request.email().isBlank()) {
            userRepository.findByEmail(request.email()).ifPresent(user -> {
                if (!user.getId().equals(currentUser.getId())) {
                    throw new EmailAlreadyExistsException("Email '" + request.email() + "' already exists.");
                }
            });
            currentUser.setEmail(request.email());
        }
        if (request.password() != null && !request.password().isBlank()) {
            currentUser.setPassword(passwordEncoder.encode(request.password()));
        }
        userRepository.save(currentUser);
        return userMapper.toResponse(currentUser);
    }

    @Transactional
    public AdminUserResponse updateByAdmin(UUID id, UserAdminUpdateRequest request) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID '" + id + "' not found."));

        if (request.email() != null) {
            userRepository.findByEmail(request.email()).ifPresent(user -> {
                if (!user.getId().equals(id)) {
                    throw new EmailAlreadyExistsException("The email address is already in use.");
                }
            });
            userToUpdate.setEmail(request.email());
        }

        if (request.name() != null) {
            userToUpdate.setName(request.name());
        }
        if (request.role() != null) {
            userToUpdate.setRole(request.role());
        }

        userRepository.save(userToUpdate);

        return userMapper.toAdminResponse(userToUpdate);
    }

    @Transactional
    public void deleteById(UUID id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getId().equals(id)) {
            throw new SelfDeletionException("An admin cannot delete themselves.");
        }

        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID '" + id + "' not found for deletion."));

        userToDelete.setDeletedAt(OffsetDateTime.now());
        userToDelete.setDeletedBy(currentUser);

        userRepository.save(userToDelete);
    }

    @Transactional
    public AdminUserResponse restoreById(UUID id) {
        User userToRestore = userRepository.findByIdIncludingDeleted(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID '" + id + "' not found."));

        if (userToRestore.getDeletedAt() == null) {
            throw new IllegalStateException("User is not deleted and cannot be restored.");
        }

        userToRestore.setDeletedAt(null);
        userToRestore.setDeletedBy(null);

        userRepository.save(userToRestore);
        return userMapper.toAdminResponse(userToRestore);
    }
}
