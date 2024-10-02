package com.academiadodesenvolvedor.ecommerce_api.usecases.category;

import com.academiadodesenvolvedor.ecommerce_api.entities.Category;
import com.academiadodesenvolvedor.ecommerce_api.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCategoryUseCase {
    private final CategoryRepository categoryRepository;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;

    public Category execute(Long id, String name){
        Category saved = getCategoryByIdUseCase.execute(id);

        saved.setName(name);

        return categoryRepository.save(saved);
    }
}
