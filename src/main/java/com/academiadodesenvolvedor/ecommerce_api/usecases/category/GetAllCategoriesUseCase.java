package com.academiadodesenvolvedor.ecommerce_api.usecases.category;

import com.academiadodesenvolvedor.ecommerce_api.entities.Category;
import com.academiadodesenvolvedor.ecommerce_api.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllCategoriesUseCase {
    private final CategoryRepository categoryRepository;

    public List<Category> execute(){
        return this.categoryRepository.findAll();
    }
}
