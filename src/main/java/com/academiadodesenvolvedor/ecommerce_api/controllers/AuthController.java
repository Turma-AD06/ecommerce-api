package com.academiadodesenvolvedor.ecommerce_api.controllers;

import com.academiadodesenvolvedor.ecommerce_api.dto.input.CreateUserDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.User;
import com.academiadodesenvolvedor.ecommerce_api.usecases.user.CreateUserUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final CreateUserUseCase createUserUseCase;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody @Valid CreateUserDto dto){
        User user = new User();
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user = createUserUseCase.execute(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
