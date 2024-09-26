package com.academiadodesenvolvedor.ecommerce_api.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
}
