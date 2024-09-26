package com.academiadodesenvolvedor.ecommerce_api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtLoginDto {
    private String access_token;
    private Instant expires_at;
}
