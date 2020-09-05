package com.exemplo.livroapi.exception;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableEntityException.class)
    public ErrorMessage handleUnprocessableEntity(UnprocessableEntityException e) {
        return new ErrorMessage("422",
                "Dados enviados estão nulos.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IsbnCadastradoException.class)
    public ErrorMessage handleUnprocessableEntity(IsbnCadastradoException e) {
        return new ErrorMessage("500",
                "Já existe um livro com esse ISBN cadastrado na base de dados.");
    }

    @Getter
    class ErrorMessage {
        String error;
        String message;

        ErrorMessage(String error, String message) {
            this.error = error;
            this.message = message;
        }

    }



}
