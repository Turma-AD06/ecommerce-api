package com.academiadodesenvolvedor.ecommerce_api.settings;

import com.academiadodesenvolvedor.ecommerce_api.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecuritySetting {
    private static final String[] PUBLIC_GET_ROUTES = {"/products", "/storage/**"};
    private static final String[] PUBLIC_POST_ROUTES = {"/auth/**", "/webhook/stripe"};
    private final JwtAuthFilter filterJwt;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, PUBLIC_GET_ROUTES).permitAll()
                        .requestMatchers(HttpMethod.POST, PUBLIC_POST_ROUTES).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filterJwt, BasicAuthenticationFilter.class);

        return http.build();
    }
}
