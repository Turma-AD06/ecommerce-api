package com.academiadodesenvolvedor.ecommerce_api.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderSetting {
    @Bean
    public PasswordEncoder setup(){
        return new BCryptPasswordEncoder();
    }
}
