package br.com.littleme.url_shortener.user.mapper;

import br.com.littleme.url_shortener.user.domain.User;
import br.com.littleme.url_shortener.user.dto.AdminUserResponse;
import br.com.littleme.url_shortener.user.dto.UserCreateRequest;
import br.com.littleme.url_shortener.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest userCreateRequest);

    UserResponse toResponse(User user);

    @Mapping(source = "createdBy.email", target = "createdBy")
    @Mapping(source = "updatedBy.email", target = "updatedBy")
    AdminUserResponse toAdminResponse(User user);
}
