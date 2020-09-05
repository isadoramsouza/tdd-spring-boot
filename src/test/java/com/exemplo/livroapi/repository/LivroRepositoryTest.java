package com.exemplo.livroapi.repository;

import com.exemplo.livroapi.model.Livro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class LivroRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    LivroRepository livroRepository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um livro na base com o isbn informado")
    public void retornaTrueQuandoIsbnExiste(){
        String isbn = "123";
        Livro livro = criaLivro(isbn);
        entityManager.persist(livro);
        Boolean bExiste = livroRepository.existsByIsbn(isbn);
        assertThat(bExiste).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando não existir um livro na base com o isbn informado")
    public void retornaFalseQuandoIsbnExiste(){
        String isbn = "123";
        Boolean bExiste = livroRepository.existsByIsbn(isbn);
        assertThat(bExiste).isFalse();
    }

    private Livro criaLivro(String isbn){
        return Livro.builder()
                .titulo("Senhor dos Anéis")
                .autor("J R. R. Tolkien")
                .isbn(isbn).build();
    }
}