package com.academiadodesenvolvedor.ecommerce_api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {
    private String message;
    private HttpStatus status;
}
