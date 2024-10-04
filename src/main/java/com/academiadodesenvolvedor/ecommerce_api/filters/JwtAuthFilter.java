package com.academiadodesenvolvedor.ecommerce_api.filters;

import com.academiadodesenvolvedor.ecommerce_api.entities.User;
import com.academiadodesenvolvedor.ecommerce_api.usecases.user.GetUserByIdUseCase;
import com.academiadodesenvolvedor.ecommerce_api.usecases.user.ValidateTokenUseCase;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final ValidateTokenUseCase validateTokenUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null) {
            filterChain.doFilter(request, response);
            return;
        }
        DecodedJWT tokenDecoded = this.validateTokenUseCase.execute(header.replace("Bearer ", ""));
        Long userId = Long.valueOf(tokenDecoded.getSubject());
        User user = this.getUserByIdUseCase.execute(userId);

        request.setAttribute("user_id", userId);

        List<SimpleGrantedAuthority> roles = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().getRole()))
                .toList();

        UsernamePasswordAuthenticationToken authUser = new UsernamePasswordAuthenticationToken(
                user,
                null,
                roles);

        SecurityContextHolder.getContext().setAuthentication(authUser);

        filterChain.doFilter(request, response);
    }
}
