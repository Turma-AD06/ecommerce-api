package com.academiadodesenvolvedor.ecommerce_api.controllers;

import com.academiadodesenvolvedor.ecommerce_api.dto.input.CreateCategoryDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.Category;
import com.academiadodesenvolvedor.ecommerce_api.mappers.CategoryMapper;
import com.academiadodesenvolvedor.ecommerce_api.usecases.category.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Category> create(@RequestBody CreateCategoryDto dto){
        Category cat = categoryMapper.toEntity(dto);
        Category category = this.createCategoryUseCase.execute(cat);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Category>> list(){
        List<Category> category = this.getAllCategoriesUseCase.execute();
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") Long id){
        Category category =  this.getCategoryByIdUseCase.execute(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") Long id, @RequestBody CreateCategoryDto dto){

        Category category = this.updateCategoryUseCase.execute(id, dto.getName());
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
      this.deleteCategoryUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
