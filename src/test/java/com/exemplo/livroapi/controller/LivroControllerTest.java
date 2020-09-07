package com.exemplo.livroapi.controller;

import com.exemplo.livroapi.dto.LivroDTO;
import com.exemplo.livroapi.exception.LivroNotFoundException;
import com.exemplo.livroapi.service.LivroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class LivroControllerTest {

    private final static String livro_api = "/api/livro";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LivroService livroService;

    @Test
    @DisplayName("Deve criar um novo livro com sucesso.")
    public void criarLivroTest() throws Exception {
        LivroDTO livroDTO = criaLivroDTO();
        LivroDTO livroCriado = retornaLivroCriadoDTO();
        BDDMockito.given(livroService.criarLivro(Mockito.any(LivroDTO.class))).willReturn(livroCriado);
        String json = new ObjectMapper().writeValueAsString(livroDTO);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(livro_api)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(json);
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("titulo").value(livroDTO.getTitulo()))
                .andExpect(jsonPath("autor").value(livroDTO.getAutor()))
                .andExpect(jsonPath("isbn").value(livroDTO.getIsbn()));
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando titulo do livro estiver em branco.")
    public void criaLivroComTituloEmBrancoTest() throws Exception {
        LivroDTO livroDTO = criaLivroDTO();
        livroDTO.setTitulo("");
        String json = new ObjectMapper().writeValueAsString(livroDTO);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(livro_api)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(400))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.fieldErrors[0]").value("titulo - must not be blank"));
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando autor for nulo.")
    public void criaLivroComAutorNuloTest() throws Exception {
        LivroDTO livroDTO = criaLivroDTO();
        livroDTO.setAutor(null);
        String json = new ObjectMapper().writeValueAsString(livroDTO);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(livro_api)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(400))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.fieldErrors[0]").value("autor - must not be null"));
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando isbn for nulo.")
    public void criaLivroComIsbnNuloTest() throws Exception {
        LivroDTO livroDTO = criaLivroDTO();
        livroDTO.setIsbn(null);
        String json = new ObjectMapper().writeValueAsString(livroDTO);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(livro_api)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(400))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.fieldErrors[0]").value("isbn - must not be null"));
    }

    @Test
    @DisplayName("Deve obter informações de um livro.")
    public void obtemInformacoesLivroTest() throws Exception {
        Long id = 1L;
        LivroDTO livroDTO = retornaLivroCriadoDTO();
        livroDTO.setId(id);
        BDDMockito.given(livroService.findLivroById(id)).willReturn(livroDTO);

        String json = new ObjectMapper().writeValueAsString(livroDTO);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(livro_api.concat("/"+id))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(json);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("titulo").value(livroDTO.getTitulo()))
                .andExpect(jsonPath("autor").value(livroDTO.getAutor()))
                .andExpect(jsonPath("isbn").value(livroDTO.getIsbn()));
    }

    @Test
    @DisplayName("Deve lançar erro ao buscar por um id não existente.")
    public void obtemInformacoesLivroComIdInexistenteTest() throws Exception {
        Long id = 1L;
        BDDMockito.given(livroService.findLivroById(id)).willThrow(new LivroNotFoundException(id));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(livro_api.concat("/"+id))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Livro com id " +id+ " não existe."));
    }

    private LivroDTO criaLivroDTO(){
        return LivroDTO.builder()
                .titulo("Senhor dos Anéis")
                .autor("J R. R. Tolkien")
                .isbn("9780007525546").build();
    }

    private LivroDTO retornaLivroCriadoDTO(){
        return LivroDTO.builder()
                .id(1L)
                .titulo("Senhor dos Anéis")
                .autor("J R. R. Tolkien")
                .isbn("9780007525546").build();
    }

}
