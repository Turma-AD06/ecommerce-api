package com.academiadodesenvolvedor.ecommerce_api.usecases.user;

import com.academiadodesenvolvedor.ecommerce_api.entities.User;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.HttpException;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.NotFoundException;
import com.academiadodesenvolvedor.ecommerce_api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetUserByEmailUseCase {
    private final UserRepository userRepository;

    public User execute(String email){
        Optional<User> user = this.userRepository.findByEmail(email);

        if(user.isEmpty()){
            throw new NotFoundException("Não foi posssível encontrar o usuário");
        }

        return user.get();
    }
}
