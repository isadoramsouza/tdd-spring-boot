package com.exemplo.livroapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {

    private Long id;

    @NotBlank
    private String titulo;

    @NotNull
    private String autor;

    @NotNull
    private String isbn;


}
