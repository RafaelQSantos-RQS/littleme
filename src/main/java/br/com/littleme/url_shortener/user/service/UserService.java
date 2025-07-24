package br.com.littleme.url_shortener.user.service;

import br.com.littleme.url_shortener.user.dto.UserCreateRequest;
import br.com.littleme.url_shortener.user.dto.UserResponse;
import br.com.littleme.url_shortener.user.mapper.UserMapper;
import br.com.littleme.url_shortener.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        var savedUser = userRepository.save(newUser);

        return userMapper.toResponse(savedUser);
    }
}
