package com.academiadodesenvolvedor.ecommerce_api.usecases.product;

import com.academiadodesenvolvedor.ecommerce_api.entities.Product;
import com.academiadodesenvolvedor.ecommerce_api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCase {
    private final ProductRepository repository;
    private final GetProductByIdUseCase getProduct;

    public Product execute(Long id, Product product){
        Product saved = getProduct.execute(id);
        if(product.getCategoryId() != null){
            saved.setCategoryId(product.getCategoryId());
        }
        if(product.getName() != null){
            saved.setName(product.getName());
        }
        if(product.getDescription() !=null){
            saved.setDescription(product.getDescription());
        }
        if(product.getPicture() != null){
            saved.setPicture(product.getPicture());
        }
        if(product.getPrice() != null){
            saved.setPrice(product.getPrice());
        }
        if(product.getScore() != null){
            saved.setScore(product.getScore());
        }
         return repository.save(saved);
    }
}
