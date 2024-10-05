package com.academiadodesenvolvedor.ecommerce_api.usecases.order;

import com.academiadodesenvolvedor.ecommerce_api.entities.Order;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.NotFoundException;
import com.academiadodesenvolvedor.ecommerce_api.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrderByIdUseCase {
    public final OrderRepository orderRepository;

    public Order execute(Long id) {
        return this.orderRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Nenhum pedido encontrado"));
    }
}
