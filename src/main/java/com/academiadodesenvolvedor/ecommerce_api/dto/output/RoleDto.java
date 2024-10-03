package com.academiadodesenvolvedor.ecommerce_api.dto.output;

import com.academiadodesenvolvedor.ecommerce_api.entities.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private Roles role;
}
