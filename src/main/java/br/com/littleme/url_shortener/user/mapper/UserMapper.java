package br.com.littleme.url_shortener.user.mapper;

import br.com.littleme.url_shortener.user.domain.User;
import br.com.littleme.url_shortener.user.dto.UserCreateRequest;
import br.com.littleme.url_shortener.user.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest userCreateRequest);

    UserResponse toResponse(User user);
}
