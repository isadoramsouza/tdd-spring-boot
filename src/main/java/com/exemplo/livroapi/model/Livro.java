package com.exemplo.livroapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    private Long id;
    private String titulo;
    private String autor;
    private String isbn;

}
