package com.academiadodesenvolvedor.ecommerce_api.controllers;

import com.academiadodesenvolvedor.ecommerce_api.dto.input.CreateProductDto;
import com.academiadodesenvolvedor.ecommerce_api.dto.output.ProductDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.Product;
import com.academiadodesenvolvedor.ecommerce_api.mappers.ProductMapper;
import com.academiadodesenvolvedor.ecommerce_api.usecases.product.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final CreateProductUseCase createProductUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final ProductMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll(){
        List<Product> products = this.getAllProductsUseCase.execute();
        List<ProductDto> productDtos = products.stream().map(this.mapper::toOutputDto).toList();
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(
            @RequestBody @Valid CreateProductDto dto,
                                             HttpServletRequest request
    ){
        Long userId = Long.valueOf(request.getAttribute("user_id").toString());
        Product product = this.mapper.toEntity(dto);
        product.setUserId(userId);

        return new ResponseEntity<>(
                this.mapper.toOutputDto(
                        this.createProductUseCase.execute(product)
                ),
                HttpStatus.CREATED
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable("id") Long id){
        Product product = this.getProductByIdUseCase.execute(id);
        return new ResponseEntity<>(
                this.mapper.toOutputDto(product),
                HttpStatus.OK
        );
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProductDto> update(@PathVariable("id") Long id, @RequestBody @Valid CreateProductDto dto){
        Product mapped = this.mapper.toEntity(dto);
        mapped.setCategoryId(dto.getCategoryId());
        Product product = this.updateProductUseCase.execute(id,mapped );
        return new ResponseEntity<>(
                this.mapper.toOutputDto(product),
                HttpStatus.OK
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
         this.deleteProductUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
