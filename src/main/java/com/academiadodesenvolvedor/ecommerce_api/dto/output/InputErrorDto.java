package com.academiadodesenvolvedor.ecommerce_api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputErrorDto {
    private String field;
    private String message;
}
