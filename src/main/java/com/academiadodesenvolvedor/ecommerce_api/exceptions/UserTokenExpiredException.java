package com.academiadodesenvolvedor.ecommerce_api.exceptions;

public class UserTokenExpiredException extends RuntimeException{
    public UserTokenExpiredException(String message){
        super(message);
    }
}
