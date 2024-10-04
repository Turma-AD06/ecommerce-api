package com.academiadodesenvolvedor.ecommerce_api.mappers;

import com.academiadodesenvolvedor.ecommerce_api.dto.output.OrderDto;
import com.academiadodesenvolvedor.ecommerce_api.dto.output.OrderItemDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.Order;
import com.academiadodesenvolvedor.ecommerce_api.entities.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toOutputDto(Order order);

    OrderItemDto toOrderItemDto(OrderItem orderItem);
}
