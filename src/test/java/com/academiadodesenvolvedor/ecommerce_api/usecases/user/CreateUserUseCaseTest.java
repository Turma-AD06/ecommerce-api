package com.academiadodesenvolvedor.ecommerce_api.usecases.user;

import com.academiadodesenvolvedor.ecommerce_api.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CreateUserUseCaseTest {
    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Test
    @DisplayName("should create an user")
    public void shouldCreateAnUser() {
        String password = "123456789";
        User user = new User();
        user.setName("User Dummy");
        user.setEmail("dummy@user.com");
        user.setPassword(password);

        User savedUser = createUserUseCase.execute(user);

        assertNotNull(savedUser.getId());
        assertNotEquals(password, savedUser.getPassword());
    }
}
