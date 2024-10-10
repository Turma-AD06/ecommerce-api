package com.academiadodesenvolvedor.ecommerce_api.usecases.category;

import com.academiadodesenvolvedor.ecommerce_api.entities.Category;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.HttpException;
import com.academiadodesenvolvedor.ecommerce_api.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreateCategoryUseCaseTest {
    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;
    @MockBean
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        Category category = new Category(1L, "Cat 01", List.of());
        when(categoryRepository.findByName("Cat 01")).thenReturn(Optional.empty());
        when(categoryRepository.findByName("Cat 02"))
                .thenReturn(Optional.of(new Category(1L, "Cat 02", List.of())));
        when(categoryRepository.save(new Category(null, "Cat 01", null))).thenReturn(category);
    }

    @Test
    @DisplayName("should create a category")
    public void shuldCreateACategory() {
        Category category = new Category();
        category.setName("Cat 01");

        Category categorySaved = createCategoryUseCase.execute(category);

        assertNotNull(categorySaved.getId());
    }

    @Test
    @DisplayName("Should throws HttpException if category already exists")
    public void shouldThrowException() {
        Category category = new Category();
        category.setName("Cat 02");

        HttpException result = assertThrows(HttpException.class, () -> {
            createCategoryUseCase.execute(category);
        });

        assertEquals("Categoria duplicada", result.getMessage());
    }

}
