package com.academiadodesenvolvedor.ecommerce_api.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDto {
    @NotEmpty
    private List<CreateOrderItemDto> products;
}
