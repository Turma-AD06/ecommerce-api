package com.academiadodesenvolvedor.ecommerce_api.usecases.role;

import com.academiadodesenvolvedor.ecommerce_api.entities.Role;
import com.academiadodesenvolvedor.ecommerce_api.entities.enums.Roles;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.NotFoundException;
import com.academiadodesenvolvedor.ecommerce_api.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetRoleByNameUseCase {
    private final RoleRepository repository;

    public Role execute(Roles role) {
        return this.repository.findByName(role)
                .orElseThrow(() -> new NotFoundException("Role não encontrada"));
    }
}
