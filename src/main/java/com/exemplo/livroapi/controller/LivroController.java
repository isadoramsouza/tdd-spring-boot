package com.exemplo.livroapi.controller;

import com.exemplo.livroapi.dto.LivroDTO;
import com.exemplo.livroapi.model.Livro;
import com.exemplo.livroapi.service.LivroService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/livro")
public class LivroController {

    private LivroService livroService;
    private ModelMapper modelMapper;

    public LivroController(LivroService livroService, ModelMapper modelMapper) {
        this.livroService = livroService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Cria novo livro", tags = "Livro")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operação bem sucedida."),
            @ApiResponse(code = 400, message = "Parâmetros inválidos."),
            @ApiResponse(code = 401, message = "Sem autorização."),
            @ApiResponse(code = 403, message = "Sem autorização."),
            @ApiResponse(code = 404, message = "Endpoint não encontrado."),
            @ApiResponse(code = 500, message = "Erro durante a operação.")
    })
    public ResponseEntity<LivroDTO> criarLivro(@RequestBody LivroDTO livroDTO) {
        Livro livro = livroService.criarLivro(livroDTO);
        return new ResponseEntity<>(modelMapper.map(livro, LivroDTO.class), HttpStatus.CREATED);
    }


}
