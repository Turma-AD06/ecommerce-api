package com.academiadodesenvolvedor.ecommerce_api.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    @JsonProperty("total_price")
    private Long totalPrice;
    private List<OrderItemDto> items;
}
