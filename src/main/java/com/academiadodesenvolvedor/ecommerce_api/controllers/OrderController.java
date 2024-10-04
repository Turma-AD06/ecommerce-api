package com.academiadodesenvolvedor.ecommerce_api.controllers;

import com.academiadodesenvolvedor.ecommerce_api.dto.input.CreateOrderDto;
import com.academiadodesenvolvedor.ecommerce_api.usecases.order.CreateOrderUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateOrderDto dto, HttpServletRequest request) {
        Long userId = Long.valueOf(request.getAttribute("user_id").toString());
        this.createOrderUseCase.execute(userId, dto);
        return ResponseEntity.ok().build();
    }

}
