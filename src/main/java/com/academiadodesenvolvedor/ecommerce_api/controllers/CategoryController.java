package com.academiadodesenvolvedor.ecommerce_api.controllers;

import com.academiadodesenvolvedor.ecommerce_api.dto.input.CreateCategoryDto;
import com.academiadodesenvolvedor.ecommerce_api.dto.output.CategoryDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.Category;
import com.academiadodesenvolvedor.ecommerce_api.mappers.CategoryMapper;
import com.academiadodesenvolvedor.ecommerce_api.usecases.category.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CreateCategoryUseCase createCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final GetAllCategoriesUseCase getAllCategoriesUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final CategoryMapper categoryMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> create(@RequestBody CreateCategoryDto dto) {
        Category cat = categoryMapper.toEntity(dto);
        Category category = this.createCategoryUseCase.execute(cat);

        return new ResponseEntity<>(this.categoryMapper.toOutputDto(category), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> list() {
        List<Category> categories = this.getAllCategoriesUseCase.execute();
        List<CategoryDto> categoriesDto =
                categories.stream().map(this.categoryMapper::toOutputDto).toList();
        return new ResponseEntity<>(categoriesDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable("id") Long id) {
        Category category = this.getCategoryByIdUseCase.execute(id);
        return new ResponseEntity<>(this.categoryMapper.toOutputDto(category), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> update(@PathVariable("id") Long id, @RequestBody CreateCategoryDto dto) {

        Category category = this.updateCategoryUseCase.execute(id, dto.getName());
        return new ResponseEntity<>(this.categoryMapper.toOutputDto(category), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        this.deleteCategoryUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
