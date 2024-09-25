package com.academiadodesenvolvedor.ecommerce_api.validation;

import com.academiadodesenvolvedor.ecommerce_api.validation.rules.IsUniqueRule;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsUniqueRule.class)
public @interface IsUnique {
    Class<?> entity();
    String field() default "email";
    String message() default "The email is already in use";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default  {};

}
