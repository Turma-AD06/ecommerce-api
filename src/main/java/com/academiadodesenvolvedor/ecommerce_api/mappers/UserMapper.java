package com.academiadodesenvolvedor.ecommerce_api.mappers;

import com.academiadodesenvolvedor.ecommerce_api.dto.input.CreateUserDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(CreateUserDto dto);
}
