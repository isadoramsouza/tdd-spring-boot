package com.exemplo.livroapi.service;

import com.exemplo.livroapi.dto.LivroDTO;
import com.exemplo.livroapi.exception.IsbnCadastradoException;
import com.exemplo.livroapi.exception.LivroNotFoundException;
import com.exemplo.livroapi.model.Livro;
import com.exemplo.livroapi.repository.LivroRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LivroServiceTest {

    private LivroService livroService;

    @MockBean
    private LivroRepository livroRepository;

    @MockBean
    private ModelMapper modelMapper;


    @BeforeEach
    public void setUp(){
        this.modelMapper = new ModelMapper();
        this.livroService = new LivroService(livroRepository, modelMapper);
    }

    @Test
    @DisplayName("Deve criar um livro")
    public void criarLivroTest(){
        LivroDTO livroDTO = criaLivroDTO();
        Livro livroCriado = retornaLivroCriado();
        when(livroRepository.saveAndFlush(modelMapper.map(livroDTO, Livro.class))).thenReturn(livroCriado);
        LivroDTO livroCriadoDTO = livroService.criarLivro(livroDTO);
        assertThat(livroCriadoDTO.getId()).isNotNull();
        assertThat(livroCriadoDTO.getTitulo()).isEqualTo("Senhor dos Anéis");
        assertThat(livroCriadoDTO.getAutor()).isEqualTo("J R. R. Tolkien");
        assertThat(livroCriadoDTO.getIsbn()).isEqualTo("9780007525546");
    }

    @Test
    @DisplayName("Deve lançar erro ao criar um livro com isbn já utilizado.")
    public void criarLivroComIsbnDuplicadoTest() {
        LivroDTO livroDTO = criaLivroDTO();
        when(livroRepository.existsByIsbn(Mockito.anyString())).thenReturn(true);
        Throwable exception = Assertions.catchThrowable(() -> livroService.criarLivro(livroDTO));
        assertThat(exception)
                .isInstanceOf(IsbnCadastradoException.class);
        Mockito.verify(livroRepository, Mockito.never()).saveAndFlush(modelMapper.map(livroDTO, Livro.class));
    }

    @Test
    @DisplayName("Deve retornar um livro por id.")
    public void obtemInformacoesLivroTest(){
        Long id = 1L;
        Livro livro = retornaLivroCriado();
        when(livroRepository.findById(id)).thenReturn(Optional.of(livro));
        LivroDTO livroCriadoDTO = livroService.findLivroById(id);
        assertThat(livroCriadoDTO.getId()).isEqualTo(id);
        assertThat(livroCriadoDTO.getTitulo()).isEqualTo(livroCriadoDTO.getTitulo());
        assertThat(livroCriadoDTO.getAutor()).isEqualTo(livroCriadoDTO.getAutor());
        assertThat(livroCriadoDTO.getIsbn()).isEqualTo(livroCriadoDTO.getIsbn());
    }

    @Test
    public void obtemInformacoesLivroComIdInexistenteTest() {
        Long id = 1L;
        Throwable exception = Assertions.catchThrowable(() -> livroService.findLivroById(id));
        assertThat(exception)
                .isInstanceOf(LivroNotFoundException.class);
    }


    private LivroDTO criaLivroDTO(){
        return LivroDTO.builder()
                .titulo("Senhor dos Anéis")
                .autor("J R. R. Tolkien")
                .isbn("9780007525546").build();
    }

    private Livro retornaLivroCriado(){
        return modelMapper.map(LivroDTO.builder()
                .id(1L)
                .titulo("Senhor dos Anéis")
                .autor("J R. R. Tolkien")
                .isbn("9780007525546").build(), Livro.class);
    }

}
