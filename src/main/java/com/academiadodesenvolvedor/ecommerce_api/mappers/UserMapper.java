package com.academiadodesenvolvedor.ecommerce_api.mappers;

import com.academiadodesenvolvedor.ecommerce_api.dto.input.CreateUserDto;
import com.academiadodesenvolvedor.ecommerce_api.dto.output.UserDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(CreateUserDto dto);

    @Mapping(target = "roles", ignore = true)
    UserDto toOutputDto(User user);
}
