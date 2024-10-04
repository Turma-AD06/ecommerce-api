package com.academiadodesenvolvedor.ecommerce_api.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderItemDto {
    @JsonProperty("product_id")
    private Long productId;
    private Integer quantity;
}
