package com.academiadodesenvolvedor.ecommerce_api.controllers;

import com.academiadodesenvolvedor.ecommerce_api.dto.input.CreateUserDto;
import com.academiadodesenvolvedor.ecommerce_api.dto.input.LoginUserDto;
import com.academiadodesenvolvedor.ecommerce_api.dto.output.JwtLoginDto;
import com.academiadodesenvolvedor.ecommerce_api.dto.output.UserDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.Role;
import com.academiadodesenvolvedor.ecommerce_api.entities.User;
import com.academiadodesenvolvedor.ecommerce_api.entities.enums.Roles;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.HttpException;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.UnauthorizedException;
import com.academiadodesenvolvedor.ecommerce_api.mappers.UserMapper;
import com.academiadodesenvolvedor.ecommerce_api.usecases.role.GetRoleByNameUseCase;
import com.academiadodesenvolvedor.ecommerce_api.usecases.user.CreateUserUseCase;
import com.academiadodesenvolvedor.ecommerce_api.usecases.user.LoginUserUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final CreateUserUseCase createUserUseCase;
    private final UserMapper userMapper;
    private final LoginUserUseCase loginUserUseCase;
    private final GetRoleByNameUseCase getRoleByNameUseCase;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody @Valid CreateUserDto dto) {
        if (dto.getRole().equals(Roles.ADMIN)) {
            throw new HttpException("Permissão de admin não pode ser atribuída", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Role role = this.getRoleByNameUseCase.execute(dto.getRole());
        List<Role> roles = List.of(role);
        User user = this.userMapper.toEntity(dto);
        user.setRoles(roles);

        user = createUserUseCase.execute(user);

        UserDto response = this.userMapper.toOutputDto(user);
        List<Roles> rolesResponse = user.getRoles().stream().map(Role::getName).toList();
        response.setRoles(rolesResponse);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtLoginDto> login(@RequestBody @Valid LoginUserDto dto) {
        try {
            JwtLoginDto login = this.loginUserUseCase.execute(dto.getEmail(), dto.getPassword());
            return new ResponseEntity<>(login, HttpStatus.OK);
        } catch (Exception e) {
            throw new UnauthorizedException("Email e/ou senha Inválidos.");
        }
    }

}
