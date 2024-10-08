package com.academiadodesenvolvedor.ecommerce_api.controllers;

import com.academiadodesenvolvedor.ecommerce_api.dto.input.CreateOrderDto;
import com.academiadodesenvolvedor.ecommerce_api.dto.output.OrderDto;
import com.academiadodesenvolvedor.ecommerce_api.dto.output.PaymentDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.Order;
import com.academiadodesenvolvedor.ecommerce_api.entities.enums.OrderStatus;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.HttpException;
import com.academiadodesenvolvedor.ecommerce_api.mappers.OrderMapper;
import com.academiadodesenvolvedor.ecommerce_api.usecases.order.CreateOrderUseCase;
import com.academiadodesenvolvedor.ecommerce_api.usecases.order.GetOrderByIdUseCase;
import com.academiadodesenvolvedor.ecommerce_api.usecases.order.ProcessStripePaymentUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final ProcessStripePaymentUseCase processStripePaymentUseCase;
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

    @PutMapping("/{id}/payment")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<PaymentDto> payment(@PathVariable("id") Long id) {
        Order order = this.getOrderByIdUseCase.execute(id);
        if (order.getStatus() == OrderStatus.PAID) {
            throw new HttpException("Não é possível Processar um pedido pago", HttpStatus.BAD_REQUEST);
        }
        PaymentDto result = processStripePaymentUseCase.execute(order.getId());

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
