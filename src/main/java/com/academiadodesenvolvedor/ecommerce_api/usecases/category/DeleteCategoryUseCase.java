package com.academiadodesenvolvedor.ecommerce_api.usecases.category;

import com.academiadodesenvolvedor.ecommerce_api.entities.Category;
import com.academiadodesenvolvedor.ecommerce_api.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCategoryUseCase {
    private final CategoryRepository categoryRepository;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;

    public void execute(Long id){
        Category category = this.getCategoryByIdUseCase.execute(id);

        this.categoryRepository.delete(category);
    }

}
