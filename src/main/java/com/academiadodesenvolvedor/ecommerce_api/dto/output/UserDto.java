package com.academiadodesenvolvedor.ecommerce_api.dto.output;

import com.academiadodesenvolvedor.ecommerce_api.entities.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private List<ProductDto> products;
    private List<Roles> roles;
}
