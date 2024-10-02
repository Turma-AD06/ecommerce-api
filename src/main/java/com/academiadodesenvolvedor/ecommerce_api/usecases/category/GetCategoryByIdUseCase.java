package com.academiadodesenvolvedor.ecommerce_api.usecases.category;

import com.academiadodesenvolvedor.ecommerce_api.entities.Category;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.NotFoundException;
import com.academiadodesenvolvedor.ecommerce_api.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCategoryByIdUseCase {
    private final CategoryRepository categoryRepository;

    public Category execute(Long id){
        return this.categoryRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Categoria não encontrada"));
    }
}
