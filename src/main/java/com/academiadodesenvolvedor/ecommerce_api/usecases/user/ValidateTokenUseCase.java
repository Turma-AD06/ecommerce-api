package com.academiadodesenvolvedor.ecommerce_api.usecases.user;

import com.academiadodesenvolvedor.ecommerce_api.exceptions.UserTokenExpiredException;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.UnauthorizedException;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ValidateTokenUseCase {

    private final JWTVerifier jwtVerifier;

    public DecodedJWT execute(String token){
        try{
            DecodedJWT decoded = this.jwtVerifier.verify(token);
            LocalDateTime tokenExpiration = decoded.getExpiresAt()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            if(LocalDateTime.now().isAfter(tokenExpiration)){
                throw new UserTokenExpiredException("Token expirado");
            }

            return decoded;
        }catch (Exception e){
            throw new UnauthorizedException("Invalid or expired token");
        }
    }
}
