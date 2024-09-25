package com.academiadodesenvolvedor.ecommerce_api.validation.rules;

import com.academiadodesenvolvedor.ecommerce_api.validation.IsUnique;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class IsUniqueRule implements ConstraintValidator<IsUnique, String> {
    private String field;
    private String message;
    private Class<?> entity;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(IsUnique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.entity =constraintAnnotation.entity();
        this.field = constraintAnnotation.field();
        this.message = constraintAnnotation.message();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String sql = "SELECT e FROM "+ this.getEntityName() + " e WHERE e."+this.field + " = :value";
        List<?> entityList = this.entityManager.createQuery(sql, this.entity)
                .setParameter("value",value)
                .getResultList();

        return entityList.isEmpty();
    }

    private String getEntityName(){
        return this.entity.toString().replace("class ","");
    }
}
