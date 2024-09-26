package com.academiadodesenvolvedor.ecommerce_api.exceptions.http;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends HttpException{
   public UnauthorizedException(String message){
       super(message, HttpStatus.UNAUTHORIZED);
   }
}
