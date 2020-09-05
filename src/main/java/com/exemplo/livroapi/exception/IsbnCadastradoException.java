package com.exemplo.livroapi.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IsbnCadastradoException extends RuntimeException {

    private static final long serialVersionUID = -4160704396501040909L;

    private Integer code;

    public IsbnCadastradoException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

}
