package com.academiadodesenvolvedor.ecommerce_api.repositories;

import com.academiadodesenvolvedor.ecommerce_api.entities.Role;
import com.academiadodesenvolvedor.ecommerce_api.entities.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Roles role);
}
