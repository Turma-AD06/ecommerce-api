package com.academiadodesenvolvedor.ecommerce_api.repositories;

import com.academiadodesenvolvedor.ecommerce_api.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
