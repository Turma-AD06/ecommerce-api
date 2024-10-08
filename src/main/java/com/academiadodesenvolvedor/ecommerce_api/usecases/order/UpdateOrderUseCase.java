package com.academiadodesenvolvedor.ecommerce_api.usecases.order;

import com.academiadodesenvolvedor.ecommerce_api.entities.Order;
import com.academiadodesenvolvedor.ecommerce_api.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UpdateOrderUseCase {
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final OrderRepository orderRepository;

    public Order execute(Long id, Order update) {
        Order order = this.getOrderByIdUseCase.execute(id);
        if (!Objects.equals(update.getTotalPrice(), order.getTotalPrice())) {
            order.setTotalPrice(update.getTotalPrice());
        }

        if (update.getPaymentIntentId() != null) {
            order.setPaymentIntentId(update.getPaymentIntentId());
        }

        if (update.getPaymentUrl() != null) {
            order.setPaymentUrl(update.getPaymentUrl());
        }

        if (update.getStatus() != null && update.getStatus() != order.getStatus()) {
            order.setStatus(update.getStatus());
        }
        
        return orderRepository.save(order);
    }
}
