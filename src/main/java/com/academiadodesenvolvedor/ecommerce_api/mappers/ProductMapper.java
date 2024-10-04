package com.academiadodesenvolvedor.ecommerce_api.mappers;

import com.academiadodesenvolvedor.ecommerce_api.dto.input.CreateProductDto;
import com.academiadodesenvolvedor.ecommerce_api.dto.output.ProductDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(CreateProductDto dto);

    ProductDto toOutputDto(Product product);
}
