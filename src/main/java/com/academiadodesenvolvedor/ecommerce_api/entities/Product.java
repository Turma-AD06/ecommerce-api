package com.academiadodesenvolvedor.ecommerce_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Long price;
    private String picture;
    private Integer score;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "category_id")
    private Long categoryId;

    @ManyToOne
    @JoinColumn(name = "category_id", updatable = false, insertable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name ="user_id", updatable = false, insertable = false)
    private User user;
}
