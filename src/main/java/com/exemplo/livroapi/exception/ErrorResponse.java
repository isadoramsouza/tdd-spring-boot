package com.exemplo.livroapi.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collection;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private int errorCode;
    private String error;
    private String message;
    private Collection<String> fieldErrors;

    public ErrorResponse(HttpStatus status, Collection<String> fieldErrors) {
        this.errorCode = status.value();
        this.error = status.name();
        this.fieldErrors = fieldErrors;
    }

    public ErrorResponse(HttpStatus status, String message) {
        this.errorCode = status.value();
        this.error = status.name();
        this.message = message;
    }
}