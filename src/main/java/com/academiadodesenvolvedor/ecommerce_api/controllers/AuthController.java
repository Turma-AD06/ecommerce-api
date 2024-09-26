package com.academiadodesenvolvedor.ecommerce_api.controllers;

import com.academiadodesenvolvedor.ecommerce_api.dto.input.CreateUserDto;
import com.academiadodesenvolvedor.ecommerce_api.dto.input.LoginUserDto;
import com.academiadodesenvolvedor.ecommerce_api.dto.output.JwtLoginDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.User;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.HttpException;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.UnauthorizedException;
import com.academiadodesenvolvedor.ecommerce_api.mappers.UserMapper;
import com.academiadodesenvolvedor.ecommerce_api.usecases.user.CreateUserUseCase;
import com.academiadodesenvolvedor.ecommerce_api.usecases.user.LoginUserUseCase;
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
    private final UserMapper userMapper;
    private final LoginUserUseCase loginUserUseCase;


    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody @Valid CreateUserDto dto){
        User user = this.userMapper.toEntity(dto);

        user = createUserUseCase.execute(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtLoginDto> login(@RequestBody @Valid LoginUserDto dto){
        try{
            JwtLoginDto login = this.loginUserUseCase.execute(dto.getEmail(), dto.getPassword());
            return new ResponseEntity<>(login, HttpStatus.OK);
        }catch (Exception e){
            throw new UnauthorizedException("Email e/ou senha Inválidos.");
        }
    }

}
