package com.exemplo.livroapi.service;

import com.exemplo.livroapi.dto.LivroDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LivroService {

    private ModelMapper modelMapper;

    public LivroService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public LivroDTO criarLivro(LivroDTO livroDTO) {
        return livroDTO;
    }

}
