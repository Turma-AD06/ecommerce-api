package com.academiadodesenvolvedor.ecommerce_api.entities.enums;

import lombok.Getter;

@Getter
public enum Roles {
    CUSTOMER("ROLE_CUSTOMER"),
    ADMIN("ROLE_ADMIN"),
    SELLER("ROLE_SELLER");

    private final String role;

    Roles(String role) {
        this.role = role;
    }
}
