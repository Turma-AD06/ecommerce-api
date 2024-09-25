package com.academiadodesenvolvedor.ecommerce_api.exceptions.handlers;

import com.academiadodesenvolvedor.ecommerce_api.dto.output.InputErrorDto;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@AllArgsConstructor
@RestControllerAdvice
public class MethodArgumentNotValidExceptionHandler {
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<InputErrorDto> handle(MethodArgumentNotValidException e){
        List<FieldError> errors = e.getFieldErrors();
        return errors.stream()
                .map(input -> {
                    String message = this.messageSource.getMessage(input, LocaleContextHolder.getLocale());
                    return new InputErrorDto(input.getField(), message);
                }).toList();
    }
}
