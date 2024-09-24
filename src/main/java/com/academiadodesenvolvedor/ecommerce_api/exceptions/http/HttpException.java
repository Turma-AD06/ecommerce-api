package com.academiadodesenvolvedor.ecommerce_api.exceptions.http;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpException extends RuntimeException {
    private final HttpStatus httpStatus;

    public HttpException(String message, HttpStatus status){
        super(message);
        this.httpStatus = status;
    }

    public HttpException(String message){
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
