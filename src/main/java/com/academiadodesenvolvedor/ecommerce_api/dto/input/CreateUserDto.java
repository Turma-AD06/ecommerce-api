package com.academiadodesenvolvedor.ecommerce_api.dto.input;

import com.academiadodesenvolvedor.ecommerce_api.entities.User;
import com.academiadodesenvolvedor.ecommerce_api.entities.enums.Roles;
import com.academiadodesenvolvedor.ecommerce_api.validation.IsUnique;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
    @NotEmpty
    private String name;
    @NotEmpty
    @Size(min = 8, max = 20)
    private String password;
    private Roles role;
    @NotEmpty
    @Email
    @IsUnique(entity = User.class)
    private String email;
}
