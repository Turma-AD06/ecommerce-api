package com.academiadodesenvolvedor.ecommerce_api.usecases.product;

import com.academiadodesenvolvedor.ecommerce_api.entities.Product;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.NotFoundException;
import com.academiadodesenvolvedor.ecommerce_api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProductByIdUseCase {
    private final ProductRepository repository;

    public Product execute(Long id)
    {
        return this.repository.findById(id)
                .orElseThrow(()-> new NotFoundException("O Produto não pode ser encontrado"));
    }
}
