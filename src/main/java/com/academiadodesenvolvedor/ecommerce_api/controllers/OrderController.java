package com.academiadodesenvolvedor.ecommerce_api.controllers;

import com.academiadodesenvolvedor.ecommerce_api.dto.input.CreateOrderDto;
import com.academiadodesenvolvedor.ecommerce_api.dto.output.OrderDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.Order;
import com.academiadodesenvolvedor.ecommerce_api.mappers.OrderMapper;
import com.academiadodesenvolvedor.ecommerce_api.usecases.order.CreateOrderUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;
    private final OrderMapper orderMapper;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderDto> create(@RequestBody @Valid CreateOrderDto dto, HttpServletRequest request) {
        Long userId = Long.valueOf(request.getAttribute("user_id").toString());
        Order order = this.createOrderUseCase.execute(userId, dto);
        OrderDto orderDto = this.orderMapper.toOutputDto(order);

        orderDto.setItems(
                order
                        .getOrderItems()
                        .stream().map(this.orderMapper::toOrderItemDto).toList()
        );

        return new ResponseEntity<>(
                orderDto,
                HttpStatus.CREATED
        );
    }

}
