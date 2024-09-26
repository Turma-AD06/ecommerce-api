package com.academiadodesenvolvedor.ecommerce_api.settings;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtSettings {

    @Value("${app.jwt.secret}")
    private String secret;

    @Bean
    public Algorithm setUpAlgorithm(){
        return Algorithm.HMAC256(secret);
    }

    @Bean
    public JWTVerifier setUpVerifier(){
        return JWT.require(this.setUpAlgorithm()).build();
    }
}
