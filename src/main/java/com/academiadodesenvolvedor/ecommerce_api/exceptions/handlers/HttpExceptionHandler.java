package com.academiadodesenvolvedor.ecommerce_api.exceptions.handlers;

import com.academiadodesenvolvedor.ecommerce_api.dto.output.ExceptionDto;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.HttpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ExceptionDto> handle(HttpException e){
        return  new ResponseEntity<>(new ExceptionDto(e.getMessage(), e.getHttpStatus()), e.getHttpStatus());
    }
}
