package com.academiadodesenvolvedor.ecommerce_api.controllers;

import com.academiadodesenvolvedor.ecommerce_api.dto.input.CreateProductDto;
import com.academiadodesenvolvedor.ecommerce_api.dto.output.ProductDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.Product;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.HttpException;
import com.academiadodesenvolvedor.ecommerce_api.mappers.ProductMapper;
import com.academiadodesenvolvedor.ecommerce_api.usecases.FileUploadUseCase;
import com.academiadodesenvolvedor.ecommerce_api.usecases.product.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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
    private final FileUploadUseCase fileUploadUseCase;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        List<Product> products = this.getAllProductsUseCase.execute();
        List<ProductDto> productDtos = products.stream().map(this.mapper::toOutputDto).toList();
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductDto> create(
            @ModelAttribute @Valid CreateProductDto dto,
            @RequestPart("image") MultipartFile image,
            HttpServletRequest request
    ) {
        try {
            Long userId = Long.valueOf(request.getAttribute("user_id").toString());
            Product product = this.mapper.toEntity(dto);
            product.setUserId(userId);

            if (image != null && !image.isEmpty()) {
                String imageUrl = this.fileUploadUseCase.execute(
                        userId + "/produtos",
                        UUID.randomUUID().toString(),
                        image
                );
                product.setPicture(imageUrl);
            }

            return new ResponseEntity<>(
                    this.mapper.toOutputDto(
                            this.createProductUseCase.execute(product)
                    ),
                    HttpStatus.CREATED
            );
        } catch (IOException e) {
            throw new HttpException("Erro ao cadastrar produto " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable("id") Long id) {
        Product product = this.getProductByIdUseCase.execute(id);
        return new ResponseEntity<>(
                this.mapper.toOutputDto(product),
                HttpStatus.OK
        );
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductDto> update(
            @PathVariable("id") Long id,
            @ModelAttribute @Valid CreateProductDto dto,
            @RequestPart("image") MultipartFile image,
            HttpServletRequest request
    ) {
        Long userId = Long.valueOf(request.getAttribute("user_id").toString());
        Product savedProduct = this.getProductByIdUseCase.execute(id);
        Product mapped = this.mapper.toEntity(dto);
        try {
            mapped.setCategoryId(dto.getCategoryId());
            if (image != null && !image.isEmpty()) {
                if (savedProduct.getPicture() != null) {
                    Path oldImage = Paths.get(savedProduct.getPicture());
                    Files.deleteIfExists(oldImage);
                }
                String imageUrl = this.fileUploadUseCase.execute(
                        userId + "/produtos",
                        UUID.randomUUID().toString(),
                        image
                );
                mapped.setPicture(imageUrl);
            }
            Product product = this.updateProductUseCase.execute(id, mapped);
            return new ResponseEntity<>(
                    this.mapper.toOutputDto(product),
                    HttpStatus.OK
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new HttpException("Erro ao cadastrar produto " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        this.deleteProductUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
