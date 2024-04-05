package com.algaworks.algafood.api.assembler.modelDisassembler;

import com.algaworks.algafood.api.model.request.CozinhaModelRequest;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelRequestDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toDomain(CozinhaModelRequest cozinhaModelRequest){
        return modelMapper.map(cozinhaModelRequest, Cozinha.class);
    }
}
