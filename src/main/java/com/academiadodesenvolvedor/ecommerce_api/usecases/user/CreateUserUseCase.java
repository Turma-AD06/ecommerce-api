package com.academiadodesenvolvedor.ecommerce_api.usecases.user;

import com.academiadodesenvolvedor.ecommerce_api.entities.User;
import com.academiadodesenvolvedor.ecommerce_api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserUseCase {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public User execute(User user){
        String hashedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return this.userRepository.save(user);
    }
}
