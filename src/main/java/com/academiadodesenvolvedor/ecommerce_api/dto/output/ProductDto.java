package com.academiadodesenvolvedor.ecommerce_api.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private String picture;
    private Integer score;
    @JsonProperty("category_id")
    private Long categoryId;
}
