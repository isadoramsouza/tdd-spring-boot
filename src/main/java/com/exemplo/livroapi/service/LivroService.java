package com.exemplo.livroapi.service;

import com.exemplo.livroapi.dto.LivroDTO;
import com.exemplo.livroapi.exception.IsbnCadastradoException;
import com.exemplo.livroapi.exception.LivroNotFoundException;
import com.exemplo.livroapi.model.Livro;
import com.exemplo.livroapi.repository.LivroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LivroService {

    private LivroRepository livroRepository;
    private ModelMapper modelMapper;

    public LivroService(LivroRepository livroRepository, ModelMapper modelMapper) {
        this.livroRepository = livroRepository;
        this.modelMapper = modelMapper;
    }

    public LivroDTO criarLivro(LivroDTO livroDTO) {
        if(livroRepository.existsByIsbn(livroDTO.getIsbn())){
            throw new IsbnCadastradoException();
        }
        Livro livro =  livroRepository.saveAndFlush(modelMapper.map(livroDTO, Livro.class));
        return modelMapper.map(livro, LivroDTO.class);
    }

    public LivroDTO findLivroById(Long id) {
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new LivroNotFoundException(id));
        return modelMapper.map(livro, LivroDTO.class);
    }
}
