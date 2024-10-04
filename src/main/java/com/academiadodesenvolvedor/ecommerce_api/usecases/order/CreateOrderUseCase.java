package com.academiadodesenvolvedor.ecommerce_api.usecases.order;

import com.academiadodesenvolvedor.ecommerce_api.dto.input.CreateOrderDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.Order;
import com.academiadodesenvolvedor.ecommerce_api.entities.OrderItem;
import com.academiadodesenvolvedor.ecommerce_api.entities.Product;
import com.academiadodesenvolvedor.ecommerce_api.repositories.OrderRepository;
import com.academiadodesenvolvedor.ecommerce_api.usecases.product.GetProductByIdUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final GetProductByIdUseCase getProductByIdUseCase;

    @Transactional
    public Order execute(Long userId, CreateOrderDto order) {
        List<OrderItem> orderItemList = new ArrayList<>();

        Long totalPrice = order.getProducts().stream().map(productOrder -> {
            Product product = this.getProductByIdUseCase.execute(productOrder.getProductId());

            OrderItem item = new OrderItem();
            item.setProductId(product.getId());
            item.setQuantity(productOrder.getQuantity());
            item.setUnitPrice(product.getPrice());
            orderItemList.add(item);

            return product.getPrice() * productOrder.getQuantity();
        }).reduce(0L, Long::sum);

        Order orderToStore = new Order();

        orderToStore.setUserId(userId);
        orderToStore.setTotalPrice(totalPrice);
        orderToStore.setOrderItems(orderItemList);

        return this.orderRepository.save(orderToStore);

    }
}
