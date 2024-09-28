package com.academiadodesenvolvedor.ecommerce_api.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {
    @NotEmpty
    private String name;
    private String description;
    private Long price;
    private String picture;
    private Integer score;
//    @NotEmpty
//    private Long categoryId;
}
