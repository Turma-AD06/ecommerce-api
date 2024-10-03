package com.academiadodesenvolvedor.ecommerce_api.seeder;

import com.academiadodesenvolvedor.ecommerce_api.entities.Role;
import com.academiadodesenvolvedor.ecommerce_api.entities.enums.Roles;
import com.academiadodesenvolvedor.ecommerce_api.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@RequiredArgsConstructor
public class RoleSeeder {
    private static final Logger LOG = LoggerFactory.getLogger(RoleSeeder.class);
    private final RoleRepository repository;
    private int count = 0;

    public void run() {
        LOG.info("Iniciando o seed de Roles");
        Roles[] roles = Roles.values();

        Arrays.stream(roles).forEach(roleName -> {
            if (this.repository.findByName(roleName).isPresent()) {
                return;
            }

            Role role = new Role();
            role.setName(roleName);
            repository.save(role);
            count++;
        });

        LOG.info(String.valueOf(count).concat(" novas Roles foram cadastradas."));
    }
}
