package com.academiadodesenvolvedor.ecommerce_api.usecases.user;

import com.academiadodesenvolvedor.ecommerce_api.dto.output.JwtLoginDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.User;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.HttpException;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.UnauthorizedException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LoginUserUseCase {
    private final GetUserByEmailUseCase getUserByEmailUseCase;
    private final PasswordEncoder passwordEncoder;
    private final Algorithm algorithm;

    @Value("${app.jwt.expiration}")
    private Long expiration;

    public JwtLoginDto execute(String email, String password){

        User user = this.getUserByEmailUseCase.execute(email);

        if(!this.passwordEncoder.matches(password, user.getPassword())){
            throw new UnauthorizedException("Incorrect Password");
        }

        Instant expiresIn = Instant.now().plus(Duration.ofHours(this.expiration));
        String token = JWT.create()
                .withSubject(user.getId().toString())
                .withExpiresAt(expiresIn)
                .sign(this.algorithm);

            return new JwtLoginDto(token, expiresIn);

    }

}
