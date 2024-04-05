package com.algaworks.algafood.api.assembler.modelDisassembler;

import com.algaworks.algafood.api.model.request.CidadeModelRequest;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomain(CidadeModelRequest cidadeModelRequest){
        return modelMapper.map(cidadeModelRequest, Cidade.class);
    }
}
