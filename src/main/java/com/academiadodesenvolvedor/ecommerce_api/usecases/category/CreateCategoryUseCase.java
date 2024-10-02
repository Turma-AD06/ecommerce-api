package com.academiadodesenvolvedor.ecommerce_api.usecases.category;

import com.academiadodesenvolvedor.ecommerce_api.entities.Category;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.HttpException;
import com.academiadodesenvolvedor.ecommerce_api.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateCategoryUseCase {
    private final CategoryRepository categoryRepository;

    public Category execute(Category category){
        Optional<Category> categorySaved = this.categoryRepository.findByName(category.getName());
        if(categorySaved.isPresent()){
            throw new HttpException("Categoria duplicada", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return this.categoryRepository.save(category);

    }
}
