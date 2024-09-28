package com.academiadodesenvolvedor.ecommerce_api.usecases.product;

import com.academiadodesenvolvedor.ecommerce_api.entities.Product;
import com.academiadodesenvolvedor.ecommerce_api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductUseCase {
    private final ProductRepository repository;

    public Product execute(Product product){
        return this.repository.save(product);
    }
}
