package com.exemplo.livroapi.exception;

public class LivroNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2834316862312517375L;

    public LivroNotFoundException(Long id) {
        super("Livro com id " +id+ " não existe.");
    }
}
