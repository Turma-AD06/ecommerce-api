package com.academiadodesenvolvedor.ecommerce_api.controllers;


import com.academiadodesenvolvedor.ecommerce_api.dto.input.LoginUserDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.User;
import com.academiadodesenvolvedor.ecommerce_api.usecases.user.CreateUserUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTest {

    private final String REQUEST_URL = "/auth";
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Test
    @DisplayName("Should create an user")
    public void createUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(REQUEST_URL.concat("/signup"))
                .contentType("application/json")
                .content("{\"name\": \"User dummy\"," +
                        "\"password\": \"123456789\"," +
                        "\"role\": \"SELLER\"," +
                        "\"email\": \"dummy@email.com\" }")
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("should sign in an user")
    public void signInUser() throws Exception {
        User user = new User(null, "User Dummy Login",
                "123456789", "logindummy@email.com", null, null);
        createUserUseCase.execute(user);
        LoginUserDto login = new LoginUserDto(user.getEmail(), "123456789");
        mvc.perform(MockMvcRequestBuilders.post(REQUEST_URL.concat("/signin"))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(login))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("access_token")));

    }

    @Test
    @DisplayName("should not sign in an user without account")
    public void return401ToUser() throws Exception {
        LoginUserDto login = new LoginUserDto("emailIvalid@email.com", "123456789");
        mvc.perform(MockMvcRequestBuilders.post(REQUEST_URL.concat("/signin"))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(login))
                )
                .andExpect(MockMvcResultMatchers.status().is(401))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Email e/ou senha Inválidos.")))
        ;
    }
}
