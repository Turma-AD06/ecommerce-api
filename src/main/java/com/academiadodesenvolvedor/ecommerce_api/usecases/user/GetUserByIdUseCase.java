package com.academiadodesenvolvedor.ecommerce_api.usecases.user;

import com.academiadodesenvolvedor.ecommerce_api.entities.User;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.NotFoundException;
import com.academiadodesenvolvedor.ecommerce_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserByIdUseCase {
    private final UserRepository userRepository;

    public User execute(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nenhum usuário encontrado"));
    }
}
