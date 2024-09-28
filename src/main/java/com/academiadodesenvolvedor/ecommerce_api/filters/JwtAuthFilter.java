package com.academiadodesenvolvedor.ecommerce_api.filters;

import com.academiadodesenvolvedor.ecommerce_api.usecases.user.ValidateTokenUseCase;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final ValidateTokenUseCase validateTokenUseCase;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException
    {
        String header = request.getHeader("Authorization");
        if(header == null){
            filterChain.doFilter(request, response);
            return;
        }

        DecodedJWT tokenDecoded = this.validateTokenUseCase.execute(header.replace("Bearer ",""));
        request.setAttribute("user_id",Long.valueOf(tokenDecoded.getSubject()));

        UsernamePasswordAuthenticationToken authUser = new UsernamePasswordAuthenticationToken(
                tokenDecoded,
                null,
                List.of());
        SecurityContextHolder.getContext().setAuthentication(authUser);

        filterChain.doFilter(request,response);
    }
}
