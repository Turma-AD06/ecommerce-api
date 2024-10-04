package com.academiadodesenvolvedor.ecommerce_api.mappers;

import com.academiadodesenvolvedor.ecommerce_api.dto.input.CreateCategoryDto;
import com.academiadodesenvolvedor.ecommerce_api.dto.output.CategoryDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CreateCategoryDto dto);

    CategoryDto toOutputDto(Category category);
}
