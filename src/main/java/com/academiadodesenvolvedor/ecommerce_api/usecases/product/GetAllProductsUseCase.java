package com.academiadodesenvolvedor.ecommerce_api.usecases.product;

import com.academiadodesenvolvedor.ecommerce_api.entities.Product;
import com.academiadodesenvolvedor.ecommerce_api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllProductsUseCase {
    private final ProductRepository repository;

    public List<Product> execute(){
        return repository.findAll();
    }
}
