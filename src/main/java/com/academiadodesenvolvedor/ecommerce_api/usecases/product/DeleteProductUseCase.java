package com.academiadodesenvolvedor.ecommerce_api.usecases.product;

import com.academiadodesenvolvedor.ecommerce_api.entities.Product;
import com.academiadodesenvolvedor.ecommerce_api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteProductUseCase {
    private final ProductRepository repository;
    private final GetProductByIdUseCase getProduct;

    public void execute(Long id){
        Product product = this.getProduct.execute(id);
        repository.delete(product);
    }
}
