package com.academiadodesenvolvedor.ecommerce_api.seeder;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseSeeder.class);
    private final RoleSeeder roleSeeder;

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Executando DatabaseSeeder...");
        roleSeeder.run();
    }
}
