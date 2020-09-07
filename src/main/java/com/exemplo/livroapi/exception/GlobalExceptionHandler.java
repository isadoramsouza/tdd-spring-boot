package com.exemplo.livroapi.exception;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(status, ex.getBindingResult().getFieldErrors().stream()
                .map(field -> String.format("%s - %s", field.getField(), field.getDefaultMessage())).collect(Collectors.toList())), status);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IsbnCadastradoException.class)
    public ErrorMessage handleUnprocessableEntity(IsbnCadastradoException e) {
        return new ErrorMessage("500",
                "Já existe um livro com esse ISBN cadastrado na base de dados.");
    }

    @ExceptionHandler(LivroNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleRecordNotFoundException(LivroNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
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
